package com.ysp.houge.model.entity.bean;

import java.io.Serializable;

/**
 * * @描述:
 *
 * @author Ocean
 * @version 1.01
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * @date 2016/7/28
 * Email: m13478943650@163.com
 */
public class SkillMoneyUnitEntity implements Serializable {


    /**
     * result : true
     * lists : [{"name":"元/个"},{"name":"元/米"},{"name":"元/页"},{"name":"元/款"},{"name":"元/天"},{"name":"元/小时"}]
     */

    private boolean result;

    /**
     * name : 元/个
     */

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }


    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
