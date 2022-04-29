package com.sy.mapper;

import com.sy.model.FrameUser;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * mapper接口
 *
 * @author lfeiyang
 * @since 2022-04-25 20:25
 */
public interface FrameUserMapper {
    @Select("select * from frame_user where userGuid = #{userGuid}")
    public FrameUser findFrameUser(String userGuid);

    @Select("select * from frame_user")
    public List<FrameUser> getFrameUserList();
}
