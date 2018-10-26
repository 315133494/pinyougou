package com.pinyougou.pojo;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-10-04
 */
@TableName("tb_freight_template")
public class FreightTemplate extends Model<FreightTemplate> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 商家ID
     */
    @TableField("seller_id")
    private String sellerId;
    /**
     * 是否默认   （‘Y’是   'N'否）
     */
    @TableField("is_default")
    private String isDefault;
    /**
     * 模版名称
     */
    private String name;
    /**
     * 发货时间（1:12h  2:24h  3:48h  4:72h  5:7d 6:15d ）
     */
    @TableField("send_time_type")
    private String sendTimeType;
    /**
     * 统一价格
     */
    private BigDecimal price;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSendTimeType() {
        return sendTimeType;
    }

    public void setSendTimeType(String sendTimeType) {
        this.sendTimeType = sendTimeType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FreightTemplate{" +
        ", id=" + id +
        ", sellerId=" + sellerId +
        ", isDefault=" + isDefault +
        ", name=" + name +
        ", sendTimeType=" + sendTimeType +
        ", price=" + price +
        ", createTime=" + createTime +
        "}";
    }
}
