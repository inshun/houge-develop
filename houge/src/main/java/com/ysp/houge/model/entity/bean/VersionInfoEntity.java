package com.ysp.houge.model.entity.bean;

import java.io.Serializable;

/**
 * * @描述:
 *
 * @author Ocean
 * @version 1.01
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * @date 2016/8/3
 * Email: m13478943650@163.com
 */
public class VersionInfoEntity implements Serializable{
    /**
     * version_code : 2
     * version_name : 1.0.2
     * version_info : 1、优化启动页面；2、修复若干bug；
     * version_must_update : 0
     */
        private String version_code;
        private String version_name;
        private String version_info;
        private int version_must_update;

        public String getVersion_code() {
            return version_code;
        }

        public void setVersion_code(String version_code) {
            this.version_code = version_code;
        }

        public String getVersion_name() {
            return version_name;
        }

        public void setVersion_name(String version_name) {
            this.version_name = version_name;
        }

        public String getVersion_info() {
            return version_info;
        }

        public void setVersion_info(String version_info) {
            this.version_info = version_info;
        }

        public int getVersion_must_update() {
            return version_must_update;
        }

        public void setVersion_must_update(int version_must_update) {
            this.version_must_update = version_must_update;
        }
    }





