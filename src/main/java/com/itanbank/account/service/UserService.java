package com.itanbank.account.service;

import com.itanbank.account.domain.app.User;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 用户Service
 * Created by dongdongshi on 16/1/15.
 */
public interface UserService {

    List<User> findAll();

    User findUserById(Long id);

    User login(String username, String password);

    void update(User user);

    void deleteUser(Long id);

    User findUserByUsername(String username);


    /**
     * 添加用户
     * @param user
     * @param roleIds
     */
    void addUser(User user, Long[] roleIds);

    /**
     * 编辑用户
     * @param user
     * @param roleIds
     */
    void editUser(User user, Long[] roleIds);

    /**
     * 分页查询
     * @param example
     * @param page
     * @return
     */
    public Page<User> findPage(User example, Pageable page);

    /**
     * 得到登陆剩余次数
     * @param user
     * @return
     */
    int getLoginSurplusNums(User user);

    /**
     * 重置登陆错误的次数为0
     * @param username
     */
    void resetFailNums(String username);

    /**
     * 更新登陆错误的次数+1
     * @param username
     */
    User updateFailNums(String username);
}
