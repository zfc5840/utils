package com.java.utils.javaUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Description: 对象工具处理类
 * All Rights Reserved.
 * @version 1.0  2015-3-9 下午1:51:46  by 张富成（fc.zhang@zuche.com）创建
 */
public class ObjectUtil {

	/**
	 * Description: 对象所有类型为String的属性去空格
	 * @Version1.0 2015-3-9 下午1:52:06 by 张富成（fc.zhang@zuche.com）创建
	 * @param obj
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object trimObject(Object obj){
		Class tempClass = obj.getClass();
		Field[] tempField = tempClass.getDeclaredFields();
		for(int i=0;i<tempField.length;i++){
			Field field=tempField[i];
			String fieldName = field.getName();
			if(fieldName.equals("serialVersionUID"))
				continue;
			String newName = fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
			String getMethodName="get"+newName;
			String setMethodName="set"+newName;
			Method getMethod ,setMethod;
			try {
				getMethod = tempClass.getMethod(getMethodName, new Class[]{});
				Object value = getMethod.invoke(obj, new Object[]{});
				if(value == null) 
					continue;
				if(value instanceof String){
					value=String.valueOf(value).trim();
				}
				if("java.lang.String".equals(field.getType().getName())){
					setMethod = tempClass.getMethod(setMethodName, new Class[]{field.getType()});
					setMethod.invoke(obj, new Object[]{value});
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
}
