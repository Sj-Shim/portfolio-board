package com.kh.board.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@ToString
public abstract class AuditingTimeEntity {
    @CreatedDate
    @Column(updatable = false, nullable = false, name = "createdDate")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(updatable = true, nullable = false, name = "modifiedDate")
    private LocalDateTime modifiedDate;
}
