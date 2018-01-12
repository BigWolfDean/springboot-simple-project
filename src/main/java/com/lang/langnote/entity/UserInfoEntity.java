package com.lang.langnote.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_info")
@Data
@DynamicUpdate
public class UserInfoEntity {

    @Id
    private String userId;

    private String userName;

    private String userPwd;

    private Integer userSex;

    private String userProfile;

    private String avatarUrl = "https://pic2.zhimg.com/50/v2-d757e91a15dde9792a1850aed2f1a1c8_hd.jpg";


    public UserInfoEntity() {

    }
}
