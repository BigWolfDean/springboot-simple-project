package com.lang.langnote.dto;

import lombok.Data;

@Data
public class UserNoteDTO {

    private String userId;

    private Long noteId;

    private String noteTitle;

    private String noteContent;
}
