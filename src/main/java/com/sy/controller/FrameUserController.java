package com.sy.controller;

import com.sy.model.FrameUser;
import com.sy.service.IFrameUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户控制层
 *
 * @author lfeiyang
 * @since 2022-04-25 21:31
 */
@RestController(value = "/lfy")
@Scope(value = "singleton")
public class FrameUserController {
    @Autowired
    private IFrameUserService userService;

    @RequestMapping("/findFrameUser")
    public FrameUser findFrameUser(@RequestParam(value = "userGuid", required = false, defaultValue = "0001c750-01d0-400f-bf56-18f66669b3a7") String userGuid) {
        return userService.findFrameUser(userGuid);
    }

    @RequestMapping("/getSimpleFrameUserList")
    public List<FrameUser> getSimpleFrameUserList(@RequestParam(defaultValue = "1") int first, @RequestParam(defaultValue = "10") int pageSize) {
        return userService.getSimpleFrameUserList(first, pageSize);
    }

    @RequestMapping("/getFrameUserList")
    public List<FrameUser> getFrameUserList(@RequestParam(defaultValue = "1") int first, @RequestParam(defaultValue = "10") int pageSize) {
        return userService.getFrameUserList(first, pageSize);
    }
}
