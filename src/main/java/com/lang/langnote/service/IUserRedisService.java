package com.lang.langnote.service;

import com.lang.langnote.entity.UserTokenEntity;

public interface IUserRedisService {

    void saveToken(String userId, UserTokenEntity userTokenEntity) throws Exception;

    String getToken(String userId) throws Exception;

    void removeToken(String userId) throws Exception;
}
