package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhuzhiqiang on 2017/7/16.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {

        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0) {
            return ServerResponse.createByError("用户名不存在");
        }

        User user = userMapper.selectLogin(username, MD5Util.MD5EncodeUtf8(password));
        if (null == user) {
            return ServerResponse.createByError("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登陆成功", user);
    }

    @Override
    public ServerResponse<String> register(User user) {
        ServerResponse<String> usernameValid = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!usernameValid.isSuccess()) {
            return usernameValid;
        }
        ServerResponse<String> emailValid = this.checkValid(user.getUsername(), Const.EMAIL);
        if (!emailValid.isSuccess()) {
            return emailValid;
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int insert = userMapper.insert(user);
        if (0 == insert) {
            return ServerResponse.createByError("注册失败");
        }
        return ServerResponse.createBySuccess("注册成功");
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            if (Const.EMAIL.equals(type)) {
                int emailCount = userMapper.checkEmail(str);
                if (emailCount > 0) {
                    return ServerResponse.createByError("邮箱已经存在");
                }
            } else if (Const.USERNAME.equals(type)) {
                int resultCount = userMapper.checkUsername(str);
                if (resultCount > 0) {
                    return ServerResponse.createByError("用户名已经存在");
                }
            }
        } else {
            return ServerResponse.createByError("传值错误");
        }
        return ServerResponse.createBySuccess("校验成功");
    }

}
