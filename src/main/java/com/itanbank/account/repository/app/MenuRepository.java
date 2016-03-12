package com.itanbank.account.repository.app;

import com.itanbank.account.domain.app.Menu;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by dongdongshi on 16/1/18.
 */
public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {
    @Query("select u from Menu u  where u.parentId=?1")
    Page<Menu> findPage(long parentId, Pageable page);

    Page<Menu> findAll(Specification<Menu> spec, Pageable page);

    @Query("select u from Menu u  where u.parentId=?1")
    List<Menu> findMenuById(long parentId);

    @Query(value = "SELECT t.* " +
            "FROM ( " +
            " SELECT a.* " +
            " FROM " +
            "  s_menu a  " +
            " WHERE  a.sys_id = ?2 AND " +
            "  NOT EXISTS ( " +
            "    SELECT id  " +
            "    FROM " +
            "     s_resource b " +
            "    WHERE " +
            "     a.url = b.resource_content " +
            "   ) " +
            "  UNION ALL " +
            " SELECT a.* " +
            " FROM " +
            "  s_menu a " +
            "  INNER JOIN s_resource b ON a.url = b.resource_content " +
            "  INNER JOIN s_resource_role c ON b.id = c.resource_id " +
            "  INNER JOIN s_user_role d ON c.role_id = d.role_id " +
            " WHERE d.user_id = ?1 AND a.sys_id = ?2 " +
            ") t " +
            "ORDER BY t.parent_id, t.sort", nativeQuery = true)
    List<Menu> findByUserIdAndSysId(Long userId, Long sysId);
}
