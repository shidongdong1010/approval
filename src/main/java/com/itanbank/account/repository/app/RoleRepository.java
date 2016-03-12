package com.itanbank.account.repository.app;

import com.itanbank.account.domain.app.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by dongdongshi on 16/1/12.
 */
public interface RoleRepository extends JpaRepository<Role,Long> {

    /**
     * 查询用记的所有角色
     * @param userId 用户ID
     * @return
     */
    @Query(value = "select b.* from s_role b where exists (select 1 from s_user_role a where a.user_id = ?1 and a.role_id = b.id)", nativeQuery = true)
    public List<Role> findByUserId(Long userId);
}