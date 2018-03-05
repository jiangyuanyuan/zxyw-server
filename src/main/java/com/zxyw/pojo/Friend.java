package com.zxyw.pojo;

import java.util.Date;

public class Friend {
    private Integer id;

    private String username;

    private String friendname;

    private String friengphoto;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String memoname;

    public Friend(Integer id, String username, String friendname, String friengphoto, Integer status, Date createTime, Date updateTime, String memoname) {
        this.id = id;
        this.username = username;
        this.friendname = friendname;
        this.friengphoto = friengphoto;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.memoname = memoname;
    }

    public Friend() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getFriendname() {
        return friendname;
    }

    public void setFriendname(String friendname) {
        this.friendname = friendname == null ? null : friendname.trim();
    }

    public String getFriengphoto() {
        return friengphoto;
    }

    public void setFriengphoto(String friengphoto) {
        this.friengphoto = friengphoto == null ? null : friengphoto.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getMemoname() {
        return memoname;
    }

    public void setMemoname(String memoname) {
        this.memoname = memoname == null ? null : memoname.trim();
    }
}