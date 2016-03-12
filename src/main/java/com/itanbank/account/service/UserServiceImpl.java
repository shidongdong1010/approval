package com.itanbank.account.service;

import com.itanbank.account.domain.app.User;
import com.itanbank.account.domain.app.UserRole;
import com.itanbank.account.domain.app.enums.YesOrNoEnum;
import com.itanbank.account.repository.app.UserRepository;
import com.itanbank.account.repository.app.UserRoleRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.epbcommons.transformation.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;

/**
 *
 * Created by dongdongshi on 16/1/12.
 */
@Service
public class UserServiceImpl implements UserService {

    // 登陆最多错误次数，默认为5
    @Value("${login.error.max.nums}")
    private int loginErrorMaxNums = 5;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    @Override
    public User findUserById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public void update(User user){
        userRepository.save(user);
    }


    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findOne(id);
        user.setEnabled(YesOrNoEnum.Y.getCode());
        update(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    /**
     * 添加用户
     * @param user
     * @param roleIds
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    @Override
    public void addUser(User user, Long[] roleIds){
        // 设置密码
        BCryptPasswordEncoder bc=new BCryptPasswordEncoder(4);
        user.setPassword(bc.encode(user.getPassword()));
        user.setLoginErrorCount(0);
        // 保存用户
        userRepository.save(user);

        // 保存用户与角色关系
        if(roleIds != null) {
            List<UserRole> userRoles = new ArrayList<>();
            for(Long roleId : roleIds) {
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(roleId);
                userRoles.add(userRole);
            }
            userRoleRepository.save(userRoles);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    @Override
    public void editUser(User user, Long[] roleIds) {
        userRepository.save(user);

        List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
        // 保存用户与角色关系
        if(roleIds != null) {
            // 删除去掉的角色
            for(UserRole ur : userRoles){
                if(!ArrayUtils.contains(roleIds, ur.getRoleId())){
                    userRoleRepository.delete(ur);
                }
            }

            // 添加新的角色
            for(Long roleId : roleIds) {
                boolean exists = false;
                for(UserRole ur: userRoles) {
                    if (ur.getRoleId().equals(roleId)) {
                        exists = true;
                        break;
                    }
                }
                if(!exists){
                    UserRole userRole = new UserRole();
                    userRole.setUserId(user.getId());
                    userRole.setRoleId(roleId);
                    userRoleRepository.save(userRole);
                }
            }
        }
    }


    @Override
    public Page<User> findPage(final User example, Pageable page){
        return userRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> params = new ArrayList<Predicate>();
                if(StringUtils.isNotBlank(example.getUsername())){
                    Path<String> usernamePath = root.get("username");
                    params.add(criteriaBuilder.like(usernamePath, "%"+example.getUsername()+"%"));
                }
                if(StringUtils.isNotBlank(example.getFullname())){
                    Path<String> fullnamePath = root.get("fullname");
                    params.add(criteriaBuilder.like(fullnamePath, "%"+example.getFullname()+"%"));
                }
                Predicate[] predicates = new Predicate[params.size()];
                criteriaQuery.where(params.toArray(predicates));

                /*Path<String> usernamePath = root.get("id");
                criteriaQuery.orderBy(criteriaBuilder.asc(usernamePath));*/

                return null;
            }
        }, page);
    }


    /**
     * 得到登陆剩余次数
     * @param user
     * @return
     */
    @Override
    public int getLoginSurplusNums(User user){
        String lastLoginTime = DateUtil.getDate(user.getLastLoginTime());
        String currentDate = DateUtil.getDate(new Date());
        if(currentDate.equals(currentDate)) {
            return loginErrorMaxNums - user.getLoginErrorCount();
        }
        return 0;
    }

    /**
     * 重置登陆错误的次数为0
     * @param username
     */
    @Override
    public void resetFailNums(String username){
        User user = this.findUserByUsername(username);
        if(user != null && user.getId() != null) {
            user.setLoginErrorCount(0);
            user.setLastLoginTime(new Date());
            update(user);
        }
    }

    /**
     * 更新登陆错误的次数+1
     * @param username
     */
    @Override
    public User updateFailNums(String username) {
        User user = this.findUserByUsername(username);
        if(user != null && user.getId() != null) {
            int errorCount = user.getLoginErrorCount() == null ? 0 : user.getLoginErrorCount() ;
            user.setLoginErrorCount(errorCount + 1);
            user.setLastLoginTime(new Date());

            if(user.getLoginErrorCount() >= loginErrorMaxNums){
                user.setLocked(YesOrNoEnum.Y.getCode());
            }
            update(user);
        }
        return user;
    }
}
