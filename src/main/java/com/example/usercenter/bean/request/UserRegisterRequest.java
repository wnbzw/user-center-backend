package com.example.usercenter.bean.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = 7788681402844453446L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String planetCode;
}
