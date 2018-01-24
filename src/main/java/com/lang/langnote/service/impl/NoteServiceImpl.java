package com.lang.langnote.service.impl;

import com.lang.langnote.converter.NoteEntity2NoteDTOConverter;
import com.lang.langnote.dto.UserNoteDTO;
import com.lang.langnote.entity.NoteInfoEntity;
import com.lang.langnote.enums.ResultEnum;
import com.lang.langnote.exception.NoteException;
import com.lang.langnote.repository.NoteRepository;
import com.lang.langnote.repository.UserRepository;
import com.lang.langnote.service.IUserRedisService;
import com.lang.langnote.service.NoteService;
import com.lang.langnote.utils.CookieUtil;
import com.lang.langnote.utils.Util;
import com.lang.langnote.vo.NoteInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Slf4j
public class NoteServiceImpl implements NoteService {


    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private IUserRedisService userRedisService;

    @Autowired
    private UserRepository userRepository;


    /**
     * 获取日记
     *
     * @param pageable
     * @return
     * @throws Exception
     */
    @Override
    public Page<NoteInfoVO> noteList(HttpServletRequest request, Pageable pageable) throws Exception {
        //判断用户是否存在
        checkUser(getUserId(request));

        //判断用户token
        checkToken(getToken(request), getUserId(request));

        Page<NoteInfoEntity> noteInfoPage = noteRepository.findByUserIdOrderByUpdateTimeDesc(getUserId(request), pageable);

        List<NoteInfoVO> noteInfoVOList = NoteEntity2NoteDTOConverter.convert(noteInfoPage.getContent());

        return new PageImpl<>(noteInfoVOList, pageable, noteInfoPage.getTotalElements());
    }

    /**
     * 更新日记
     *
     * @param request
     * @param userNoteDTO
     * @return
     * @throws Exception
     */
    @Override
    public NoteInfoVO updateNote(HttpServletRequest request, UserNoteDTO userNoteDTO) throws Exception {

        //判断用户是否存在
        checkUser(getUserId(request));

        //标题必传
        if (StringUtils.isEmpty(userNoteDTO.getNoteTitle())) {
            throw new NoteException(ResultEnum.NOTE_TITLE_NON_ERROR);
        }

        //内容必传
        if (StringUtils.isEmpty(userNoteDTO.getNoteTitle())) {
            throw new NoteException(ResultEnum.NOTE_CONTENT_NON_ERROR);
        }

        //noteId必传
        if (StringUtils.isEmpty(userNoteDTO.getNoteId()) || userNoteDTO.getNoteId() == 0) {
            throw new NoteException(ResultEnum.NOTE_ID_NON_ERROR);
        }

        //判断用户token
        checkToken(getToken(request), getUserId(request));

        NoteInfoEntity newInfoEntity = new NoteInfoEntity();
        Util.copyPropertiesIgnoreNull(userNoteDTO, newInfoEntity);

        NoteInfoEntity infoEntity = noteRepository.save(newInfoEntity);

        //数据转换
        NoteInfoVO infoDTO = new NoteInfoVO();
        Util.copyPropertiesIgnoreNull(infoEntity, infoDTO);

        log.error("这是NoteInfoEntity=>", infoEntity);
        log.error("NoteInfoVO=>", infoDTO);


        return infoDTO;
    }

    /**
     * 添加日记
     *
     * @param request
     * @param userNoteDTO
     * @return
     * @throws Exception
     */
    @Override
    public NoteInfoVO addNote(HttpServletRequest request, UserNoteDTO userNoteDTO) throws Exception {

        //判断用户是否存在
        checkUser(getUserId(request));

        //标题必传
        if (StringUtils.isEmpty(userNoteDTO.getNoteTitle())) {
            throw new NoteException(ResultEnum.NOTE_TITLE_NON_ERROR);
        }

        //内容必传
        if (StringUtils.isEmpty(userNoteDTO.getNoteTitle())) {
            throw new NoteException(ResultEnum.NOTE_CONTENT_NON_ERROR);
        }

        //判断用户token
        checkToken(getToken(request), getUserId(request));

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

    /**
     * 删除日记
     *
     * @param request
     * @param userNoteDTO
     * @throws Exception
     */
    @Override
    public void delNote(HttpServletRequest request, UserNoteDTO userNoteDTO) throws Exception {

        //判断用户是否存在
        checkUser(getUserId(request));
        
        //noteId必传
        if (StringUtils.isEmpty(userNoteDTO.getNoteId()) || userNoteDTO.getNoteId() == 0) {
            throw new NoteException(ResultEnum.NOTE_ID_NON_ERROR);
        }

        //判断用户token
        checkToken(getToken(request), getUserId(request));

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

    /**
     * 获取Token
     *
     * @param request
     * @return
     */
    private String getToken(HttpServletRequest request) {
        Cookie cookie = CookieUtil.getCookieByName(request, "token");
        //token必传
        if (null == cookie) {
            throw new NoteException(ResultEnum.TOKEN_NON_ERROR);
        }
        return cookie.getValue();
    }

    /**
     * 获取userId
     *
     * @param request
     * @return
     */
    private String getUserId(HttpServletRequest request) {
        Cookie cookie = CookieUtil.getCookieByName(request, "userId");
        //userId必传
        if (null == cookie) {
            throw new NoteException(ResultEnum.USERID_NON_ERROR);
        }
        return cookie.getValue();
    }


    /**
     * 判断用户是否存在
     *
     * @param userId
     * @return
     */
    private void checkUser(String userId) {
        if (userRepository.findOne(userId) == null) {
            throw new NoteException(ResultEnum.USER_NOT_EXIST);
        }
    }
}
