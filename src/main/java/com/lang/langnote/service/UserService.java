package com.lang.langnote.service;

import com.lang.langnote.dto.UserInfoDTO;
import com.lang.langnote.vo.UserInfoVO;
import com.lang.langnote.entity.UserInfoEntity;
import com.lang.langnote.entity.UserLoginEntity;

public interface UserService {


    UserInfoVO userRegister(UserInfoEntity userInfoEntity) throws Exception;

    UserInfoVO userLogin(UserLoginEntity loginEntity) throws Exception;

    void updatePwd(UserInfoDTO userInfoDTO) throws Exception;

    void updateProfile(UserInfoDTO userInfoDTO) throws Exception;

    UserInfoVO userInfo(UserInfoDTO userInfoDTO) throws Exception;

    void userLogout(UserInfoDTO userInfoDTO) throws Exception;

}
