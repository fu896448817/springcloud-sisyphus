package com.prometheus.sisyphus.uaa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by tommy on 2017/11/26.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@javax.persistence.Table(name = "tbl_thoth_uaa_role")
public class UaaRole extends AbstractAuditingEntity{
    private static final long serialVersionUID = 6631018847040979144L;
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String value;

    @JsonIgnore
    @ManyToMany(targetEntity = UaaAuthority.class,fetch = FetchType.EAGER)
    @BatchSize(size = 20)
    private Set<UaaAuthority> authorities = new HashSet<>();
}
