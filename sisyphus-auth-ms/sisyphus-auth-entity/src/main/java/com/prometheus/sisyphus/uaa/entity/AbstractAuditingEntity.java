package com.prometheus.sisyphus.uaa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * Base abstract class for entities which will hold definitions for created, last modified by and created,
 * last modified by date.
 * 添加操作审计
 * Created by sunliang on 2017/1/26.
 */
@MappedSuperclass
@Audited
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 2234417181549790845L;

    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    @JsonIgnore
    private String createdBy;

    @LastModifiedBy
    @Column(name = "last_modified_by", length = 50)
    @JsonIgnore
    private String lastModifiedBy;
    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;
    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

}
