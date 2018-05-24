package com.prometheus.sisyphus.uaa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author wushaoyong
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "tbl_thoth_uaa_bootstrap")
public class UaaBootstrap extends AbstractAuditingEntity {

    public UaaBootstrap() {
    }

    public UaaBootstrap(Long userId, Long tenantId) {
        this.tenantId = tenantId;
        this.userId = userId;
    }

    public UaaBootstrap(Long userId, Float version, Long tenantId) {
        this.tenantId = tenantId;
        this.userId = userId;
        this.version = version;
    }

    private static final long serialVersionUID = -397616725584876481L;
    @Id
    @GeneratedValue
    private Long id;
    /**
     * 租户Id
     */
    private Long tenantId;

    private Long userId;

    private Float version;
}
