package com.sy.model;

/**
 * 用户实体
 *
 * @author lfeiyang
 * @since 2022-04-18 22:59
 */
public class FrameUser {
    private String userGuid;
    private String loginId;
    private String displayName;

    public String getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "FrameUser{" +
                "userGuid='" + userGuid + '\'' +
                ", loginId='" + loginId + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
