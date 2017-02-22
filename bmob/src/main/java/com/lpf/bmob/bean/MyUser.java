package com.lpf.bmob.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by liupengfei on 17/2/7.
 */

public class MyUser extends BmobUser {

    private Boolean gender;
    private String nickName;
    private Integer age;

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
