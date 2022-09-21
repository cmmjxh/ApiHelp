package com.cmm.generator.vo;

/**
 * TODO 路径配置类
 *
 * @version 1.0
 * @author: caomm
 * @createTime: 2022/6/30 14:39
 */
public class DescriptionConfigVo {
    /**
     * 作者名称
     */
    private String author;
    private String productName;
    private String interfactClassName;
    private String controllerClassName;
    private String serviceClassName;
    private String daoClassName;

    public String getDaoClassName() {
        return daoClassName;
    }

    public void setDaoClassName(String daoClassName) {
        this.daoClassName = daoClassName;
    }

    public String getServiceClassName() {
        return serviceClassName;
    }

    public void setServiceClassName(String serviceClassName) {
        this.serviceClassName = serviceClassName;
    }

    public String getInterfactClassName() {
        return interfactClassName;
    }

    public void setInterfactClassName(String interfactClassName) {
        this.interfactClassName = interfactClassName;
    }

    public String getControllerClassName() {
        return controllerClassName;
    }

    public void setControllerClassName(String controllerClassName) {
        this.controllerClassName = controllerClassName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}