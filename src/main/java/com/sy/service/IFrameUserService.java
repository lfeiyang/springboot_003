package com.sy.service;

import com.sy.model.FrameUser;

import java.util.List;

/**
 * 用户接口
 *
 * @author lfeiyang
 * @since 2022-04-25 21:06
 */
public interface IFrameUserService extends IService<FrameUser> {
    FrameUser findFrameUser(String userGuid);

    FrameUser findFrameUserByLoginidAndPassWord(String loginid, String password);

    int updateFrameUser(FrameUser frameUser);

    List<FrameUser> getSimpleFrameUserList(int first, int pageSize);

    List<FrameUser> getFrameUserList(int first, int pageSize);

    List<FrameUser> getLocalFrameUserList(int first, int pageSize);
}
