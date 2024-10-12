package com.example.usercenter.bean.request;

import lombok.Data;

import java.io.Serializable;

@Data
    public class TeamExitRequest implements Serializable {
        private static final long serialVersionUID = -2038884913144640407L;
        /**
        *  id
        */
        private Long teamId;
    }