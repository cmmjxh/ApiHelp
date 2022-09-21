package com.cmm.generator.vo;

/**
 * TODO 路径配置类
 *
 * @version 1.0
 * @author: caomm
 * @createTime: 2022/6/30 14:39
 */
public class PathConfigVo {
    /**
     * API规格请求地址
     */
    private String apiUrl;
    /**
     * 实体实现路径
     */
    private String codePathImpl;
    /**
     * 接口实现路径
     */
    private String codePathInterface;

    /**
     *  APIname
     * */
    private String apiName;

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getCodePathImpl() {
        return codePathImpl;
    }

    public void setCodePathImpl(String codePathImpl) {
        this.codePathImpl = codePathImpl;
    }

    public String getCodePathInterface() {
        return codePathInterface;
    }

    public void setCodePathInterface(String codePathInterface) {
        this.codePathInterface = codePathInterface;
    }
}