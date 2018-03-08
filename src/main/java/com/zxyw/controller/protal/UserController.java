package com.zxyw.controller.protal;


import com.zxyw.common.Const;
import com.zxyw.common.ResponseCode;
import com.zxyw.common.ServerResponse;
import com.zxyw.pojo.User;
import com.zxyw.service.IUserService;
import com.zxyw.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by jiangyuanyuan on 22/11/17.
 */
@Controller
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private IUserService iUserService;

    /**
     * 登录接口   http://localhost:8080/user/login.do
     *
     * @param username 必填 登录的用户名
     * @param password 必填 登录密码
     * @param session  无视
     * @return 成功：
     * {
     * "status": 0,
     * "msg": "登录成功",
     * "data": {
     * "id": 23,
     * "username": "jyy",
     * "password": "",
     * "email": "test09@happymmall.com",
     * "phone": "15622501000",
     * "question": "问题",
     * "answer": null,
     * "role": 1,
     * "createTime": 1517726933000,
     * "updateTime": 1517726933000,
     * "robotsn": null
     * }
     * }
     * 失败：
     * {
     * "status": 1,
     * "msg": "密码错误"
     * }
     * {
     * "status": 1,
     * "msg": "用户名不存在"
     * }
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse<User> response = iUserService.login(username, MD5Util.MD5EncodeUtf8(password));
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    /**
     * 用户退出接口   http://localhost:8080/user/logout.do
     *
     * @param session 无视
     * @return {
     * "status": 0
     * }
     */
    @RequestMapping(value = "logout.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    /**
     * 注册的接口    http://120.79.2.255:8080/zxyw/user/register.do
     * 参数 username   必填  前端做非空判断 用户名
     * 参数 password   必填  前端做非空判断 密码
     * 参数 email      邮箱
     * 参数 phone      电话
     * 参数 question   找回密码的问题
     * 参数 answer     找回密码的答案
     * 参数 robotsn    机器人唯一标识 通过扫二维码获得
     * 参数 role       设置超级用户or普通用户  后续用于后台管理

     * @return 成功：
     * {
     * "status": 0,
     * "msg": "注册成功"
     * }
     * <p>
     * 失败：
     * {
     * "status": 1,
     * "msg": "用户已经存在"
     * }
     *
     *  @param user 用户类 不用管
     */
    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user) {
        return iUserService.register(user);
    }

    /**
     * 校验接口  http://localhost:8080/user/check_valid.do
     *
     * @param string 必填 被校验的string
     * @param type   必填 校验用户名是否可用：check_username   邮箱是否可用：check_email
     * @return 成功：
     * {
     * "status": 0,
     * "msg": "校验成功"
     * }
     * 失败：
     * {
     * "status": 1,
     * "msg": "用户已经存在"
     * }
     */
    @RequestMapping(value = "check_valid.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(String string, String type) {
        return iUserService.checkValid(string, type);
    }

    /**
     * 登录情况下获取用户信息接口     http://localhost:8080/user/get_user_info.do
     *
     * @param session 登录情况下获取用户信息
     * @return 成功：
     * {
     * "status": 0,
     * "msg": "获取用户信息成功",
     * "data": {
     * "id": 23,
     * "username": "jyy",
     * "password": "",
     * "email": "test09@happymmall.com",
     * "phone": "15622501000",
     * "question": "问题",
     * "answer": "答案",
     * "role": 1,
     * "createTime": 1517726933000,
     * "updateTime": 1517726933000,
     * "robotsn": null
     * }
     * }
     * <p>
     * 失败：
     * {
     * "status": 10,
     * "msg": "用户未登录"
     * }
     */
    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeAndMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return ServerResponse.createBySuccessMessageAndData("获取用户信息成功", user);
    }

    /**
     * 查询忘记密码的问题是什么接口     http://localhost:8080/user/forget_get_question.do
     *
     * @param username 必填 用户名
     * @return 成功：
     * {
     * "status": 0,
     * "msg": "查询问题成功",
     * "data": "问题"
     * }
     * 失败：
     * {
     * "status": 1,
     * "msg": "用户不存在"
     * }
     */
    @RequestMapping(value = "forget_get_question.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username) {

        return iUserService.selectQuestion(username);
    }

    /**
     * 校验找回密码问题     http://localhost:8080/user/forget_check_question.do
     *
     * @param username 必填 用户名
     * @param question 必填 问题
     * @param answer   必填 答案
     * @return 成功：
     * {
     * "status": 0,
     * "msg": "token",
     * "data": "c8b7119c-5cb5-48fd-a75c-d105e7250bbc"
     * }
     * 失败：
     * {
     * "status": 1,
     * "msg": "问题答案错误"
     * }
     */
    @RequestMapping(value = "forget_check_question.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {

        return iUserService.checkAnswer(username, question, answer);
    }

    /**
     * 密码问题重置密码接口    http://localhost:8080/user/forget_reset_question.do
     *
     * @param username    必填 用户名
     * @param newPassword 必填 新密码
     * @param forgetToken token
     * @return 成功：
     * {
     * "status": 0,
     * "msg": "修改密码成功"
     * }
     * <p>
     * 失败：
     * {
     * "status": 1,
     * "msg": "token错误，请重新获取重置密码的token"
     * }
     */
    @RequestMapping(value = "forget_reset_question.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetResetQuestion(String username, String newPassword, String forgetToken) {
        return iUserService.forgetResetPassword(username, newPassword, forgetToken);
    }

    /**
     * 登录状态下重置密码接口    http://localhost:8080/user/reset_password.do
     *
     * @param session     无视
     * @param newPassword 必填 新密码
     * @param oldPassword 必填 老密码
     * @return 成功：
     * {
     * "status": 0,
     * "msg": "修改密码成功"
     * }
     * <p>
     * 失败：
     * {
     * "status": 10,
     * "msg": "用户未登录"
     * }
     * <p>
     * {
     * "status": 1,
     * "msg": "密码错误"
     * }
     */
    @RequestMapping(value = "reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpSession session, String newPassword, String oldPassword) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeAndMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iUserService.resetPassword(newPassword, oldPassword, user);
    }

    /**
     * 更新用户信息接口    http://localhost:8080/user/update_information.do
     * 参数 email      邮箱
     * 参数 phone      电话
     * 参数 question   找回密码的问题
     * 参数 answer     找回密码的答案
     * 参数 robotsn    机器人唯一标识 通过扫二维码获得
     * 参数 role       设置超级用户or普通用户  后续用于后台管理
     *
     * @return 成功：
     * {
     * "status": 0,
     * "msg": "用户信息更新成功"
     * }
     * 失败：
     * {
     * "status": 1,
     * "msg": "用户信息更新失败"
     * }
     *
     */
    @RequestMapping(value = "update_information.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> updateInformation(User user) {
        return iUserService.updateInformation(user);
    }

    /**
     * 获取用户信息接口    http://localhost:8080/user/get_information.do
     *
     * @param session 无视
     * @return 成功：
     * {
     * "status": 0,
     * "msg": "获取用户信息成功",
     * "data": {
     * "id": 23,
     * "username": "jyy",
     * "password": "",
     * "email": "test09@happymmall.com",
     * "phone": "15622501000",
     * "question": "问题",
     * "answer": null,
     * "role": 1,
     * "createTime": 1518357949000,
     * "updateTime": 1518357949000,
     * "robotsn": null
     * }
     * }
     * <p>
     * 失败：
     * {
     * "status": 10,
     * "msg": "用户未登录"
     * }
     */
    @RequestMapping(value = "get_information.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> get_information(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeAndMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iUserService.getInformation(currentUser.getId());
    }
}
