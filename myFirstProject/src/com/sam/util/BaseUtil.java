package com.sam.util;

import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class BaseUtil {

	public static String returnString(Object object) {
		try {
			return object.toString();
		} catch (Exception e) {
			return "";
		}
	}
	
	public static void addTimeToTable(Hashtable<String, Object> src, boolean newOrUpd) {
		
	}
	
	public static void tableToObject(Object target, Hashtable src)
			throws Exception {
		if (src == null || target == null){
			target = null;
			return;
		}

		Iterator<String> it = src.keySet().iterator();
		Method methods[] = target.getClass().getMethods();
		while (it.hasNext()) {
			String key = it.next();
			String value = String.valueOf(src.get(key));
			String methordName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].getName().equalsIgnoreCase(methordName)) {
					Class type = methods[i].getParameterTypes()[0];
					Object retValue = null;
					if ("".equals(value) || value.equals("null")) {
				      retValue = null;
					}
					else{
						retValue = parseParamenter(type, value);
					}
					methods[i].invoke(target, new Object[] { retValue });
					break;
				}
			}
		}
		return;
	}
	
	private static Object parseParamenter(Class type, Object strValue) throws Exception {
		String typeName = type.getName();
		Object ret = null;
		if (type.isPrimitive()) {
			if ("int".equals(typeName))
				type = Class.forName("java.lang.Integer");
			else if ("long".equals(typeName))
				type = Class.forName("java.lang.Long");
			else if ("float".equals(typeName))
				type = Class.forName("java.lang.Float");
			else if ("double".equals(typeName))
				type = Class.forName("java.lang.Double");
			else if ("boolean".equals(typeName))
				type = Class.forName("java.lang.Boolean");
			else if ("char".equals(typeName))
				type = Class.forName("java.lang.Character");
			else if ("byte".equals(typeName))
				type = Class.forName("java.lang.Byte");
		}

		if ("java.util.Date".equals(typeName) || "java.sql.Date".equals(typeName)) {
			String value = strValue.toString().trim();
			if (value.indexOf(":") == -1)
				value += " 00:00:00";
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			ret = format.parse(value);
		} else if (typeName.equals("java.lang.String")) {
			ret = strValue.toString();
		} else {
			if (typeName.equals("java.sql.Timestamp") && strValue != null) {
				String value = strValue.toString().trim();
				int len = value.trim().length();
				if (len > 7 && len < 11) {
					value = value + " 00:00:00.0";
				} else if (len > 11 && value.indexOf(".") == -1) {
					value = value + ".0";
				}
				strValue = value;
			} else if (typeName.equals("java.sql.Date") && strValue != null) {
				String value = strValue.toString().trim();
				if (value.length() > 10) {
					value = value.substring(0, 10);
				}
				strValue = value;
			}
			Method method = type.getMethod("valueOf", new Class[] { "java.lang.String".getClass() });
			ret = method.invoke(type, new Object[] { strValue.toString() });
		}

		return ret;
	}
	
	public void objectToTable(Hashtable target, Object src) throws Exception {
		if (src == null || target == null)
			return;
		Method methods[] = src.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			String method = methods[i].getName();
			if (method.startsWith("get") && !"getClass".equals(method)) {
				Class type = methods[i].getReturnType();
				String key = method.replaceFirst("get", "");
				key = key.substring(0, 1).toLowerCase() + key.substring(1);
				Object value = methods[i].invoke(src, (Object[]) null);
				if (value == null)
					target.put(key, "");
				else {
					target.put(key, parseParamenter(type, value));
				}
			}
		}
	}
	
}
