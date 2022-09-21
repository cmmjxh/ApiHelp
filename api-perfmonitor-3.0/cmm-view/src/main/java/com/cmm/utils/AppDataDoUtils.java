package com.cmm.utils;

import com.sun.tools.attach.VirtualMachineDescriptor;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author caomm
 * @Title 业务数据处理工具类
 * @Description TODO
 * @Date 2022/8/19 17:45
 */
public class AppDataDoUtils {

    /**
     * List<Map> 数据结构，指定字段分组
     * @Param data 待分组数据
     * @Param field 待分组字段，多个 , 号分割
     * <pre>
     *     不去除其他字段，需要注意的是其他未分组字段不建议后续使用
     * </pre>
     */
    public static List<VirtualMachineDescriptor> getGroupData(List<VirtualMachineDescriptor> data) {
        if (data.size() > 0 ) {
            data = data.stream().collect(Collectors.groupingBy(x ->
                    {
                        // 处理动态分组属性
                        return x.id()+" = "+x.displayName();
                    }
            )).values().stream().map(e -> {
                VirtualMachineDescriptor virtualMachineDescriptor = e.get(0);
                return virtualMachineDescriptor;

            }).collect(Collectors.toList());
        }
        return data;
    }

    /**
     * List<Map> 数据结构，指定字段分组求sum
     * @Param data 待分组数据
     * @Param field 待分组字段，多个 , 号分割
     * @Param sumfield 待求和字段，多个 , 号分割
     * <pre>
     *     不去除其他字段，需要注意的是其他未分组字段不建议后续使用
     * </pre>
     */
    public static List<Map<String, Object>> getGroupDataSum(List<Map<String, Object>> data, String field,String sumfield) {
        if (data.size() > 0 && !field.isEmpty() && !"null".equals(field) && !sumfield.isEmpty() && !"null".equals(sumfield) ) {
            String[] fields = field.split(",");
            String[] sumfields = sumfield.split(",");
            data = data.stream().collect(Collectors.groupingBy(x ->
                    {
                        // 处理动态分组属性
                        StringBuilder o = new StringBuilder();
                        if (fields.length > 0) {
                            for (int i = 0; i < fields.length; i++) {
                                String groupfield = fields[i];
                                o.append(String.valueOf(x.get(groupfield)) + ",");
                            }
                        }
                        return o.toString();
                    }
            )).values().stream().map(e -> {
                Map<String, Object> map = e.get(0);
                if (sumfields.length > 0) {
                    for (int i = 0; i < sumfields.length; i++) {
                        String sumfieldv = sumfields[i];
                        map.put(sumfieldv, e.stream().map(f ->
                                        new BigDecimal(String.valueOf(f.get(sumfieldv))))
                                .reduce(BigDecimal.ZERO, BigDecimal::add).toString());
                    }
                }
                return map;
            }).collect(Collectors.toList());
        }
        return data;
    }

    /**
     * List<Map> 数据结构，指定字段分组求max
     * @Param data 待分组数据
     * @Param field 待分组字段，多个 , 号分割
     * @Param sumfield 待求和字段，多个 , 号分割
     * <pre>
     *     不去除其他字段，需要注意的是其他未分组字段不建议后续使用
     * </pre>
     */
    public static List<Map<String, Object>> getGroupDataMax(List<Map<String, Object>> data, String field,String sumfield) {
        if (data.size() > 0 && !field.isEmpty() && !"null".equals(field) && !sumfield.isEmpty() && !"null".equals(sumfield) ) {
            String[] fields = field.split(",");
            String[] sumfields = sumfield.split(",");
            data = data.stream().collect(Collectors.groupingBy(x ->
                    {
                        // 处理动态分组属性
                        StringBuilder o = new StringBuilder();
                        if (fields.length > 0) {
                            for (int i = 0; i < fields.length; i++) {
                                String groupfield = fields[i];
                                o.append(String.valueOf(x.get(groupfield)) + ",");
                            }
                        }
                        return o.toString();
                    }
            )).values().stream().map(e -> {
                Map<String, Object> map = e.get(0);
                if (sumfields.length > 0) {
                    for (int i = 0; i < sumfields.length; i++) {
                        String sumfieldv = sumfields[i];
                        map.put(sumfieldv, e.stream().map(f ->
                                        new BigDecimal(String.valueOf(f.get(sumfieldv))))
                                .reduce(BigDecimal.ZERO, BigDecimal::max).toString());
                    }
                }
                return map;
            }).collect(Collectors.toList());
        }
        return data;
    }

