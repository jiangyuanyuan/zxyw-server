package com.zxyw.service;

import com.zxyw.common.ServerResponse;

/**
 * Created by jiangyuanyuan on 11/2/18.
 */
public interface IFriendService {
    ServerResponse addfriend(String username, String friendname);

    ServerResponse deletefriend(String username, String friendname);

    ServerResponse updateFriendStatus(String username, String friendname, Integer status);

    ServerResponse updateFriendMemoname(String username, String friendname, String memoname);

    ServerResponse selectByFriendname(String username, String friendname, int pageNum, int pageSize);

    ServerResponse listFriends(String username, int pageNum, int pageSize);
}
