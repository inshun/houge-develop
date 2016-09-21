package com.ysp.houge.model.entity.bean;

import java.io.Serializable;

/**
 * * @描述:
 *
 * @author Ocean
 * @version 1.01
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * @date 2016/8/11
 * Email: m13478943650@163.com
 */
public class ReUserEntity implements Serializable {

    /**
     * result : true
     * data : {"username":"656","password":"3d28ed51f00b78d5cd6890b15343a6dd"}
     */

    /**
     * username : 656
     * password : 3d28ed51f00b78d5cd6890b15343a6dd
     */
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

