package com.zhongjinlang.dao;

import com.zhongjinlang.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: zhongjinlang
 * @date: Created in 2019/4/30 0:19
 * @description:
 */
public interface UserDao extends JpaRepository<User, Integer> {

    User findByMobile(String mobile);
}
