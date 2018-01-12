package com.lang.langnote.exception;


import com.lang.langnote.enums.ResultEnum;
import lombok.Data;

@Data
public class NoteException extends RuntimeException {


    private Integer code;

    public NoteException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }


}