    /**
     * List<Map> 数据结构，指定字段分组求min
     * @Param data 待分组数据
     * @Param field 待分组字段，多个 , 号分割
     * @Param sumfield 待求和字段，多个 , 号分割
     * <pre>
     *     不去除其他字段，需要注意的是其他未分组字段不建议后续使用
     * </pre>
     */
    public static List<Map<String, Object>> getGroupDataMin(List<Map<String, Object>> data, String field,String sumfield) {
        if (data.size() > 0 && !field.isEmpty() && !"null".equals(field) && !sumfield.isEmpty() && !"null".equals(sumfield) ) {
            String[] fields = field.split(",");
            String[] sumfields = sumfield.split(",");
            data = data.stream().collect(Collectors.groupingBy(x ->
                    {
                        // 处理动态分组属性
                        StringBuilder o = new StringBuilder();
                        if (fields.length > 0) {
                            for (int i = 0; i < fields.length; i++) {
                                String groupfield = fields[i];
                                o.append(String.valueOf(x.get(groupfield)) + ",");
                            }
                        }
                        return o.toString();
                    }
            )).values().stream().map(e -> {
                Map<String, Object> map = e.get(0);
                if (sumfields.length > 0) {
                    for (int i = 0; i < sumfields.length; i++) {
                        String sumfieldv = sumfields[i];
                        map.put(sumfieldv, e.stream().map(f ->
                                        new BigDecimal(String.valueOf(f.get(sumfieldv))))
                                .min(BigDecimal::compareTo).get());
                    }
                }
                return map;
            }).collect(Collectors.toList());
        }
        return data;
    }

    /**
     * List<Map> 数据结构，指定字段分组求avg
     * @Param data 待分组数据
     * @Param field 待分组字段，多个 , 号分割
     * @Param sumfield 待求和字段，多个 , 号分割
     * <pre>
     *     不去除其他字段，需要注意的是其他未分组字段不建议后续使用
     * </pre>
     */
    public static List<Map<String, Object>> getGroupDataAvg(List<Map<String, Object>> data, String field,String sumfield) {
        if (data.size() > 0 && !field.isEmpty() && !"null".equals(field) && !sumfield.isEmpty() && !"null".equals(sumfield) ) {
            String[] fields = field.split(",");
            String[] sumfields = sumfield.split(",");
            data = data.stream().collect(Collectors.groupingBy(x ->
                    {
                        // 处理动态分组属性
                        StringBuilder o = new StringBuilder();
                        if (fields.length > 0) {
                            for (int i = 0; i < fields.length; i++) {
                                String groupfield = fields[i];
                                o.append(String.valueOf(x.get(groupfield)) + ",");
                            }
                        }
                        return o.toString();
                    }
            )).values().stream().map(e -> {
                Map<String, Object> map = e.get(0);
                if (sumfields.length > 0) {
                    for (int i = 0; i < sumfields.length; i++) {
                        String sumfieldv = sumfields[i];
                        map.put(sumfieldv, e.stream().map(f ->
                                        new BigDecimal(String.valueOf(f.get(sumfieldv))))
                                .reduce(BigDecimal.ZERO, BigDecimal::add).divide(BigDecimal.valueOf(e.size()), 2, RoundingMode.HALF_UP));
                    }
                }
                return map;
            }).collect(Collectors.toList());
        }
        return data;
    }

