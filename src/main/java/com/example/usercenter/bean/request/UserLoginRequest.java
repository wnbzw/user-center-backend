package com.example.usercenter.bean.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 2899050753699372030L;

    private String userAccount;
    private String userPassword;

}
