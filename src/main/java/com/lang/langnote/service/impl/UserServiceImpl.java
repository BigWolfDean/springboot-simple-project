package com.lang.langnote.service.impl;

import com.lang.langnote.dto.UserInfoDTO;
import com.lang.langnote.entity.UserInfoEntity;
import com.lang.langnote.entity.UserLoginEntity;
import com.lang.langnote.entity.UserTokenEntity;
import com.lang.langnote.enums.ResultEnum;
import com.lang.langnote.exception.NoteException;
import com.lang.langnote.repository.UserRepository;
import com.lang.langnote.service.IUserRedisService;
import com.lang.langnote.service.UserService;
import com.lang.langnote.utils.SecurityUtil;
import com.lang.langnote.utils.Util;
import com.lang.langnote.vo.UserInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * lang
 * 用户Service
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserRedisService userRedisService;


    /**
     * 用户注册
     *
     * @param userInfoEntity
     * @return
     * @throws Exception
     */
    @Override
    public UserInfoVO userRegister(UserInfoEntity userInfoEntity) throws Exception {

        //用户id必传
        if (StringUtils.isEmpty(userInfoEntity.getUserId())) {
            throw new NoteException(ResultEnum.USERID_NON_ERROR);
        }

        //用户密码必传
        if (StringUtils.isEmpty(userInfoEntity.getUserPwd())) {
            throw new NoteException(ResultEnum.PASSWORD_NON_ERROR);
        }
        //用户名必传
        if (StringUtils.isEmpty(userInfoEntity.getUserName())) {
            throw new NoteException(ResultEnum.USERNAME_NON_ERROR);
        }

        if (null != userRepository.findOne(userInfoEntity.getUserId())) {
            throw new NoteException(ResultEnum.REGISTER_ERROR_HAD);
        }

        UserInfoVO result = new UserInfoVO();
        //获取请求对象
        UserInfoEntity infoEntity = userRepository.save(userInfoEntity);
        // 对象转换
        BeanUtils.copyProperties(infoEntity, result);

        return result;
    }

    /**
     * 用户登录
     *
     * @param loginEntity
     * @return
     * @throws Exception
     */
    @Override
    public UserInfoVO userLogin(UserLoginEntity loginEntity) throws Exception {

        //用户id必传
        if (StringUtils.isEmpty(loginEntity.getUserId())) {
            throw new NoteException(ResultEnum.USERID_NON_ERROR);
        }

        //用户密码必传
        if (StringUtils.isEmpty(loginEntity.getUserPwd())) {
            throw new NoteException(ResultEnum.PASSWORD_NON_ERROR);
        }

        // 获取UserInfo对象
        UserInfoEntity infoEntity = getUserInfo(loginEntity.getUserId());
        if (!infoEntity.getUserPwd().equals(loginEntity.getUserPwd())) {
            throw new NoteException(ResultEnum.LOGIN_ERROR);
        }

        //生成token
        Map<String, Object> map = new HashMap<>();
        map.put("id", infoEntity.getUserId());
        map.put("date", System.currentTimeMillis());
        map.put("name", infoEntity.getUserName());
        String token = SecurityUtil.authentication(map);

        //保存用户token到redis
        UserTokenEntity tokenEntity = new UserTokenEntity(token);
        userRedisService.saveToken(infoEntity.getUserId(), tokenEntity);

        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(infoEntity, userInfoVO);
        userInfoVO.setToken(token);

        return userInfoVO;
    }

    /**
     * 更改用户密码
     *
     * @param userInfoDTO
     * @throws Exception
     */
    @Override
    public void updatePwd(UserInfoDTO userInfoDTO) throws Exception {

        //用户id必传
        if (StringUtils.isEmpty(userInfoDTO.getUserId())) {
            throw new NoteException(ResultEnum.USERID_NON_ERROR);
        }

        //用户密码必传
        if (StringUtils.isEmpty(userInfoDTO.getOldPwd())) {
            throw new NoteException(ResultEnum.PASSWORD_NON_ERROR);
        }

        //用户新密码必传
        if (StringUtils.isEmpty(userInfoDTO.getNewPwd())) {
            throw new NoteException(ResultEnum.PASSWORD_NEW_NON_ERROR);
        }

        //token必传
        if (StringUtils.isEmpty(userInfoDTO.getToken())) {
            throw new NoteException(ResultEnum.TOKEN_NON_ERROR);
        }


        //校验token
        checkToken(userInfoDTO);

        //校验旧密码
        UserInfoEntity infoEntity = getUserInfo(userInfoDTO.getUserId());
        if (!userInfoDTO.getOldPwd().equals(infoEntity.getUserPwd())) {
            throw new NoteException(ResultEnum.PASSWORD_CHECK_ERROR);
        }

        //设置新密码
        infoEntity.setUserPwd(userInfoDTO.getNewPwd());

        userRepository.save(infoEntity);

    }

    /**
     * 修改个人简介
     *
     * @param userInfoDTO
     * @throws Exception
     */
    @Override
    public void updateProfile(UserInfoDTO userInfoDTO) throws Exception {

        //用户id必传
        if (StringUtils.isEmpty(userInfoDTO.getUserId())) {
            throw new NoteException(ResultEnum.USERID_NON_ERROR);
        }

        //token必传
        if (StringUtils.isEmpty(userInfoDTO.getToken())) {
            throw new NoteException(ResultEnum.TOKEN_NON_ERROR);
        }

        //个人简介必传
        if (StringUtils.isEmpty(userInfoDTO.getToken())) {
            throw new NoteException(ResultEnum.PROFILE_NON_ERROR);
        }


        // 校验token
        checkToken(userInfoDTO);

        //修改profile
        UserInfoEntity infoEntity = getUserInfo(userInfoDTO.getUserId());
        infoEntity.setUserProfile(userInfoDTO.getUserProfile());

        //修改数据库
        userRepository.save(infoEntity);

    }

    /**
     * 获取用户数据
     *
     * @param userInfoDTO
     * @return
     * @throws Exception
     */
    @Override
    public UserInfoVO userInfo(UserInfoDTO userInfoDTO) throws Exception {

        //用户id必传
        if (StringUtils.isEmpty(userInfoDTO.getUserId())) {
            throw new NoteException(ResultEnum.USERID_NON_ERROR);
        }

        //token必传
        if (StringUtils.isEmpty(userInfoDTO.getToken())) {
            throw new NoteException(ResultEnum.TOKEN_NON_ERROR);
        }

        // 校验token
        checkToken(userInfoDTO);

        //获取用户信息
        UserInfoEntity infoEntity = getUserInfo(userInfoDTO.getUserId());

        UserInfoVO vo = new UserInfoVO();

        //拷贝数据
        BeanUtils.copyProperties(infoEntity, vo);

        return vo;
    }

    /**
     * 退出登录,用于清除token
     *
     * @param userInfoDTO
     * @throws Exception
     */
    @Override
    public void userLogout(UserInfoDTO userInfoDTO) throws Exception {
        //用户id必传
        if (StringUtils.isEmpty(userInfoDTO.getUserId())) {
            throw new NoteException(ResultEnum.USERID_NON_ERROR);
        }
        userRedisService.removeToken(userInfoDTO.getUserId());
    }


    /**
     * 判断用户token
     *
     * @param userInfoDTO
     * @throws Exception
     */
    private void checkToken(UserInfoDTO userInfoDTO) throws Exception {
        Util.checkToken(userInfoDTO, userRedisService.getToken(userInfoDTO.getUserId()));
    }

    /**
     * 获取UserInfo实体
     *
     * @param userId
     * @return
     */
    private UserInfoEntity getUserInfo(String userId) {
        return userRepository.findOne(userId);
    }


}
