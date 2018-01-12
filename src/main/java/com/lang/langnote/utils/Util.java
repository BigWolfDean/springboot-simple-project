package com.lang.langnote.utils;

import com.lang.langnote.dto.UserInfoDTO;
import com.lang.langnote.enums.ResultEnum;
import com.lang.langnote.exception.NoteException;
import com.lang.langnote.service.IUserRedisService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class Util {


    /**
     * 校验token
     */
    public static void checkToken(UserInfoDTO userInfoDTO, String token) throws Exception {
        //校验token
        if (StringUtils.isEmpty(token)) {
            throw new NoteException(ResultEnum.TOKEN_CHECK_ERROR);
        }
        if (!token.equals(userInfoDTO.getToken())) {
            throw new NoteException(ResultEnum.TOKEN_CHECK_ERROR);
        }
    }

    /**
     * 校验token
     */
    public static void checkToken(String token, String userId, IUserRedisService redisService) throws Exception {
        //校验token
        if (StringUtils.isEmpty(token)) {
            throw new NoteException(ResultEnum.TOKEN_CHECK_ERROR);
        }
        if (!token.equals(redisService.getToken(userId))) {
            throw new NoteException(ResultEnum.TOKEN_CHECK_ERROR);
        }
    }


    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 防止拷贝null对象
     *
     * @param src
     * @param target
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }


}
