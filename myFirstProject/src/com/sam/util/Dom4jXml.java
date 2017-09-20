package com.sam.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Service("dom4jXml")
public class Dom4jXml {
	
	private static Map<String, String> valueMap = new HashMap<String, String>();
	
	public static String initSqlXml(String path, Map<String, String> params) throws Exception {
		try {
			if(params.get("id") == null){
				throw new Exception("sqlId为空!");
			}
			valueMap.clear();
			valueMap.putAll(params);
			SAXReader reader = new SAXReader();
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("GBK");// 设置XML文件的编码格式
			Document document = reader.read(new File(path));
			Element root = document.getRootElement();
			StringBuffer sql = new StringBuffer();
			
			Iterator<Element> iterator = root.elementIterator();
			while (iterator.hasNext()) {
				Element e = iterator.next();
				listNodes(e, sql);
			}
			
			String xmlsql = sql.toString();
			String querySql = "";
			for(Map.Entry<String, String> entry : valueMap.entrySet()){
				if(entry.getKey() != null){
				   String p = "{#" + entry.getKey() + "}";
				   querySql = xmlsql.replace( p, entry.getValue());
					xmlsql = querySql;
				}
			}
			return querySql;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	private static void listNodes(Element node, StringBuffer sql) {
		String sqls = "";
		if("sql".equals(node.getName())){
			List<Attribute> list = node.attributes();
			for(Attribute attribute : list){
				if("id".equals(attribute.getName()) && valueMap.get("id").equals(attribute.getValue())){
					if(node.getTextTrim() != null){
						sql.append(node.getTextTrim());
					}
					break;
				}
			}
		}
	}
	
	private String systemPath = System.getProperty("user.dir");

	public static void main(String[] args) {
		try {
			String path = System.getProperty("user.dir");
			path += "\\web\\sql\\test.xml";
			File file = new File(path);
			Map<String, String> params = new HashMap<String, String>();
			params.put("id", "test2");
			params.put("param", "where 1 = 1");
			String sql = "";
			if (file.exists()) {
				sql = initSqlXml(path,params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}







