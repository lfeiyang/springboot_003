package com.sy.controller;

import com.sy.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制层
 *
 * @author lfeiyang
 * @since 2022-04-23 22:08
 */
@RestController
public class UserController {
    @RequestMapping("findUser.do")
    public User findUser() {
        User user = new User();
        user.setId("1");
        user.setUserName("小明");
        user.setPassWord("123456");

        return user;
    }
}
