package com.ysxsoft.common_base.orm.hx;

import org.litepal.crud.DataSupport;

/**
 * 通讯录分组  我的好友/朋友/自定义分组之类的
 */
public class Groups extends DataSupport {
	public long id;
	public String title;
	public String gid;
	public String groupId;//群组id
}
