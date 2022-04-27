package com.sy.service;

import com.sy.model.FrameUser;

/**
 * 用户接口
 *
 * @author lfeiyang
 * @since 2022-04-25 21:06
 */
public interface IFrameUserService {
    FrameUser findFrameUser(String userGuid);
}