    /**
     * 处理集合List<Map> , 用于实现分组结构封装（包括头和体）
     * @param obj0         头实体对象
     * @param obj02        身实体对象
     * @param groupfields1 分组字段，多个逗号分隔
     * @param resultList   结果集数据
     * @param childKeyName 子集key的别名
     */
    public static List<Map<String, Object>> getSelectGroupList(List<Map<String, Object>> resultList, Object obj0, Object obj02, String groupfields1, String childKeyName) {
        List<Map<String, Object>> result = new ArrayList<>();
        String arr[] = groupfields1.split(",");
        if (arr.length <= 0 || resultList.size() <= 0) {
            return null;
        }
        // 反射处理待处理字属性
        Class<?> aClass = obj0.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        Class<?> aClass2 = obj02.getClass();
        Field[] declaredFields2 = aClass2.getDeclaredFields();
        if (declaredFields.length <= 0 || declaredFields2.length <= 0) {
            return null;
        }
        // 处理-头输出属性字段
        resultList.stream().forEach(x -> {
            Map<String, Object> map = new HashMap<>();
            for (Field field : declaredFields) {
                String name = field.getName();
                map.put(name, x.get(name));
            }
            result.add(map);
        });
        // 去重处理
        List<Map<String, Object>> listHeader = setDistinct(result,arr);
        // 处理 - 身
        // 自定义条件分组
        Map<String, List<Map<String, Object>>> collectBody = setListGroup(resultList,arr);
        // 根据自定义条件获取单头对应单身
        listHeader.stream().forEach(x -> {
            // 处理自定义分组属性
            StringBuilder o = new StringBuilder();
            if (arr.length > 0) {
                for (int i = 0; i < arr.length; i++) {
                    String groupfield = arr[i];
                    o.append(String.valueOf(x.get(groupfield)) + ",");
                }
            }
            String keyValue = o.toString();
            List<Map<String, Object>> listBody = collectBody.get(keyValue);
            // 处理单身 输出属性字段
            List<Map<String, Object>> listBodyR = new ArrayList<>();
            listBody.stream().forEach(y -> {
                Map<String, Object> map = new HashMap<>();
                for (Field field : declaredFields2) {
                    String name = field.getName();
                    map.put(name, y.get(name));
                }
                listBodyR.add(map);
            });
            // 单身加入单头
            String childKeyNameResult = !childKeyName.isEmpty()?childKeyName:"child" ;
            x.put(childKeyNameResult, listBodyR);
        });
        return listHeader;
    }

    /**
     *  list去重
     *  @param list 待去重集合
     *  @param arr  动态分组字段
     * */
    public static List<Map<String, Object>> setDistinct(List<Map<String, Object>> list,String[] arr){
        List<Map<String, Object>> listR = list.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(
                                () -> new TreeSet<>(Comparator.comparing(m -> {
                                            // 处理动态分组属性
                                            StringBuilder o = new StringBuilder();
                                            if (arr.length > 0) {
                                                for (int i = 0; i < arr.length; i++) {
                                                    String groupfield = arr[i];
                                                    o.append(String.valueOf(m.get(groupfield)) + ",");
                                                }
                                            }
                                            return o.toString();
                                        }
                                ))
                        ), ArrayList::new
                )
        );
        return listR;
    }

    /**
     *  list分组
     *  @param list 待去重集合
     *  @param arr  动态分组字段
     * */
    public static Map<String, List<Map<String, Object>>> setListGroup(List<Map<String, Object>> list,String[] arr){
        // 自定义条件分组
        Map<String, List<Map<String, Object>>> collectBody =
                list.stream().collect(
                        Collectors.groupingBy(x -> {
                            // 处理动态分组属性
                            StringBuilder o = new StringBuilder();
                            if (arr.length > 0) {
                                for (int i = 0; i < arr.length; i++) {
                                    String groupfield = arr[i];
                                    o.append(String.valueOf(x.get(groupfield)) + ",");
                                }
                            }
                            return o.toString();
                        }));
        return collectBody;
    }
}
