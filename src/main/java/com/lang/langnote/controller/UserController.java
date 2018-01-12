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
    public ResultVO login(UserLoginEntity loginEntity) throws Exception {
        return ResultVOUtil.success(service.userLogin(loginEntity));
    }


    /**
     * 获取个人资料
     *
     * @param infoDTO
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/info")
    public ResultVO info(UserInfoDTO infoDTO) throws Exception {
        return ResultVOUtil.success(service.userInfo(infoDTO));
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
    public ResultVO updatePwd(UserInfoDTO infoDTO) throws Exception {
        service.updatePwd(infoDTO);
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
    public ResultVO updateProfile(UserInfoDTO infoDTO) throws Exception {
        service.updateProfile(infoDTO);
        return ResultVOUtil.success();
    }


    /**
     * 该接口非必要
     * 请求参数（必要）
     * userId
     *
     * @param infoDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/logout")
    public ResultVO logout(UserInfoDTO infoDTO) throws Exception {
        service.userLogout(infoDTO);
        return ResultVOUtil.success();
    }


}
