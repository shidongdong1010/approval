package com.itanbank.account.repository.app;

import com.itanbank.account.domain.app.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by dongdongshi on 16/2/18.
 */
public interface ResourceRepository extends JpaRepository<Resource, Long>,JpaSpecificationExecutor<Resource> {

    @Query(value = "SELECT a.* FROM s_resource a WHERE EXISTS( SELECT  1 FROM s_resource_role t WHERE t.role_id = ?1 AND t.resource_id = a.id)", nativeQuery = true)
    public List<Resource> findByRoleId(Long roleId);

}

