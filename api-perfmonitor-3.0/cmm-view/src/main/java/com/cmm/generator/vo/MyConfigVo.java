package com.cmm.generator.vo;

/**
 * @author caomm
 * @Title TODO
 * @Description TODO
 * @Date 2022/7/6 14:04
 */
public class MyConfigVo {
    private String interfactPath;
    private String implPath;
    private String productName;
    private String author;
    private String interfactName;
    private String controllerName;

    public String getInterfactPath() {
        return interfactPath;
    }

    public void setInterfactPath(String interfactPath) {
        this.interfactPath = interfactPath;
    }

    public String getImplPath() {
        return implPath;
    }

    public void setImplPath(String implPath) {
        this.implPath = implPath;
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

    public String getInterfactName() {
        return interfactName;
    }

    public void setInterfactName(String interfactName) {
        this.interfactName = interfactName;
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }
}
