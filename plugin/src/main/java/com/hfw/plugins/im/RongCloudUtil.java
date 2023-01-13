package com.hfw.plugins.im;

import io.rong.RongCloud;
import io.rong.methods.group.Group;
import io.rong.methods.user.User;
import io.rong.models.Result;
import io.rong.models.group.GroupModel;
import io.rong.models.response.GroupUser;
import io.rong.models.response.GroupUserQueryResult;
import io.rong.models.response.TokenResult;
import io.rong.models.response.UserResult;
import io.rong.models.user.UserModel;

import java.util.List;

/**
 * 融云im
 * @author farkle
 * @date 2022-12-26
 */
public class RongCloudUtil {

    private static String appKey = "appKey";
    private static String appSecret = "appSecret";
    private static  RongCloud rongCloud = null;

    static {
        rongCloud = RongCloud.getInstance(appKey, appSecret);
    }

    /**
     * 注册用户
     * @param userModel
     * @return
     * @throws Exception
     */
    public static String regist(UserModel userModel) throws Exception {
        User user = rongCloud.user;
        TokenResult result = user.register(userModel);
        System.out.println(result);
        return result.getToken();
    }
    public UserResult userInfo(UserModel userModel) throws Exception {
        User user = rongCloud.user;
        UserResult userResult = user.get(userModel);
        return userResult;
    }
    public boolean update(UserModel userModel) throws Exception {
        User user = rongCloud.user;
        Result result = user.update(userModel);
        return result.getCode().equals(200);
    }


    public boolean createGroup(GroupModel groupModel) throws Exception {
        Group group = rongCloud.group;
        Result result = group.create(groupModel);
        return result.getCode().equals(200);
    }
    public boolean updateGroup(GroupModel groupModel) throws Exception {
        Group group = rongCloud.group;
        Result result = group.update(groupModel);
        return result.getCode().equals(200);
    }

    /**
     * 邀请用户入群
     * @param groupModel
     * @return
     * @throws Exception
     */
    public boolean invite(GroupModel groupModel) throws Exception {
        Group group = rongCloud.group;
        Result result = group.invite(groupModel);
        return result.getCode().equals(200);
    }

    /**
     * 加入群组
     * @param groupModel
     * @return
     * @throws Exception
     */
    public boolean join(GroupModel groupModel) throws Exception {
        Group group = rongCloud.group;
        Result result = group.join(groupModel);
        return result.getCode().equals(200);
    }
    public List<GroupUser> getMembers(String groupId) throws Exception {
        Group group = rongCloud.group;
        GroupModel groupModel = new GroupModel().setId(groupId);
        GroupUserQueryResult result = group.get(groupModel);
        return result.getMembers();
    }

    /**
     * 退出群组
     * @param groupModel
     * @return
     * @throws Exception
     */
    public boolean quit(GroupModel groupModel) throws Exception {
        Group group = rongCloud.group;
        Result result = group.quit(groupModel);
        return result.getCode().equals(200);
    }

    /**
     * 解散群组
     * @param groupModel
     * @return
     * @throws Exception
     */
    public boolean dismiss(GroupModel groupModel) throws Exception {
        Group group = rongCloud.group;
        Result result = group.dismiss(groupModel);
        return result.getCode().equals(200);
    }
}
