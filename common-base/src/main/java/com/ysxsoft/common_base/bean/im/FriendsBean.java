package com.ysxsoft.common_base.bean.im;

import com.ysxsoft.common_base.adapter.entity.MultiItemEntity;
import com.ysxsoft.common_base.helper.PinYinHelper;
import com.ysxsoft.common_base.orm.hx.Friends;

import static com.ysxsoft.common_base.config.BaseConfig.IM_HX_FRIEND_ITEM_LETTER;
import static com.ysxsoft.common_base.config.BaseConfig.IM_HX_FRIEND_ITEM_NROMAL;


/**
 * create by Sincerly on 2019/1/16 0016
 **/
public class FriendsBean implements MultiItemEntity,PinYinHelper.IPinYin {
    private String latter;
    private boolean isTop;
    private boolean isCheck;
    private Friends friends;
    private boolean isClick;//判断是否可点

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public String getLatter() {
        return latter == null ? "" : latter;
    }

    public void setLatter(String latter) {
        this.latter = latter;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public Friends getFriends() {
        return friends;
    }

    public void setFriends(Friends friends) {
        this.friends = friends;
    }

    @Override
    public int getItemType() {
        return isTop?IM_HX_FRIEND_ITEM_LETTER:IM_HX_FRIEND_ITEM_NROMAL;
    }

    @Override
    public String getLetter() {
        return null;
    }
}
