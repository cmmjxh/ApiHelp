package com.cmm.generator.vo;

import java.util.List;
import java.util.Map;

/**
 * TODO 类描述
 *
 * @version 1.0
 * @author: caomm
 * @createTime: 2022/6/30 16:12
 */
public class ApiInfoVo {
    private String apiName;
    private String author;
    private String time;
    private String type;
    private String description;
    private String productName;
    private String interfaceClassName;
    private String controllerClassName;
    private String serviceClassName;
    private String daoClassName;
    private List<Map<String,Object>> apiBody;

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

    public String getControllerClassName() {
        return controllerClassName;
    }

    public void setControllerClassName(String controllerClassName) {
        this.controllerClassName = controllerClassName;
    }

    public String getInterfaceClassName() {
        return interfaceClassName;
    }

    public void setInterfaceClassName(String interfaceClassName) {
        this.interfaceClassName = interfaceClassName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Map<String, Object>> getApiBody() {
        return apiBody;
    }

    public void setApiBody(List<Map<String, Object>> apiBody) {
        this.apiBody = apiBody;
    }
}