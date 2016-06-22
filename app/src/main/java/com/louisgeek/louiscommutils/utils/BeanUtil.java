package com.louisgeek.louiscommutils.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;


public class BeanUtil {

   /* *//**
     * 使用Introspector进行转换
     *
     * Introspector 类为通过工具学习有关受目标 Java Bean 支持的属性、事件和方法的知识提供了一个标准方法。
     *
     * @param map
     * @param beanClass
     * @return
     * @throws Exception
     *//*
    public static Object mapToBean(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            Method setter = property.getWriteMethod();
            if (setter != null) {
                setter.invoke(obj, map.get(property.getName()));
            }
        }

        return obj;
    }

    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if(obj == null)
            return null;

        Map<String, Object> map = new HashMap<String, Object>();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter!=null ? getter.invoke(obj) : null;
            map.put(key, value);
        }

        return map;
    }
*/

    /**
     *  使用reflect进行转换
     * @param map
     * @param beanClass
     * @return
     * @throws Exception
     */
    public static Object map2Bean(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
        {
            return null;
        }
        Object obj = beanClass.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                continue;
            }

            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }

        return obj;
    }

    public static <K, V> Map<K, V> bean2Map(Object beanObj) throws Exception {
        if(beanObj == null){
            return null;
        }
        Map<K, V> map = new HashMap<>();

        Field[] declaredFields = beanObj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put((K)field.getName(), (V)field.get(beanObj));
        }
        return map;
    }

    /**
     * javaBean2Map   反射  第二种方式
     * @param javaBean
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> javaBean2Map(Object javaBean) {
        Map<K, V> ret = new HashMap<>();
        try {
            Method[] methods = javaBean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().startsWith("get")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);
                    Object value = method.invoke(javaBean, (Object[]) null);
                    ret.put((K) field, (V) (null == value ? "" : value));
                }
            }
        } catch (Exception e) {
        }
        return ret;
    }

    public static Map<String,String> toMap(Object object)
    {
        if(object == null)
        {
            return null;
        }

        Class<?> classType = object.getClass();
        Map<String, String> map = new HashMap<>();
        Field[] fields = classType.getDeclaredFields();

        try {
            for (Field field : fields)
            {
                String name = field.getName();
                Method method = classType.getMethod("get"+getMethodName(name));
                map.put(name, String.valueOf(method.invoke(object)));

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
           // L.i("BeanUtil.toMap Error: " + e.getMessage());
        }
        return map;
    }

    private static String getMethodName(String fildeName) {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }
}
