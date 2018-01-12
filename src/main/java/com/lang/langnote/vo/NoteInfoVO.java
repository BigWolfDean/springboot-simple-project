package com.lang.langnote.vo;

import lombok.Data;

import java.util.Date;

@Data
public class NoteInfoVO {


    private Long noteId;

    private String noteContent;

    private String noteTitle;

    private Date updateTime;

}
