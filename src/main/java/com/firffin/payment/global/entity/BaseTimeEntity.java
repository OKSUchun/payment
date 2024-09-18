package com.firffin.payment.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 공통으로 사용되는 매핑 정보를 정의하는 클래스
 * {@link MappedSuperclass} : 매핑 정보만 상속받는다는 의미
 * {@link EntityListeners} : 엔티티의 변화를 감지-> 엔티티와 매핑된 테이블의 데이터 조작
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime modifiedDateTime;
}