package com.lang.langnote.repository;

import com.lang.langnote.entity.NoteInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<NoteInfoEntity, Long> {

    Page<NoteInfoEntity> findByUserIdOrderByUpdateTimeDesc(String userId, Pageable pageable);


}
