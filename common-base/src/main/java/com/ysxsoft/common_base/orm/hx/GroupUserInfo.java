package com.ysxsoft.common_base.orm.hx;

import org.litepal.crud.DataSupport;

/**
 * create by Sincerly on 2019/2/15 0015
 * 用户
 **/
public class GroupUserInfo extends DataSupport {
    public long id;
    public String uid;
    public String nickname;//群昵称
    public String gid;//群id
}
