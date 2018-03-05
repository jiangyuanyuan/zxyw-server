package com.zxyw.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by jiangyuanyuan on 23/11/17.
 */
public class Const {
    public static final String CURRENT_USER = "currentUser";
    public static final String CHECK_USERNAME = "check_username";
    public static final String CHECK_EMAIL = "check_email";

    //    public interface ProductListOrderBy{
//        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
//    }
    public interface Role {
        int ADMINISTRATOR = 0;
        int GENERAL_USER = 1;
    }

    //
//    public interface Cart{
//        int CHECKED = 1;//即购物车选中状态
//        int UN_CHECKED = 0;//购物车中未选中状态
//
//        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
//        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
//    }
//
    public enum FriendStatusEnum {

        Is_Friended(1, "好友"),
        Request_to_add(2, "请求添加"),
        Requests_are_added(3, "请求被添加"),
        No_Friended(4, "陌生人"),
        Black(0, "黑名单");
        private int code;
        private String value;

        FriendStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }
}
