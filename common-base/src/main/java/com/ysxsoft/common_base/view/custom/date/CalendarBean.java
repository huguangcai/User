package com.ysxsoft.common_base.view.custom.date;

/**
 * Created by Administrator on 2018/3/1 0001.
 */

public class CalendarBean {
	private int logoResource;
	private String orderNum;
	private String money;

	public int getLogoResource() {
		return logoResource;
	}

	public void setLogoResource(int logoResource) {
		this.logoResource = logoResource;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}


	public CalendarBean(int logoResource, String orderNum, String money) {
		this.logoResource = logoResource;
		this.orderNum = orderNum;
		this.money = money;
	}
}
