package com.prometheus.sisyphus.uaa.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by tommy on 17/11/25.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@javax.persistence.Table(name = "tbl_thoth_uaa_tenant")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UaaTenant extends AbstractAuditingEntity {
    private static final long serialVersionUID = 5812944239462501390L;
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String domain;

    private String contacts;

    private String telephone;

    private String email;

    private String qq;

    private String weixin;

    private Boolean status;

    private String clientId;

    private String clientSecret;
}
