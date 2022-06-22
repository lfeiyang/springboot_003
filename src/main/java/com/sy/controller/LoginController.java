package com.sy.controller;

import com.sy.model.FrameUser;
import com.sy.service.impl.FrameUserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 登录接口
 *
 * @author lfeiyang
 * @since 2022-06-07 0:25
 */
@RestController
@RequestMapping("/rest")
public class LoginController {
    @Resource
    private FrameUserService frameUserService;

    /**
     * 用户验证
     *
     * @param user     用户名
     * @param password 密码
     * @return 登录信息
     */
    @PostMapping(value = "/login")
    public String login(@RequestParam("user") String user, @RequestParam("password") String password) {
        JSONObject loginData = new JSONObject();
        loginData.put("code", 0);

        FrameUser frameUser = frameUserService.findFrameUserByLoginidAndPassWord(user, password);
        JSONObject data = new JSONObject();
        if (frameUser != null) {
            data.put("success", true);
            data.put("msg", "登录成功");
            data.put("token", "admin-token");
            data.put("user", "admin");
        } else {
            data.put("success", false);
            data.put("msg", "登录失败");
        }

        loginData.put("data", data);

        return loginData.toString();
    }

    /**
     * 角色权限
     *
     * @return 用户权限
     */
    @RequestMapping(value = "/getInfo")
    public String getInfo() {
        JSONObject loginInfo = new JSONObject();
        loginInfo.put("code", 0);

        JSONObject data = new JSONObject();
        data.put("name", "admin");

        JSONArray roles = new JSONArray();
        roles.add("Home");
        roles.add("Dashbord");
        data.put("roles", roles);

        loginInfo.put("data", data);

        return loginInfo.toString();
    }
}
