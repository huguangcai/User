package com.ysxsoft.common_base.orm.hx;

import org.litepal.crud.DataSupport;

/**
 * create by Sincerly on 2019/1/18 0018
 * 消息免打扰表
 **/
public class BlockGroup extends DataSupport {
    public long id;
    public String gid;//消息免打扰的群id
    public String uid;//账号id
}
