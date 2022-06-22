package com.sy.mapper;

import com.sy.model.FrameUser;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * mapper接口
 *
 * @author lfeiyang
 * @since 2022-04-25 20:25
 */
@Repository
public interface FrameUserMapper extends Mapper<FrameUser> {
    @Select("select * from frame_user where userGuid = #{userGuid}")
    public FrameUser findFrameUser(String userGuid);

    @Select("select * from frame_user where loginId = #{loginid} and password = #{password}")
    public FrameUser findFrameUserByLoginidAndPassWord(String loginid, String password);

    @Update("update user set userGuid=#{userGuid},loginId=#{loginId},displayName=#{displayName} where userGuid=#{userGuid}")
    public int update(FrameUser frameUser);

    @Select("select * from frame_user")
    public List<FrameUser> getFrameUserList();

    @Select("select * from frame_user limit #{first},#{pageSize}")
    public List<FrameUser> getLocalFrameUserList(int first, int pageSize);
}
