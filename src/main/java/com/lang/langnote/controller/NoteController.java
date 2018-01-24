package com.lang.langnote.controller;

import com.lang.langnote.dto.UserNoteDTO;
import com.lang.langnote.service.NoteService;
import com.lang.langnote.utils.ResultVOUtil;
import com.lang.langnote.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping
@Slf4j
public class NoteController {


    @Autowired
    private NoteService noteService;

    /**
     * 请求参数（必传）
     * userId token
     *
     * @param page
     * @param size
     * @return
     * @throws Exception
     */
    @GetMapping(value = "getNote")
    public ResultVO noteList(HttpServletRequest servletRequest,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {

        PageRequest pageRequest = new PageRequest(page - 1, size);

        return ResultVOUtil.success(noteService.noteList(servletRequest, pageRequest).getContent());

    }

    /**
     * 请求参数（必传）
     * userId token noteContent noteTitle
     *
     * @param noteDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value = "addNote")
    public ResultVO add(HttpServletRequest servletRequest, UserNoteDTO noteDTO) throws Exception {
        log.error("这是传递进来的UserNoteDTO=>{}", noteDTO.toString());
        return ResultVOUtil.success(noteService.addNote(servletRequest, noteDTO));
    }


    /**
     * 请求参数（必传）
     * userId token noteId noteContent noteTitle
     *
     * @param noteDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value = "updateNote")
    public ResultVO update(HttpServletRequest servletRequest, UserNoteDTO noteDTO) throws Exception {
        log.error("这是传递进来的UserNoteDTO=>{}", noteDTO.toString());
        return ResultVOUtil.success(noteService.updateNote(servletRequest, noteDTO));
    }


    @PostMapping(value = "deleteNote")
    public ResultVO delete(HttpServletRequest servletRequest, UserNoteDTO noteDTO) throws Exception {
        noteService.delNote(servletRequest, noteDTO);
        log.error("noteDTO => {}", noteDTO);
        return ResultVOUtil.success();
    }


}
