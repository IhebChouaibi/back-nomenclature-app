package eng.bns.nomenclature.service;

import eng.bns.nomenclature.entities.*;
import eng.bns.nomenclature.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ExcelImportService {
    private static final Logger logger = LoggerFactory.getLogger(ExcelImportService.class);
    private static final Pattern DIGIT_PATTERN = Pattern.compile("^\\d+$");
    private static final Set<String> HEADER_KEYWORDS = Set.of(
            "Codification", "N° de Position", "Position", "N.D.P", "N.G.P", "Tarif", "U.E",
            "NSH", "Clé", "Désignation des produits", "Qtité", "Imposition", "D.D", "D.C"
    );

    // Repositories
    private final PositionRepository positionRepository;
    private final SousPositionRespositry sousPositionRespositry;
    private final NCRepository ncRepository;
    private final TaricRepository taricRepository;
    private final ChapitreRepository chapitreRepository;
    private final SuffixRepository  suffixRepository;
    private final DescriptionRepository descriptionRepository;


    @Transactional
    public void importExcelData(MultipartFile file) throws Exception {
        Map<String, Position> positionsCache = new HashMap<>();
        Map<String, SousPosition> sousPositionCache = new HashMap<>();
        Map<String, NC> ncCache = new HashMap<>();
        Map<String, TARIC> taricCache = new HashMap<>();
        Map<String ,Suffix> suffixCache = new HashMap<>();
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row == null || row.getRowNum() < 4) continue;
                if (isHeaderRow(row)) continue;

                // Traitement colonne A pour les Positions
                String rawColA = getCellValue(row, 0);
                if (rawColA != null && !rawColA.trim().toUpperCase().startsWith("TC")) {
                    String colA = cleanPositionCode(rawColA);
                    if (colA != null) {
                        getOrCreatePosition(sheet ,row, colA, positionsCache);
                    }
                }

                StringBuilder fullCodeBuilder = new StringBuilder();

                String partB = getCellValue(row, 1);
                if (partB == null || partB.isEmpty()) continue;
                fullCodeBuilder.append(partB.replaceAll("[^0-9]", ""));

                String partC = getCellValue(row, 2);
                if (partC != null && !partC.isEmpty()) {
                    fullCodeBuilder.append(partC.replaceAll("[^0-9]", ""));
                }

                // Colonne D
                String partD = getCellValue(row, 3);
                if (partD != null && !partD.isEmpty()) {
                    fullCodeBuilder.append(partD.replaceAll("[^0-9]", ""));
                }

                // Colonne E
                String partE = getCellValue(row, 4);
                if (partE != null && !partE.isEmpty()) {
                    fullCodeBuilder.append(partE.replaceAll("[^0-9]", ""));
                }

                // Colonne F

                String partF = getCellValue(row, 5);
                if (partF != null && !partF.isEmpty()) {
                    fullCodeBuilder.append(partF.replaceAll("[^0-9]", ""));
                }
                String suffixCode = null;
                String partG = getCellValue(row, 6);
                if (partG != null && !partG.isEmpty()) {
                    // Nettoyer et limiter à 2 caractères
                    suffixCode = partG.replaceAll("[^a-zA-Z0-9]", "");
                    suffixCode = suffixCode.length() > 2 ? suffixCode.substring(0, 2) : suffixCode;
                }
                String fullCode = fullCodeBuilder.toString();
                if (fullCode.isEmpty()) continue;

                final int SOUS_POSITION_LENGTH = 6;
                final int NC_LENGTH = 8;
                final int TARIC_LENGTH = 10;

                if (fullCode.length() >= SOUS_POSITION_LENGTH) {
                    String sousPositionCode = fullCode.substring(0, SOUS_POSITION_LENGTH);
                    processSousPosition(sheet ,row, sousPositionCode, positionsCache, sousPositionCache);

                    if (fullCode.length() >= NC_LENGTH) {
                        String ncCode = fullCode.substring(0, NC_LENGTH);
                        processNC(sheet ,row, ncCode, sousPositionCache, ncCache);

                        if (fullCode.length() >= TARIC_LENGTH) {
                            String taricCode = fullCode.substring(0, TARIC_LENGTH);
                            processTaric(sheet, row, taricCode, suffixCode, ncCache, taricCache, suffixCache);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new Exception("Error processing Excel file", e);
        }
    }

    private Position getOrCreatePosition(Sheet sheet,Row row, String positionCode, Map<String, Position> cache) {
        if (positionCode.length() != 4) {
            logger.warn("Invalid Position code length at row {}: {}", row.getRowNum(), positionCode);
            return null;
        }

        if (cache.containsKey(positionCode)) {
            return cache.get(positionCode);
        }

        Position position = positionRepository.findBycodePosition(positionCode);
        if (position != null) {
            cache.put(positionCode, position);
            return position;
        }

        String chapitreCode = positionCode.substring(0, 2);
        Chapitre chapitre = chapitreRepository.findByCodeChapitre(chapitreCode);
        if (chapitre == null) {
            logger.error("Chapter not found for code: {} at row {}", chapitreCode, row.getRowNum());
            return null;
        }

        position = new Position();
        position.setCodePosition(positionCode);
        position.setChapitre(chapitre);

        // CORRECTION: Prendre le libellé de la colonne H (index 7)
        String libelle = getMergedLibelle(sheet, row);
        if (!libelle.isEmpty()) {
            position.setLibellePosition(libelle);
        }

        position = positionRepository.save(position);
        cache.put(positionCode, position);
        return position;
    }

    private void processSousPosition(Sheet sheet,Row row, String code,
                                     Map<String, Position> positionsCache,
                                     Map<String, SousPosition> cache) {
        if (code.length() != 6) {
            logger.warn("Invalid SousPosition code length at row {}: {}", row.getRowNum(), code);
            return;
        }

        String positionCode = code.substring(0, 4);
        Position position = getOrCreatePosition(sheet,row, positionCode, positionsCache);
        if (position == null) return;

        if (!code.startsWith(positionCode)) {
            logger.warn("SousPosition {} doesn't match Position {} at row {}",
                    code, positionCode, row.getRowNum());
        }

        SousPosition sousPosition = cache.get(code);
        if (sousPosition == null) {
            sousPosition = sousPositionRespositry.findBycodeSousPosition(code);
            if (sousPosition == null) {
                sousPosition = new SousPosition();
                sousPosition.setCodeSousPosition(code);
                sousPosition.setPosition(position);

                // Set libellé avant sauvegarde
                String libelle = getMergedLibelle(sheet, row);
                if (!libelle.isEmpty()) {
                    sousPosition.setLibelleSousPosition(libelle);
                }

                sousPosition = sousPositionRespositry.save(sousPosition);
            }
            cache.put(code, sousPosition);
        }

        // Màj libellé seulement si différent
        String newLibelle = getMergedLibelle(sheet, row);
        if (!newLibelle.isEmpty() && !newLibelle.equals(sousPosition.getLibelleSousPosition())) {
            sousPosition.setLibelleSousPosition(newLibelle);
            sousPositionRespositry.save(sousPosition);
        }
    }

    private void processNC(Sheet sheet,Row row, String ncCode,
                           Map<String, SousPosition> spCache,
                           Map<String, NC> ncCache) {
        if (ncCode.length() != 8) {
            logger.warn("Invalid NC code length at row {}: {}", row.getRowNum(), ncCode);
            return;
        }

        String sousPositionCode = ncCode.substring(0, 6);
        SousPosition sousPosition = spCache.get(sousPositionCode);
        if (sousPosition == null) {
            sousPosition = sousPositionRespositry.findBycodeSousPosition(sousPositionCode);
            if (sousPosition == null) {
                logger.error("SousPosition not found for NC {} at row {}", sousPositionCode, row.getRowNum());
                return;
            }
            spCache.put(sousPositionCode, sousPosition);
        }

        NC nc = ncCache.get(ncCode);
        if (nc == null) {
            nc = ncRepository.findByCodeNCombinee(ncCode);
            if (nc == null) {
                nc = new NC();
                nc.setCodeNCombinee(ncCode);
                nc.setSousPosition(sousPosition);

                // Set libellé avant sauvegarde
                String libelle = getMergedLibelle(sheet, row);
                if (!libelle.isEmpty()) {
                    nc.setLibelleNC(libelle);
                }

                nc = ncRepository.save(nc);
            }
            ncCache.put(ncCode, nc);
        }

        String newLibelle = getMergedLibelle(sheet, row);
        if (!newLibelle.isEmpty() && !newLibelle.equals(nc.getLibelleNC())) {
            nc.setLibelleNC(newLibelle);
            ncRepository.save(nc);
        }
    }

    private void processTaric(Sheet sheet, Row row, String taricCode, String suffixCode,
                              Map<String, NC> ncCache, Map<String, TARIC> taricCache,
                              Map<String, Suffix> suffixCache) {
        if (taricCode.length() != 10) {
            logger.warn("Invalid TARIC code length at row {}: {}", row.getRowNum(), taricCode);
            return;
        }

        String ncCode = taricCode.substring(0, 8);
        NC nc = ncCache.get(ncCode);
        if (nc == null) {
            nc = ncRepository.findByCodeNCombinee(ncCode);
            if (nc == null) {
                logger.error("NC not found for TARIC {} at row {}", ncCode, row.getRowNum());
                return;
            }
            ncCache.put(ncCode, nc);
        }

        TARIC taric = taricCache.get(taricCode);
        if (taric == null) {
            taric = taricRepository.findByCodeNomenclature(taricCode);
            if (taric == null) {
                taric = new TARIC();
                taric.setCodeNomenclature(taricCode);
                taric.setNomenclatureCombinee(nc);
                Suffix suffix = getOrCreateSuffix(suffixCode, suffixCache);
                if (suffix != null) {
                    taric.setSuffix(suffix);
                }

                // Set libellé avant sauvegarde
                String libelle = getMergedLibelle(sheet, row);
                if (!libelle.isEmpty()) {
                    Description description = new Description();
                    description.setDescription(libelle);
                    description.setStatus("1");
                    description.setTaric(taric);
                    taric.getDescriptions().add(description);
                }
                taric = taricRepository.save(taric);
            }
            taricCache.put(taricCode, taric);
        }
        else {
            Suffix suffix = getOrCreateSuffix(suffixCode, suffixCache);
            if (suffix != null && (taric.getSuffix() == null || !taric.getSuffix().equals(suffix))) {
                taric.setSuffix(suffix);
                taricRepository.save(taric);
            }
        }
        String newLibelle = getMergedLibelle(sheet, row);


        if (!newLibelle.isEmpty() ) {
            Description description = new Description();
            description.setDescription(newLibelle);
            description.setStatus("1");
            description.setTaric(taric);
            taric.getDescriptions().add(description);
            taricRepository.save(taric);
        }
    }
    private Suffix getOrCreateSuffix(String suffixCode, Map<String, Suffix> cache) {
        if (suffixCode == null || suffixCode.isEmpty()) return null;

        if (cache.containsKey(suffixCode)) {
            return cache.get(suffixCode);
        }

        Suffix suffix = suffixRepository.findByCodeSuffix(suffixCode);
        if (suffix == null) {
            suffix = new Suffix();
            suffix.setCodeSuffix(suffixCode);
            suffix.setDeclarable(false);
            suffix.setNational(true);
            suffix = suffixRepository.save(suffix);
        }
        cache.put(suffixCode, suffix);
        return suffix;
    }

    // Utilities ----------------------------------------------------------------
    private String cleanPositionCode(String value) {
        if (value == null) return null;
        String cleaned = value.replaceAll("[^0-9]", "");
        return cleaned.length() == 4 ? cleaned : null;
    }

    private boolean isHeaderRow(Row row) {
        for (int i = 0; i < 5; i++) {
            Cell cell = row.getCell(i);
            if (cell != null) {
                String value = getCellValue(cell);
                if (value != null) {
                    for (String keyword : HEADER_KEYWORDS) {
                        if (value.contains(keyword)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private String getCellValue(Row row, int cellIndex) {
        if (row == null) return null;
        Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        return getCellValue(cell);
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return null;

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    double numValue = cell.getNumericCellValue();
                    if (numValue == (int) numValue) {
                        return String.valueOf((int) numValue);
                    } else {
                        return String.valueOf(numValue);
                    }
                }
            case BLANK:
                return null;
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return null;
        }
    }
    // 🔧 Nouvelle méthode utilitaire pour concaténer les libellés multi-lignes
    private String getMergedLibelle(Sheet sheet, Row currentRow) {
        StringBuilder libelleBuilder = new StringBuilder();

        int currentRowNum = currentRow.getRowNum();
        int totalRows = sheet.getLastRowNum();

        while (currentRowNum <= totalRows) {
            Row row = sheet.getRow(currentRowNum);
            if (row == null) break;

            // Pour les lignes suivantes uniquement
            if (currentRowNum != currentRow.getRowNum()) {
                String cellB = getCellValue(row, 1); // Colonne B
                String cellH = getCellValue(row, 7); // Colonne H

                boolean colBNotEmpty = cellB != null && !cellB.trim().isEmpty();
                boolean colHEmpty = cellH == null || cellH.trim().isEmpty();

                // Arrêter si colonne B a une valeur OU si colonne H est vide
                if (colBNotEmpty || colHEmpty) break;
            }

            // Lire le libellé colonne H (index 7)
            String libellePart = getCellValue(row, 7);
            if (libellePart != null && !libellePart.trim().isEmpty()) {
                if (!libelleBuilder.isEmpty()) libelleBuilder.append(" ");
                libelleBuilder.append(libellePart.trim());
            }

            currentRowNum++;
        }

        return libelleBuilder.toString().trim();
    }



}