package com.lang.langnote.dto;

import lombok.Data;

@Data
public class NoteReqDTO {

    private String userId;

    private String token;

    private int page;

    private int size;
}
