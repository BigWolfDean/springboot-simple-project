package com.lang.langnote.repository;

import com.lang.langnote.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfoEntity, String> {

}
