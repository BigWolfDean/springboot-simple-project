package com.lang.langnote.service;

import com.lang.langnote.vo.NoteInfoVO;
import com.lang.langnote.dto.UserNoteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

public interface NoteService {


    Page<NoteInfoVO> noteList(HttpServletRequest request, Pageable pageable) throws Exception;

    NoteInfoVO updateNote(HttpServletRequest request, UserNoteDTO userNoteDTO) throws Exception;

    NoteInfoVO addNote(HttpServletRequest request, UserNoteDTO userNoteDTO) throws Exception;

    void delNote(HttpServletRequest request, UserNoteDTO userNoteDTO) throws Exception;


}
