package com.prometheus.sisyphus.uaa.repository;

import com.prometheus.sisyphus.uaa.entity.UaaUser;
import com.prometheus.sisyphus.uaa.repository.support.WiselyRepository;

import java.util.Optional;

/**
 * Created by sunliang on 2017/11/26.
 */
public interface UaaUserRepository extends WiselyRepository<UaaUser,Long> {
    Optional<UaaUser> findOneWithRolesByUsername(String username);
}
