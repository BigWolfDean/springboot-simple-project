package com.lang.langnote.service;

import com.lang.langnote.dto.UserInfoDTO;
import com.lang.langnote.vo.UserInfoVO;
import com.lang.langnote.entity.UserInfoEntity;
import com.lang.langnote.entity.UserLoginEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {


    UserInfoVO userRegister(UserInfoEntity userInfoEntity) throws Exception;

    UserInfoVO userLogin(UserLoginEntity loginEntity, HttpServletResponse servletResponse) throws Exception;

    void updatePwd(HttpServletRequest request, UserInfoDTO userInfoDTO) throws Exception;

    void updateProfile(HttpServletRequest request, UserInfoDTO userInfoDTO) throws Exception;

    UserInfoVO userInfo(HttpServletRequest request) throws Exception;

    void userLogout(HttpServletRequest request) throws Exception;

}
