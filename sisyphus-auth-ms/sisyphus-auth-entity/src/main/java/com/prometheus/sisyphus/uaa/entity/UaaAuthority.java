package com.prometheus.sisyphus.uaa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * Created by tommy on 2017/11/26.
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@javax.persistence.Table(name = "tbl_thoth_uaa_authority")
public  class UaaAuthority extends AbstractAuditingEntity{
    private static final long serialVersionUID = -6364801768795474725L;
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String value;
}
