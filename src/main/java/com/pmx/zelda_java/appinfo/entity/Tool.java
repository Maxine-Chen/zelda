package com.pmx.zelda_java.appinfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author pmx
 * @since 2025-08-29
 */
public class Tool implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String iconPath;

    /**
     * 文本区
     */
    private String toolText;

    /**
     * 图标顺序
     */
    private String toolOrder;

    /**
     * 跳转到的页面
     */
    private String toolUrl;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getToolText() {
        return toolText;
    }

    public void setToolText(String toolText) {
        this.toolText = toolText;
    }

    public String getToolOrder() {
        return toolOrder;
    }

    public void setToolOrder(String toolOrder) {
        this.toolOrder = toolOrder;
    }

    public String getToolUrl() {
        return toolUrl;
    }

    public void setToolUrl(String toolUrl) {
        this.toolUrl = toolUrl;
    }

    @Override
    public String toString() {
        return "Tool{" +
            "id = " + id +
            ", iconPath = " + iconPath +
            ", toolText = " + toolText +
            ", toolOrder = " + toolOrder +
            ", toolUrl = " + toolUrl +
            "}";
    }
}
