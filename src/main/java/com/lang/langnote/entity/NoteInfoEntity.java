package com.lang.langnote.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
@Table(name = "note_info")
public class NoteInfoEntity {

    @Id
    @GeneratedValue
    private Long noteId;

    private String userId;

    private String noteTitle;

    private String noteContent;

    @UpdateTimestamp
    private Date updateTime;


    public NoteInfoEntity() {
        
    }
}
