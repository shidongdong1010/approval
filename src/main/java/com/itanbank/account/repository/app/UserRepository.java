package com.itanbank.account.repository.app;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.itanbank.account.domain.app.User;

/**
 * Created by dongdongshi on 16/1/12.
 */
public interface UserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User>  {
    @Query("select u from User u where u.username=?1 and u.password=?2")
    User login(String username, String password);

    User findByUsernameAndPassword(String username, String password);

    User findUserByUsername(String username);
    
    @Query("select u from User u where u.username = ?1 and u.fullname = ?2")
    Page<User> findPage(String username, String fullname, Pageable page);
    
    Page<User> findAll(Specification<User> spec, Pageable page);
    
}
