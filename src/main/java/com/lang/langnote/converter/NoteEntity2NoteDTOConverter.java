package com.lang.langnote.converter;

import com.lang.langnote.vo.NoteInfoVO;
import com.lang.langnote.entity.NoteInfoEntity;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 转换器
 */
public class NoteEntity2NoteDTOConverter {

    public static NoteInfoVO convert(NoteInfoEntity infoEntity) {
        NoteInfoVO infoDTO = new NoteInfoVO();
        BeanUtils.copyProperties(infoEntity, infoDTO);
        return infoDTO;
    }


    public static List<NoteInfoVO> convert(List<NoteInfoEntity> noteInfoEntityList) {
        return noteInfoEntityList.stream().map(
                e -> convert(e)
        ).collect(Collectors.toList());
    }
}
