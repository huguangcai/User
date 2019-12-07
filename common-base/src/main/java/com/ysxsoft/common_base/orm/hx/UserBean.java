package com.ysxsoft.common_base.orm.hx;

import org.litepal.crud.DataSupport;

public class UserBean extends DataSupport {
	public long id;

	public String uid;
	public String username;
	public String icon;
	public String token;
	public String phone;
	public String accounts;
	public String back_pid;
	public boolean close;//tab3页 附近小店  关闭按钮  默认开启
}
