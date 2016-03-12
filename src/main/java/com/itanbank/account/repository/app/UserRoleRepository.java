package com.itanbank.account.repository.app;

import com.itanbank.account.domain.app.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by dongdongshi on 16/2/2.
 */
public interface UserRoleRepository extends JpaRepository<UserRole,Long>, JpaSpecificationExecutor<UserRole> {

    @Query("select a from UserRole a where a.userId = ?1")
    List<UserRole> findByUserId(Long userId);
}
