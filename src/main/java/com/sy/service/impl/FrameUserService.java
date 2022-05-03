package com.sy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sy.mapper.FrameUserMapper;
import com.sy.model.FrameUser;
import com.sy.service.IFrameUserService;
import com.sy.util.RedisCatchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
public class FrameUserService extends BaseService<FrameUser> implements IFrameUserService {
    @Autowired
    private RedisCatchUtil redisCatchUtil;

    @Autowired
    private FrameUserMapper userMapper;

    @Override
    public FrameUser findFrameUser(String userGuid) {
        // 缓存
        FrameUser frameUser = (FrameUser) redisCatchUtil.get(userGuid);
        if (frameUser != null) {
            return frameUser;
        }

        // 加入缓存
        FrameUser dbUser = userMapper.findFrameUser(userGuid);
        if (dbUser != null) {
            redisCatchUtil.setNx(userGuid, dbUser);
        }

        return dbUser;
    }

    @Override
    @Transactional
    public int updateFrameUser(FrameUser frameUser) {
        return userMapper.update(frameUser);
    }

    @Override
    @Async
    public List<FrameUser> getSimpleFrameUserList(int first, int pageSize) {
        // 紧跟着的第一个select方法会被分页
        System.out.println("first============>" + first + "; pageSize===============>" + pageSize);

        PageHelper.startPage(first, pageSize);

        return userMapper.getFrameUserList();
    }

    @Override
    public List<FrameUser> getFrameUserList(int first, int pageSize) {
        PageHelper.startPage(first, pageSize);

        List<FrameUser> frameUserList = userMapper.getFrameUserList();

        PageInfo<FrameUser> frameUserPageInfo = new PageInfo<>(frameUserList);

        return frameUserPageInfo.getList();
    }

    @Override
    public List<FrameUser> getLocalFrameUserList(int first, int pageSize) {
        return userMapper.getLocalFrameUserList(first, pageSize);
    }
}
