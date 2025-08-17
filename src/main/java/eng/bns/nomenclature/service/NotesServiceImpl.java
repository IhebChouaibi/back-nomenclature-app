package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.NotesDto;
import eng.bns.nomenclature.entities.Notes;
import eng.bns.nomenclature.entities.TARIC;
import eng.bns.nomenclature.exception.CodeNotFoundException;
import eng.bns.nomenclature.mapper.NotesMapper;
import eng.bns.nomenclature.repository.NotesRepository;
import eng.bns.nomenclature.repository.TaricRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotesServiceImpl implements NotesService{
    private final NotesMapper notesMapper;
    private final NotesRepository notesRepository;
    private final TaricRepository taricRepository;
    @Override
    public NotesDto addNotesToTaric(Long idTaric, NotesDto notesDto) {
        TARIC taric = taricRepository.findById(idTaric)
                .orElseThrow(() -> new CodeNotFoundException("Code TARIC inexistant"));

        Notes notes = notesMapper.toEntity(notesDto);
        notes.setTaric(taric);

        notesRepository.save(notes);

        return notesMapper.toDto(notes);


    }
}
