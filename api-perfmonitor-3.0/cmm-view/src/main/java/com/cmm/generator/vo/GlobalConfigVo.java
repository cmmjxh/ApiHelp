package com.cmm.generator.vo;

/**
 * TODO 配置体
 *
 * @version 1.0
 * @author: caomm
 * @createTime: 2022/6/30 13:56
 */
public class GlobalConfigVo {

    private ControlConfigVo controlConfigVo;
    private PathConfigVo pathConfigVo;
    private DescriptionConfigVo descriptionConfigVo;

    public DescriptionConfigVo getDescriptionConfigVo() {
        return descriptionConfigVo;
    }

    public void setDescriptionConfigVo(DescriptionConfigVo descriptionConfigVo) {
        this.descriptionConfigVo = descriptionConfigVo;
    }

    public ControlConfigVo getControlConfigVo() {
        return controlConfigVo;
    }

    public void setControlConfigVo(ControlConfigVo controlConfigVo) {
        this.controlConfigVo = controlConfigVo;
    }

    public PathConfigVo getPathConfigVo() {
        return pathConfigVo;
    }

    public void setPathConfigVo(PathConfigVo pathConfigVo) {
        this.pathConfigVo = pathConfigVo;
    }
}