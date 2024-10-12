package com.example.usercenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.usercenter.bean.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.example.usercenter.commons.contant.UserConstant.ADMIN_ROLE;
import static com.example.usercenter.commons.contant.UserConstant.USER_LOGIN_STATE;

/**
* @author 16247
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-09-14 22:17:35
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @param planetCode
     * @return
     */
    long userRegister(String userAccount, String userPassword, String checkPassword,String planetCode);

    /**
     * 用户登录
     *
     * @param userAccount
     * @param userPassword
     * @param request
     * @return
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    List<User> searchUsersByTags(List<String> tagNameList);

    /**
     * 用户注销
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);

    User getLoginUser(HttpServletRequest request);

    int updateUser(User user, User loginUser);

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    public boolean isAdmin(HttpServletRequest request);

    public boolean isAdmin(User loginUser);

    Page<User> recommendUserPage(long pageSize, long pageNum, HttpServletRequest request);

    List<User> matchUsers(long num, User user);
}
