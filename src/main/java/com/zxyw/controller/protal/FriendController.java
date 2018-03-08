package com.zxyw.controller.protal;


import com.zxyw.common.Const;
import com.zxyw.common.ResponseCode;
import com.zxyw.common.ServerResponse;
import com.zxyw.pojo.User;
import com.zxyw.service.IFriendService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by jiangyuanyuan on 11/2/18.
 */
@Controller
@RequestMapping("/friend/")
public class FriendController {

    @Autowired
    IFriendService iFriendService;

    /**
     * 添加好友接口  http://localhost:8080/friend/add_friend.do
     *
     * @param friendname 必填 好友用户名
     * @param session    无视
     * @return json
     */
    @RequestMapping(value = "add_friend.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addFriend(String friendname, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeAndMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        if (StringUtils.isEmpty(friendname)||user.getUsername().equals(friendname)) {
            return ServerResponse.createByErrorCodeAndMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        return iFriendService.addfriend(user.getUsername(), friendname);

    }

    /**
     * 添加好友接口  http://localhost:8080/friend/delete_friend.do
     *
     * @param friendname 必填 好友用户名
     * @param session    无视
     * @return json
     */
    @RequestMapping(value = "delete_friend.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteFriend(String friendname, HttpSession session) {
        if (StringUtils.isEmpty(friendname)) {
            return ServerResponse.createByErrorCodeAndMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeAndMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iFriendService.deletefriend(user.getUsername(), friendname);

    }


    /**
     * 同意or加入黑名单好友申请接口   http://localhost:8080/friend/update_friend_status.do
     *
     * @param friendname 必填 好友用户名
     * @param status     必填   1 同意添加好友    0 加入黑名单    拒绝添加请调用删除好友接口
     * @param session    无视
     * @return json
     */
    @RequestMapping(value = "update_friend_status.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateFriendStatus(String friendname, Integer status, HttpSession session) {
        if (StringUtils.isEmpty(friendname) || (status != Const.FriendStatusEnum.Is_Friended.getCode() && status != Const.FriendStatusEnum.Black.getCode())) {
            return ServerResponse.createByErrorCodeAndMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeAndMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iFriendService.updateFriendStatus(user.getUsername(), friendname, status);

    }

    /**
     * 修改好友备注接口   http://localhost:8080/friend/update_friend_memoname.do
     *
     * @param friendname 必填 好友用户名
     * @param memoname   必填 备注名
     * @param session    无视
     * @return json
     */
    @RequestMapping(value = "update_friend_memoname.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateFriendMemoname(String friendname, String memoname, HttpSession session) {
        if (StringUtils.isEmpty(friendname) || StringUtils.isEmpty(memoname)) {
            return ServerResponse.createByErrorCodeAndMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeAndMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iFriendService.updateFriendMemoname(user.getUsername(), friendname, memoname);

    }

    /**
     * 模糊查询某个好友接口  http://localhost:8080/friend/search.do
     *
     * @param friendname 必填 好友用户名的一部分
     * @param username   必填 用户名
     * @param pageNum    分页查询第几页 默认第1页
     * @param pageSize   分页查询每页共多少条 默认10
     * @return json
     */
    @RequestMapping(value = "search.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse selectByKeyword(String username, String friendname,
                                          @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        if (StringUtils.isEmpty(friendname)||StringUtils.isEmpty(username)) {
            return ServerResponse.createByErrorCodeAndMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        return iFriendService.selectByFriendname(username, friendname, pageNum, pageSize);

    }


    /**
     * 好友列表接口 http://localhost:8080/friend/list_friend.do
     *
     * @param username  必填 用户名
     * @param pageNum  分页查询第几页 默认第1页
     * @param pageSize 分页查询每页共多少条 默认10
     * @return json
     */
    @RequestMapping(value = "list_friend.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse listFriends(String username,
                                      @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        return iFriendService.listFriends(username, pageNum, pageSize);
    }


}
