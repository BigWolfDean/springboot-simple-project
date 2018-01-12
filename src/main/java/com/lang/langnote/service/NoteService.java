package com.lang.langnote.service;

import com.lang.langnote.vo.NoteInfoVO;
import com.lang.langnote.dto.UserAuthorizeDTO;
import com.lang.langnote.dto.UserNoteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoteService {


    Page<NoteInfoVO> noteList(UserAuthorizeDTO authorizeDTO, Pageable pageable) throws Exception;

    NoteInfoVO updateNote(UserNoteDTO userNoteDTO) throws Exception;

    NoteInfoVO addNote(UserNoteDTO userNoteDTO) throws Exception;

    void delNote(UserNoteDTO userNoteDTO) throws Exception;


}
