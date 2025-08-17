package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.NotesDto;

public interface NotesService {
    NotesDto addNotesToTaric(Long idTaric, NotesDto notesDto);

}
