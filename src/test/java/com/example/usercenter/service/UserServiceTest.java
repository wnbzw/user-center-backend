package com.example.usercenter.service;

import com.example.usercenter.bean.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    void testAddUser() {
        User user = new User();
        user.setUsername("test");
        user.setUserAccount("123");
        user.setAvatarUrl("https://cdn.nlark.com/yuque/0/2023/png/2961070/1687456355196-e7b8e7e8-b0e7-4d08-b0a9-d8a0d0a0d0a0.png");
        user.setGender(0);
       user.setUserPassword("12345678");
       user.setPhone("123");
       user.setEmail("123");
       boolean result = userService.save(user);
        System.out.println(result);
        Assertions.assertTrue(result);
    }

    @Test
    void testRegister() {
        String userAccount = "test";
        String userPassword = "12345678";
        String checkPassword = "12345678";
        String planetCode = "1";
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        System.out.println(result);
        Assertions.assertEquals(-1,result);
    }
}