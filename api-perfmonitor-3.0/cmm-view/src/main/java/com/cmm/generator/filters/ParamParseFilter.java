package com.cmm.generator.filters;

import com.cmm.generator.DWAbstractGeneratorFilter;
import com.cmm.generator.http.DWHttpData;
import com.cmm.generator.vo.ApiInfoVo;
import com.cmm.generator.vo.GlobalConfigVo;
import com.cmm.utils.LogOutQueue;
import com.cmm.utils.TimeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Map;

/**
 * TODO API规格解析器
 *
 * @version 1.0
 * @author: caomm
 * @createTime: 2022/6/29$ 14:46$
 */
public class ParamParseFilter extends DWAbstractGeneratorFilter {
    private final static Log log = LogFactory.getLog(ParamParseFilter.class);

    @Override
    public void doExecute(GlobalConfigVo globalConfigVo, Object object, Chain chain) throws Exception {
        log.info("API规格解析-------------------解析中");
        LogOutQueue.add(TimeUtils.newTime()+" 信息: "+"API规格解析-------------------解析中");
        Map apiMetadata = DWHttpData.getApiMetadata(globalConfigVo.getPathConfigVo().getApiName());
        String code = (String) apiMetadata.get("code");
        ApiInfoVo apiInfoVo = new ApiInfoVo();
        if ("0".equals(code)) {
            Map descriptionMap = (Map) apiMetadata.get("description");
            List<Map<String, Object>> apiBody = getApiRequestBody(apiMetadata);
            apiInfoVo.setApiName((String) apiMetadata.get("api_name"));
            apiInfoVo.setProductName(globalConfigVo.getDescriptionConfigVo().getProductName());
            apiInfoVo.setAuthor(globalConfigVo.getDescriptionConfigVo().getAuthor());
            apiInfoVo.setType((String) apiMetadata.get("type"));
            apiInfoVo.setDescription((String) descriptionMap.get("zh_CN"));
            apiInfoVo.setTime(TimeUtils.newTime());
            // 接口名称配置注入
            apiInfoVo.setInterfaceClassName(globalConfigVo.getDescriptionConfigVo().getInterfactClassName());
            // 控制类名称注入
            apiInfoVo.setControllerClassName(globalConfigVo.getDescriptionConfigVo().getControllerClassName());
            // 实现类名称注入
            apiInfoVo.setServiceClassName(globalConfigVo.getDescriptionConfigVo().getServiceClassName());
            // Dao名称注入
            apiInfoVo.setDaoClassName(globalConfigVo.getDescriptionConfigVo().getDaoClassName());
            apiInfoVo.setApiBody(apiBody);
        } else if ("201".equals(code)) {
            LogOutQueue.add(TimeUtils.newTime()+" 错误: "+"API不存在!");
            throw new Error("API不存在!");
        } else if ("500".equals(code)) {
            LogOutQueue.add(TimeUtils.newTime()+" 错误: "+"API元数据获取失败！");
            throw new Error("API元数据获取失败！");
        }
        LogOutQueue.add(TimeUtils.newTime()+" 信息: "+"API规格解析-------------------解析完成");
        chain.doFilter(apiInfoVo);
    }

    /**
     * 获取API请求体数组
     */
    private List<Map<String, Object>> getApiRequestBody(Map descriptionMap) {
        Map dataMetadata = (Map) descriptionMap.get("data_metadata");
        Map request = (Map) dataMetadata.get("request");
        Map body = (Map) request.get("body");
        if (body != null) {
             String dataName = (String) body.get("data_name");
             if("std_data".equals(dataName)){
                 List<Map<String, Object>> fieldList = (List<Map<String, Object>>) body.get("field");
                 if (fieldList.size() > 0) {
                     return (List<Map<String, Object>>) fieldList.get(0).get("field");
                 }
             }
        }
        return null;
    }
}