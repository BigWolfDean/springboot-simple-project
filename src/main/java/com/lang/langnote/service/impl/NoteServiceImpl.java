package com.lang.langnote.service.impl;

import com.lang.langnote.converter.NoteEntity2NoteDTOConverter;
import com.lang.langnote.enums.ResultEnum;
import com.lang.langnote.exception.NoteException;
import com.lang.langnote.vo.NoteInfoVO;
import com.lang.langnote.dto.UserAuthorizeDTO;
import com.lang.langnote.dto.UserNoteDTO;
import com.lang.langnote.entity.NoteInfoEntity;
import com.lang.langnote.repository.NoteRepository;
import com.lang.langnote.service.IUserRedisService;
import com.lang.langnote.service.NoteService;
import com.lang.langnote.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
public class NoteServiceImpl implements NoteService {


    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private IUserRedisService userRedisService;


    /**
     * 获取笔记
     *
     * @param pageable
     * @return
     * @throws Exception
     */
    @Override
    public Page<NoteInfoVO> noteList(UserAuthorizeDTO authorizeDTO, Pageable pageable) throws Exception {

        //用户id必传
        if (StringUtils.isEmpty(authorizeDTO.getUserId())) {
            throw new NoteException(ResultEnum.USERID_NON_ERROR);
        }

        //token必传
        if (StringUtils.isEmpty(authorizeDTO.getToken())) {
            throw new NoteException(ResultEnum.TOKEN_NON_ERROR);
        }

        //判断用户token
        checkToken(authorizeDTO.getToken(), authorizeDTO.getUserId());

        Page<NoteInfoEntity> noteInfoPage = noteRepository.findByUserIdOrderByUpdateTimeDesc(authorizeDTO.getUserId(), pageable);

        List<NoteInfoVO> noteInfoVOList = NoteEntity2NoteDTOConverter.convert(noteInfoPage.getContent());

        return new PageImpl<>(noteInfoVOList, pageable, noteInfoPage.getTotalElements());
    }

    @Override
    public NoteInfoVO updateNote(UserNoteDTO noteDTO) throws Exception {

        //用户id必传
        if (StringUtils.isEmpty(noteDTO.getUserId())) {
            throw new NoteException(ResultEnum.USERID_NON_ERROR);
        }

        //token必传
        if (StringUtils.isEmpty(noteDTO.getToken())) {
            throw new NoteException(ResultEnum.TOKEN_NON_ERROR);
        }

        //标题必传
        if (StringUtils.isEmpty(noteDTO.getNoteTitle())) {
            throw new NoteException(ResultEnum.NOTE_TITLE_NON_ERROR);
        }

        //内容必传
        if (StringUtils.isEmpty(noteDTO.getNoteTitle())) {
            throw new NoteException(ResultEnum.NOTE_CONTENT_NON_ERROR);
        }

        //noteId必传
        if (StringUtils.isEmpty(noteDTO.getNoteId()) || noteDTO.getNoteId() == 0) {
            throw new NoteException(ResultEnum.NOTE_ID_NON_ERROR);
        }

        //判断用户token
        checkToken(noteDTO.getToken(), noteDTO.getUserId());

        NoteInfoEntity newInfoEntity = new NoteInfoEntity();
        Util.copyPropertiesIgnoreNull(noteDTO, newInfoEntity);

        NoteInfoEntity infoEntity = noteRepository.save(newInfoEntity);

        //数据转换
        NoteInfoVO infoDTO = new NoteInfoVO();
        Util.copyPropertiesIgnoreNull(infoEntity, infoDTO);

        log.error("这是NoteInfoEntity=>", infoEntity);
        log.error("NoteInfoVO=>", infoDTO);


        return infoDTO;
    }

    @Override
    public NoteInfoVO addNote(UserNoteDTO userNoteDTO) throws Exception {

        //用户id必传
        if (StringUtils.isEmpty(userNoteDTO.getUserId())) {
            throw new NoteException(ResultEnum.USERID_NON_ERROR);
        }

        //token必传
        if (StringUtils.isEmpty(userNoteDTO.getToken())) {
            throw new NoteException(ResultEnum.TOKEN_NON_ERROR);
        }

        //标题必传
        if (StringUtils.isEmpty(userNoteDTO.getNoteTitle())) {
            throw new NoteException(ResultEnum.NOTE_TITLE_NON_ERROR);
        }

        //内容必传
        if (StringUtils.isEmpty(userNoteDTO.getNoteTitle())) {
            throw new NoteException(ResultEnum.NOTE_CONTENT_NON_ERROR);
        }

        //判断用户token
        checkToken(userNoteDTO.getToken(), userNoteDTO.getUserId());

        NoteInfoEntity newInfoEntity = noteRepository.findOne(userNoteDTO.getNoteId());

        Util.copyPropertiesIgnoreNull(userNoteDTO, newInfoEntity);

        NoteInfoEntity infoEntity = noteRepository.save(newInfoEntity);

        //数据转换
        NoteInfoVO infoDTO = new NoteInfoVO();
        Util.copyPropertiesIgnoreNull(infoEntity, infoDTO);

        log.error("这是NoteInfoEntity=>", infoEntity);
        log.error("NoteInfoVO=>", infoDTO);


        return infoDTO;
    }

    @Override
    public void delNote(UserNoteDTO userNoteDTO) throws Exception {

        //用户id必传
        if (StringUtils.isEmpty(userNoteDTO.getUserId())) {
            throw new NoteException(ResultEnum.USERID_NON_ERROR);
        }

        //token必传
        if (StringUtils.isEmpty(userNoteDTO.getToken())) {
            throw new NoteException(ResultEnum.TOKEN_NON_ERROR);
        }

        //noteId必传
        if (StringUtils.isEmpty(userNoteDTO.getNoteId()) || userNoteDTO.getNoteId() == 0) {
            throw new NoteException(ResultEnum.NOTE_ID_NON_ERROR);
        }

        //判断用户token
        checkToken(userNoteDTO.getToken(), userNoteDTO.getUserId());

        noteRepository.delete(userNoteDTO.getNoteId());

    }


    /**
     * 判断用户token
     *
     * @throws Exception
     */
    private void checkToken(String token, String userId) throws Exception {

        Util.checkToken(token, userId, userRedisService);
    }


}
