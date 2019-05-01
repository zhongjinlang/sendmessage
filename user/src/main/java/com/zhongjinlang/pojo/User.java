package com.zhongjinlang.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author: zhongjinlang
 * @date: Created in 2019/4/30 0:16
 * @description:
 */
@Entity
@Data
public class User {
    @Id
    private int id;
    private String username;
    private String password;
    private String mobile;
    
}
