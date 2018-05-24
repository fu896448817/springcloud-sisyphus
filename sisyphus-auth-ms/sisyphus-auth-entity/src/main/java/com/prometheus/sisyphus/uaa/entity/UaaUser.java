package com.prometheus.sisyphus.uaa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
//import javax.persistence.Table;

/**
 * Created by tommy on 2017/11/25.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@javax.persistence.Table(name = "tbl_thoth_uaa_user", uniqueConstraints = {
        @UniqueConstraint(name = "phoneNumber", columnNames = "phoneNumber")
})
public class UaaUser extends AbstractAuditingEntity {
    private static final long serialVersionUID = 4989309292401711788L;
    @Id
    @GeneratedValue
    private Long id;
    /**
     * 租户Id
     */
    private Long tenantId;
    @NotNull
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String username;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(length = 60)
    private String password;

    @Size(max = 50)
    @Column(length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(length = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    @Column(length = 100, unique = true)
    private String email;


    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Size(max = 20)
    private String phoneNumber;


    @JsonIgnore
    @ManyToMany(targetEntity = UaaRole.class, fetch = FetchType.EAGER)
    @BatchSize(size = 20)
    private Set<UaaRole> roles = new HashSet<>();

    @Transient
    private Set<GrantedAuthority> authorities = new HashSet<>();


    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> userAuthotities = new HashSet<>();
        for (UaaRole role : this.roles) {
            for (UaaAuthority authority : role.getAuthorities()) {
                userAuthotities.add(new SimpleGrantedAuthority(authority.getValue()));
            }
        }

        return userAuthotities;
    }
}
