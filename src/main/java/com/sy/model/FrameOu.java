package com.sy.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 表名：frame_ou
 */
@Table(name = "frame_ou")
public class FrameOu implements Serializable {
    @Id
    @Column(name = "OUGUID")
    private String ouguid;

    @Column(name = "OUCODE")
    private String ouCode;

    @Column(name = "OUNAME")
    private String ouname;

    @Column(name = "OUSHORTNAME")
    private String oushortname;

    @Column(name = "ORDERNUMBER")
    private Integer ordernumber;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "POSTALCODE")
    private String postalcode;

    @Column(name = "TEL")
    private String tel;

    @Column(name = "BASEOUGUID")
    private String baseouguid;

    @Column(name = "ISSUBWEBFLOW")
    private Integer issubwebflow;

    @Column(name = "PARENTOUGUID")
    private String parentouguid;

    @Column(name = "OUCODELEVEL")
    private String oucodelevel;

    @Column(name = "HASCHILDOU")
    private Integer haschildou;

    @Column(name = "HASCHILDUSER")
    private Integer haschilduser;

    @Column(name = "UPDATETIME")
    private Date updatetime;

    @Column(name = "OrderNumberFull")
    private String ordernumberfull;

    @Column(name = "OUCODEFULL")
    private String oucodefull;

    private String dimension;

    private String baseoucode;

    private String unionguid;

    private Date addtime;

    private String test;

    private String parentgabcode;

    private String gabcode;

    private String virtualou;

    /**
     * @return ouguid
     */
    public String getOuguid() {
        return ouguid;
    }

    /**
     * @param ouguid
     */
    public void setOuguid(String ouguid) {
        this.ouguid = ouguid == null ? null : ouguid.trim();
    }

    /**
     * @return ouCode
     */
    public String getOuCode() {
        return ouCode;
    }

    /**
     * @param ouCode
     */
    public void setOuCode(String ouCode) {
        this.ouCode = ouCode == null ? null : ouCode.trim();
    }

    /**
     * @return ouname
     */
    public String getOuname() {
        return ouname;
    }

    /**
     * @param ouname
     */
    public void setOuname(String ouname) {
        this.ouname = ouname == null ? null : ouname.trim();
    }

    /**
     * @return oushortname
     */
    public String getOushortname() {
        return oushortname;
    }

    /**
     * @param oushortname
     */
    public void setOushortname(String oushortname) {
        this.oushortname = oushortname == null ? null : oushortname.trim();
    }

    /**
     * @return ordernumber
     */
    public Integer getOrdernumber() {
        return ordernumber;
    }

    /**
     * @param ordernumber
     */
    public void setOrdernumber(Integer ordernumber) {
        this.ordernumber = ordernumber;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * @return postalcode
     */
    public String getPostalcode() {
        return postalcode;
    }

    /**
     * @param postalcode
     */
    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode == null ? null : postalcode.trim();
    }

    /**
     * @return tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel
     */
    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    /**
     * @return baseouguid
     */
    public String getBaseouguid() {
        return baseouguid;
    }

    /**
     * @param baseouguid
     */
    public void setBaseouguid(String baseouguid) {
        this.baseouguid = baseouguid == null ? null : baseouguid.trim();
    }

    /**
     * @return issubwebflow
     */
    public Integer getIssubwebflow() {
        return issubwebflow;
    }

    /**
     * @param issubwebflow
     */
    public void setIssubwebflow(Integer issubwebflow) {
        this.issubwebflow = issubwebflow;
    }

    /**
     * @return parentouguid
     */
    public String getParentouguid() {
        return parentouguid;
    }

    /**
     * @param parentouguid
     */
    public void setParentouguid(String parentouguid) {
        this.parentouguid = parentouguid == null ? null : parentouguid.trim();
    }

    /**
     * @return oucodelevel
     */
    public String getOucodelevel() {
        return oucodelevel;
    }

    /**
     * @param oucodelevel
     */
    public void setOucodelevel(String oucodelevel) {
        this.oucodelevel = oucodelevel == null ? null : oucodelevel.trim();
    }

    /**
     * @return haschildou
     */
    public Integer getHaschildou() {
        return haschildou;
    }

    /**
     * @param haschildou
     */
    public void setHaschildou(Integer haschildou) {
        this.haschildou = haschildou;
    }

    /**
     * @return haschilduser
     */
    public Integer getHaschilduser() {
        return haschilduser;
    }

    /**
     * @param haschilduser
     */
    public void setHaschilduser(Integer haschilduser) {
        this.haschilduser = haschilduser;
    }

    /**
     * @return updatetime
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * @param updatetime
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * @return ordernumberfull
     */
    public String getOrdernumberfull() {
        return ordernumberfull;
    }

    /**
     * @param ordernumberfull
     */
    public void setOrdernumberfull(String ordernumberfull) {
        this.ordernumberfull = ordernumberfull == null ? null : ordernumberfull.trim();
    }

    /**
     * @return oucodefull
     */
    public String getOucodefull() {
        return oucodefull;
    }

    /**
     * @param oucodefull
     */
    public void setOucodefull(String oucodefull) {
        this.oucodefull = oucodefull == null ? null : oucodefull.trim();
    }

    /**
     * @return dimension
     */
    public String getDimension() {
        return dimension;
    }

    /**
     * @param dimension
     */
    public void setDimension(String dimension) {
        this.dimension = dimension == null ? null : dimension.trim();
    }

    /**
     * @return baseoucode
     */
    public String getBaseoucode() {
        return baseoucode;
    }

    /**
     * @param baseoucode
     */
    public void setBaseoucode(String baseoucode) {
        this.baseoucode = baseoucode == null ? null : baseoucode.trim();
    }

    /**
     * @return unionguid
     */
    public String getUnionguid() {
        return unionguid;
    }

    /**
     * @param unionguid
     */
    public void setUnionguid(String unionguid) {
        this.unionguid = unionguid == null ? null : unionguid.trim();
    }

    /**
     * @return addtime
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * @return test
     */
    public String getTest() {
        return test;
    }

    /**
     * @param test
     */
    public void setTest(String test) {
        this.test = test == null ? null : test.trim();
    }

    /**
     * @return parentgabcode
     */
    public String getParentgabcode() {
        return parentgabcode;
    }

    /**
     * @param parentgabcode
     */
    public void setParentgabcode(String parentgabcode) {
        this.parentgabcode = parentgabcode == null ? null : parentgabcode.trim();
    }

    /**
     * @return gabcode
     */
    public String getGabcode() {
        return gabcode;
    }

    /**
     * @param gabcode
     */
    public void setGabcode(String gabcode) {
        this.gabcode = gabcode == null ? null : gabcode.trim();
    }

    /**
     * @return virtualou
     */
    public String getVirtualou() {
        return virtualou;
    }

    /**
     * @param virtualou
     */
    public void setVirtualou(String virtualou) {
        this.virtualou = virtualou == null ? null : virtualou.trim();
    }

    @Override
    public String toString() {
        return "FrameOu{" +
                "ouguid='" + ouguid + '\'' +
                ", oucode='" + ouCode + '\'' +
                ", ouname='" + ouname + '\'' +
                ", oushortname='" + oushortname + '\'' +
                ", ordernumber=" + ordernumber +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", postalcode='" + postalcode + '\'' +
                ", tel='" + tel + '\'' +
                ", baseouguid='" + baseouguid + '\'' +
                ", issubwebflow=" + issubwebflow +
                ", parentouguid='" + parentouguid + '\'' +
                ", oucodelevel='" + oucodelevel + '\'' +
                ", haschildou=" + haschildou +
                ", haschilduser=" + haschilduser +
                ", updatetime=" + updatetime +
                ", ordernumberfull='" + ordernumberfull + '\'' +
                ", oucodefull='" + oucodefull + '\'' +
                ", dimension='" + dimension + '\'' +
                ", baseoucode='" + baseoucode + '\'' +
                ", unionguid='" + unionguid + '\'' +
                ", addtime=" + addtime +
                ", test='" + test + '\'' +
                ", parentgabcode='" + parentgabcode + '\'' +
                ", gabcode='" + gabcode + '\'' +
                ", virtualou='" + virtualou + '\'' +
                '}';
    }
}