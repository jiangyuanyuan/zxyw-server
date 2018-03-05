package com.zxyw.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zxyw.common.Const;
import com.zxyw.common.ServerResponse;
import com.zxyw.dao.FriendMapper;
import com.zxyw.dao.UserMapper;
import com.zxyw.pojo.Friend;
import com.zxyw.service.IFriendService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangyuanyuan on 11/2/18.
 */
@Service(("iFriendService"))
public class FriendServiceImpl implements IFriendService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    FriendMapper friendMapper;

    /*
     * 添加好友
     */
    @Override
    public ServerResponse addfriend(String username, String friendname) {
        int resultCount = userMapper.checkUserName(username);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        //查询是否已经是好友关系
        resultCount = friendMapper.checkIsFriended(username, friendname);
        if (resultCount >= 1) {
            //是好友，不用添加
            return ServerResponse.createByErrorMessage("已经是好友");
        }
        //不是好友继续添加
        Friend friend = new Friend();
        friend.setUsername(username);
        friend.setFriendname(friendname);
        friend.setStatus(Const.FriendStatusEnum.Request_to_add.getCode());
        resultCount = friendMapper.insert(friend);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("添加好友失败");
        }
        Friend friend2 = new Friend();
        friend2.setUsername(friendname);
        friend2.setFriendname(username);
        friend2.setStatus(Const.FriendStatusEnum.Requests_are_added.getCode());
        resultCount = friendMapper.insert(friend2);
        if (resultCount == 0) {
            friendMapper.deleteByFriendName(username, friendname);
            return ServerResponse.createByErrorMessage("添加好友失败");
        }
        return ServerResponse.createBySuccessMessage("添加成功，等待好友确认");
    }

    /*
     * 删除好友
     */
    @Override
    public ServerResponse deletefriend(String username, String friendname) {
        int resultCount = friendMapper.deleteByFriendName(username, friendname);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("删除好友失败");
        }
        resultCount = friendMapper.deleteByFriendName(friendname, username);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("删除好友失败");
        }
        return ServerResponse.createBySuccessMessage("删除好友成功");
    }

    /*
        更新好友关系状态
     */
    @Override
    public ServerResponse updateFriendStatus(String username, String friendname, Integer status) {
        int resultCount = friendMapper.checkIsFriended(username, friendname);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("还未添加好友，请先添加");
        }
        Friend friend = new Friend();
        friend.setUsername(username);
        friend.setFriendname(friendname);
        friend.setStatus(status);
        resultCount = friendMapper.updateByFriendNameSelective(friend);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("好友状态修改失败");
        }
        Friend friend2 = new Friend();
        friend2.setUsername(friendname);
        friend2.setFriendname(username);
        friend2.setStatus(status);
        resultCount = friendMapper.updateByFriendNameSelective(friend2);
        if (resultCount == 0) {
            friendMapper.deleteByFriendName(username, friendname);
            return ServerResponse.createByErrorMessage("好友状态修改失败");
        }
        return ServerResponse.createBySuccessMessage("好友状态修改成功");
    }

    /*
        修改好友备注
     */
    @Override
    public ServerResponse updateFriendMemoname(String username, String friendname, String memoname) {
        int resultCount = friendMapper.checkIsFriended(username, friendname);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("还未添加好友，请先添加");
        }
        Friend friend = new Friend();
        friend.setUsername(username);
        friend.setFriendname(friendname);
        friend.setMemoname(memoname);
        resultCount = friendMapper.updateByFriendNameSelective(friend);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("备注修改失败");
        }
        return ServerResponse.createBySuccessMessage("备注修改成功");
    }

    /*
        模糊查找好友
     */
    @Override
    public ServerResponse selectByFriendname(String username, String friendname, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isNotBlank(friendname)) {
            friendname = new StringBuilder().append("%").append(friendname).append("%").toString();
        }
        List<Friend> friendList = friendMapper.selectByUsernameLikeFriendname(username, friendname);
        PageInfo pageResult = new PageInfo(friendList);

        return ServerResponse.createBySuccessMessageAndData("关键字搜索好友成功", pageResult);
    }

    /*
        查询好友列表
     */
    @Override
    public ServerResponse listFriends(String username, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Friend> friendList = friendMapper.selectByUsername(username);
        PageInfo pageResult = new PageInfo(friendList);

        return ServerResponse.createBySuccessMessageAndData("好友列表获取成功", pageResult);
    }


}
