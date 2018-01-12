package com.lang.langnote.service.impl;

import com.lang.langnote.entity.UserTokenEntity;
import com.lang.langnote.redis.IRedisService;
import com.lang.langnote.service.IUserRedisService;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl extends IRedisService<UserTokenEntity> implements IUserRedisService {


    private static final String USER_REDIS_KEY = "USER_REDIS_KEY";


    @Override
    protected String getRedisKey() {
        return USER_REDIS_KEY;
    }


    @Override
    public void saveToken(String userId, UserTokenEntity tokenEntity) throws Exception {
        putString(userId, tokenEntity.getToken(), -1);
    }

    @Override
    public String getToken(String userId) throws Exception {
        return getString(userId);
    }

    @Override
    public void removeToken(String userId) throws Exception {
        removeString(userId);
    }
}
