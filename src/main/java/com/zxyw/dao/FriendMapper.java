package com.zxyw.dao;

import com.zxyw.pojo.Friend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Friend record);

    int insertSelective(Friend record);

    Friend selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Friend record);
    int updateByFriendNameSelective(Friend record);

    int updateByPrimaryKey(Friend record);

    int checkIsFriended(@Param("username") String username, @Param("friendname") String friendname);

    int deleteByFriendName(@Param("username") String username, @Param("friendname") String friendname);

    List<Friend> selectByUsername(String username);

    List<Friend> selectByUsernameLikeFriendname(@Param("username") String username, @Param("friendname") String friendname);
}