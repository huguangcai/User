package com.ysxsoft.common_base.orm.hx;

import org.litepal.crud.DataSupport;

/**
 * create by Sincerly on 2019/1/18 0018
 * 消息免打扰表
 **/
public class BlockUser extends DataSupport {
    public long id;
    public String fid;//消息免打扰的好友id
    public String uid;//账号id
}
