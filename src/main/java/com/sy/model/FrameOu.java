package com.sy.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 表名：frame_ou
 * @author lfeiyang
 */
@Data
@Table(name = "frame_ou")
public class FrameOu implements Serializable {
    @Id
    @Column(name = "ouGuid")
    private String ouGuid;

    @Column(name = "ouCode")
    private String ouCode;

    @Column(name = "ouName")
    private String ouName;

    @Column(name = "ouShortName")
    private String ouShortName;

    @Column(name = "orderNumber")
    private Integer orderNumber;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;
}