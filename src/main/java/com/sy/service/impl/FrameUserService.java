package com.sy.service.impl;

import com.sy.mapper.FrameUserMapper;
import com.sy.model.FrameUser;
import com.sy.service.IFrameUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户接口实现
 *
 * @author lfeiyang
 * @since 2022-04-25 21:06
 */
@Service
@Transactional(readOnly = true)
public class FrameUserService implements IFrameUserService {
    @Autowired
    private FrameUserMapper userMapper;

    @Override
    public FrameUser findFrameUser(String userGuid) {
        return userMapper.findFrameUser(userGuid);
    }
}
