package com.ysxsoft.common_base.orm.hx;

import org.litepal.crud.DataSupport;

/**
 * 我的好友
 */
public class Friends extends DataSupport {
	public long id;
	public String uid;
	public String remark;
	public String sign;//用户签名
	public String icon;//用户头像
	public String address;
	public String gid;//用户分组id  0未在分组内 1在分组内
	public String username;
	public String tel;

	public String fid;//朋友id
}
