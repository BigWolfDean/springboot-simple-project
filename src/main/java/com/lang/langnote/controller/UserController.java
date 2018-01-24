package com.lang.langnote.controller;

import com.lang.langnote.dto.UserInfoDTO;
import com.lang.langnote.vo.ResultVO;
import com.lang.langnote.entity.UserInfoEntity;
import com.lang.langnote.entity.UserLoginEntity;
import com.lang.langnote.service.UserService;
import com.lang.langnote.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping
@RestController
public class UserController {


    @Autowired
    private UserService service;


    /**
     * 请求参数（必传）
     * userId userName userPwd
     *
     * @param infoEntity
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/register")
    public ResultVO register(UserInfoEntity infoEntity) throws Exception {
        return ResultVOUtil.success(service.userRegister(infoEntity));
    }

    /**
     * 请求参数（必传）
     * userId userPwd
     *
     * @param loginEntity
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/login")
    public ResultVO login(UserLoginEntity loginEntity, HttpServletResponse servletResponse) throws Exception {
        return ResultVOUtil.success(service.userLogin(loginEntity, servletResponse));
    }


    /**
     * 获取个人资料
     *
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/info")
    public ResultVO info(HttpServletRequest servletRequest) throws Exception {
        return ResultVOUtil.success(service.userInfo(servletRequest));
    }


    /**
     * 请求参数（必传）
     * userId oldPwd newPwd token
     *
     * @param infoDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/updatePwd")
    public ResultVO updatePwd(HttpServletRequest servletRequest, UserInfoDTO infoDTO) throws Exception {
        service.updatePwd(servletRequest, infoDTO);
        return ResultVOUtil.success();
    }


    /**
     * 请求参数（必传）
     * userId token userProfile
     *
     * @param infoDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/updateProfile")
    public ResultVO updateProfile(HttpServletRequest servletRequest, UserInfoDTO infoDTO) throws Exception {
        service.updateProfile(servletRequest, infoDTO);
        return ResultVOUtil.success();
    }


    /**
     * 该接口非必要
     * 请求参数（必要）
     * userId
     *
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/logout")
    public ResultVO logout(HttpServletRequest servletRequest) throws Exception {
        service.userLogout(servletRequest);
        return ResultVOUtil.success();
    }


}
