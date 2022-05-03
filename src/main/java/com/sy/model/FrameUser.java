package com.sy.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户实体
 *
 * @author lfeiyang
 * @since 2022-04-18 22:59
 */
@Data
@Table(name = "frame_user")
public class FrameUser implements Serializable {
    @Column(name = "userGuid")
    private String userGuid;
    @Column(name = "loginId")
    private String loginId;
    @Column(name = "displayName")
    private String displayName;
}
