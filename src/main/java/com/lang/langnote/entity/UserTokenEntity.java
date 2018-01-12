package com.lang.langnote.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserTokenEntity implements Serializable {


    private String token;


}
