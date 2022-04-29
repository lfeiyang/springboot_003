package com.sy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sy.mapper.FrameUserMapper;
import com.sy.model.FrameUser;
import com.sy.service.IFrameUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public List<FrameUser> getFrameUserList(int first, int pageSize) {
        PageHelper.startPage(first, pageSize);

        List<FrameUser> frameUserList = userMapper.getFrameUserList();

        PageInfo<FrameUser> frameUserPageInfo = new PageInfo<>(frameUserList);

        return frameUserPageInfo.getList();
    }
}
