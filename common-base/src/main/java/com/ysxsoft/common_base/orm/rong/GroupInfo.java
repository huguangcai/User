package com.ysxsoft.common_base.orm.rong;

import android.content.Context;

import com.ysxsoft.common_base.base.BaseApplication;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class GroupInfo extends DataSupport {
    public long id;
    public String groupName;
    public String groupId;
    public String uid;
    public String groupIcon;

    public static void saveGroup(Context context, String groupId, String groupName, String groupIcon) {
        GroupInfo friends = new GroupInfo();
        friends.uid = SharedPreferencesUtils.getUid(context);
        friends.groupId = groupId;
        friends.groupName = groupName;
        friends.groupIcon = groupIcon;
        friends.saveOrUpdate("groupId=? and uid=?", groupId, SharedPreferencesUtils.getUid(context));
    }

    public static GroupInfo getGroup(Context context, String groupId) {
        GroupInfo group = DataSupport.where("groupId=? and uid=?", groupId,SharedPreferencesUtils.getUid(context)).findLast(GroupInfo.class);
        return group;
    }

    public static void updateGroup(Context context, String groupId, String groupName, String groupIcon) {
        GroupInfo group = GroupInfo.getGroup(context,groupId);
        group.groupName = groupName;
        group.groupIcon = groupIcon;
        group.update(group.id);
    }

    public static void deleteGroup(Context context, String groupId) {
        GroupInfo group = GroupInfo.getGroup(context,groupId);
        if (group != null) {
            group.delete();
        }
    }

    public static boolean clearAllGroup() {
        return DataSupport.deleteAll(GroupInfo.class) > 0;
    }

    public static List<GroupInfo> getGroupList() {
        List<GroupInfo> group = DataSupport.findAll(GroupInfo.class);
        if (group == null) {
            group = new ArrayList<>();
        }
        return group;
    }

    public static boolean exist(Context context, String groupId) {
        return GroupInfo.getGroup(context,groupId) == null;
    }

    public static boolean checkGroupIconChanged(Context context, String groupId, String iconUrl) {
        if (GroupInfo.exist(context,groupId)) {
            GroupInfo group = GroupInfo.getGroup(context,groupId);
            if (group != null && group.groupIcon != null && !group.groupIcon.equals(iconUrl)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 优先返回备注
     *
     * @param groupId
     * @return
     */
    public static String getGroupName(Context context,String groupId) {
        String groupName = "";
        GroupInfo groupInfo = GroupInfo.getGroup(context,groupId);
        if (groupInfo != null && groupInfo.groupName != null) {
            groupName = groupInfo.groupName;
        }
        return groupName;
    }
}
