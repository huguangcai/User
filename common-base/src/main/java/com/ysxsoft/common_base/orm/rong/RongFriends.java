package com.ysxsoft.common_base.orm.rong;

import com.ysxsoft.common_base.base.BaseApplication;
import com.ysxsoft.common_base.orm.hx.Friends;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 融云朋友表
 */
public class RongFriends extends DataSupport {
    public long id;
    public String uid;
    public String fuid;//用户id
    public String name;//用户名称
    public String icon;//用户头像
    public String remark;//备注

    public static boolean saveUser(String fuid, String name, String icon) {
        RongFriends friends = new RongFriends();
        friends.uid = SharedPreferencesUtils.getUid(BaseApplication.getContext());
        friends.fuid = fuid;
        friends.name = name;
        friends.icon = icon;
        return friends.saveOrUpdate("fuid=? and uid=?", fuid,SharedPreferencesUtils.getUid(BaseApplication.getContext()));
    }

    public static RongFriends getFriend(String fuid) {
        RongFriends friends = DataSupport.where("fuid=?", fuid).findLast(RongFriends.class);
        return friends;
    }

    public static void updateUser(String fuid, String name, String icon) {
        RongFriends rongFriends = RongFriends.getFriend(fuid);
        rongFriends.name = name;
        rongFriends.icon = icon;
        rongFriends.update(rongFriends.id);
    }

    public static void deleteUser(String fuid) {
        RongFriends friends=RongFriends.getFriend(fuid);
        if(friends!=null){
            friends.delete();
        }
    }

    public static boolean clearAllUser() {
        return DataSupport.deleteAll(RongFriends.class)>0;
    }

    public static List<RongFriends> getUserList() {
        List<RongFriends> friends=DataSupport.findAll(RongFriends.class);
        if(friends==null){
            friends=new ArrayList<>();
        }
        return friends;
    }

    public static boolean isFriends(String fuid) {
        return RongFriends.getFriend(fuid)==null;
    }

    public static boolean checkFriendsIconChanged(String fuid, String iconUrl) {
        if(RongFriends.isFriends(fuid)){
            RongFriends friends=RongFriends.getFriend(fuid);
            if(friends!=null&&friends.icon!=null&&!friends.icon.equals(iconUrl)){
                return true;
            }
        }
        return false;
    }

    public static String getFriendRemark(String fuid) {
        String remarkName = "";
        RongFriends rongFriends = RongFriends.getFriend(fuid);
        if (rongFriends != null) {
            remarkName = rongFriends.remark;
        }
        return remarkName;
    }

    /**
     * 优先返回备注
     *
     * @param fuid
     * @return
     */
    public static String getFriendName(String fuid) {
        String nickName = "";
        RongFriends rongFriends = RongFriends.getFriend(fuid);
        if (rongFriends != null) {
            if (rongFriends.remark != null) {
                nickName = rongFriends.remark;
            }else{
                nickName = rongFriends.name;
            }
        }
        return nickName;
    }
}
