package indi.zz.dp;

import com.alibaba.fastjson.JSON;
import com.dywl.utils.BeanUtil;
import com.dywl.utils.StringUtil;
import com.google.common.base.Splitter;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.util.ReflectionUtils;

public class ParamUtil {

    public static void main(String[] args) {

        // n    54321
        // 1-n间数字，每位相差至少为2的个数
        // f(i, j, k)
        // f(i, 0) n 小于前i位组合的结果数

        // 如果 f(i, k, 0)  f(i, k, 1)
        //   数字n 前i位组成的前缀， f(i, k, 0) 表示小于前缀的情况，k为i-1位选值
        //
        // f(i, 1) n 等于前i位组合的结果数




    }

    @Data
    static class A {
        private List<Integer> values;

        private Date date;
    }

    public static <T> T parse(Map<String, String> param, Class<T> obj) {
        return BeanUtil.mapToClass(mapReflect(obj, param), obj);
    }

    private static Map<String, Object> mapReflect(Class model, Map<String, String> map) {

        Map<String, Object> result = new HashMap<>();

        // 获取实体类的所有属性，返回Field数组
        Field[] fields = model.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            // 获取属性类型
            Field field = fields[i];
            String type = field.getGenericType().toString();

            field.setAccessible(true);
            String val = map.get(field.getName());
            if ("java.util.List<java.lang.Integer>".equals(type)) {
                result.put(field.getName(), splitAndConvert(val, Integer::valueOf));
            } else if ("java.util.List<java.lang.Long>".equals(type)) {
                result.put(field.getName(), splitAndConvert(val, Long::valueOf));
            } else if ("java.util.List<java.lang.String>".equals(type)) {
                result.put(field.getName(), splitAndConvert(val, a -> a));
            } else {
                result.put(field.getName(), val);
            }
        }

        if (!Objects.equals(model.getSuperclass(), Object.class)) {
            Map<String, Object> sup = mapReflect(model.getSuperclass(), map);
            result.putAll(sup);
        }

        return result;
    }

    public static Integer getInteger(Map<String, String> param, String name) {
        String val = param.get(name);
        if (StringUtil.isEmpty(val)) {
            return null;
        }

        try {
            return Integer.parseInt(val);
        } catch (Exception e) {
            return null;
        }
    }

    public static Long getLong(Map<String, String> param, String name) {
        String val = param.get(name);
        if (StringUtil.isEmpty(val)) {
            return null;
        }

        try {
            return Long.parseLong(val);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 分割字符串
     * @param str
     * @return
     */
    private static <T> List<T> splitAndConvert(String str, Function<String, T> converter) {
        if (str == null) {
            return null;
        }
        List<String> strings = split(str);
        return strings.stream().map(converter).collect(Collectors.toList());
    }

    private static List<String> split(String str) {
        return StringUtil.isEmpty(str) ? new ArrayList<>() : Arrays.asList(str.trim().split(",| "));
    }

//    /**
//     * 校验字段是否修改
//     * @param obj1
//     * @param obj2
//     * @param fields
//     * @return
//     */
//    public static Boolean checkFieldsChange(Object obj1, Object obj2, List<String> fields) {
//        try {
//            PropertyDescriptor[] fields1 = BeanUtils.getPropertyDescriptors(obj1.getClass());
//            for (PropertyDescriptor propertyDescriptor : fields1) {
//                if(fields.contains(propertyDescriptor.getName())){
//                    Method method = propertyDescriptor.getReadMethod();
//                    Object val1 = method.invoke(obj1);
//                    Object val2 = method.invoke(obj2);
//                    if(!Objects.equals(val1,val2)){
//                        return true;
//                    }
//                }
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return false;
//    }

    /**
     * ","->[]
     *
     * @param model
     * @param map
     * @return
     * @throws SecurityException
     * @throws IllegalArgumentException
     */
    public static Map<String, Object> mapObjectReflect(Class<?> model, Map<String, String> map)
        throws SecurityException, IllegalArgumentException {

        Map<String, Object> objectMap = new HashMap<>();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            objectMap.put(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, String> e : map.entrySet()) {
            String filedName = e.getKey();
            String values = e.getValue();
            Field field= ReflectionUtils.findField(model,filedName);
            if(field==null){
                continue;
            }
            String type = field.getGenericType().getTypeName();
            if(!type.startsWith("java.util.List")){
                continue;
            }
            if ("java.util.List<java.lang.String>".equals(type)) {
                if (StringUtil.isNotEmpty(values)) {
                    objectMap.put(filedName, spl(values));
                }
            } else if ("java.util.List<java.lang.Integer>".equals(type)) {
                if (StringUtil.isNotEmpty(values)) {
                    objectMap.put(filedName,
                        spl(values).stream().map(Integer::valueOf).collect(Collectors.toList()));
                }
            }else if ("java.util.List<java.lang.Long>".equals(type)) {
                if (StringUtil.isNotEmpty(values)) {
                    objectMap.put(filedName,
                        spl(values).stream().map(Long::valueOf).collect(Collectors.toList()));
                }
            }
        }
        return objectMap;
    }
    /**
     * 分割字符串
     *
     * @param str
     * @return
     */
    public static List<String> spl(String str) {
        if (str == null) {
            return null;
        }
        return Splitter.onPattern(",|\\s").trimResults().omitEmptyStrings().splitToList(str);

    }
}
