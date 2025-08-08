package eng.bns.nomenclature.web;

import eng.bns.nomenclature.service.ExcelImportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class ExcelImportController {

    private final ExcelImportService excelImportService;

    public ExcelImportController(ExcelImportService excelImportService) {
        this.excelImportService = excelImportService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "File is empty or missing. Please select a valid Excel file."));
        }

        if (!isExcelFile(file)) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                    .body(Map.of("error", "File is not an Excel file. Please select a valid Excel file."));
        }

        try {
            excelImportService.importExcelData(file);
            return ResponseEntity.ok(Map.of("message", "Excel data imported successfully."));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "An error occurred while importing the Excel data: " + e.getMessage()));
        }
    }

    private boolean isExcelFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (
                contentType.equals("application/vnd.ms-excel") ||
                        contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        );
    }}