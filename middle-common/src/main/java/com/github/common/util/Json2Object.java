package com.github.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Json2Object {
	private static final BufferedReader	br	= new BufferedReader(new InputStreamReader(System.in));

	private static FileWriter getFileWriter(String className) throws IOException {
		File targetPath = new File("gen");
		if (!targetPath.exists()) {
			targetPath.mkdirs();
		}
		File file = new File(targetPath, StringUtils.capitalize(className) + ".java");
		return new FileWriter(file);
	}

	private static void cleanTargetPath() throws IOException {
		FileUtils.deleteDirectory(new File("gen"));
	}

	private static JSON getInputJson() throws IOException {
		System.out.println("请输入要转化为对象的Json字符串!");
		FileReader fr=new FileReader("json.txt");
        BufferedReader br=new BufferedReader(fr);
		/* String json = br.readLine(); */
		StringBuilder builder = new StringBuilder();
		String json = null;
		try {
			String temp;
			while((temp = br.readLine()) != null){
				System.out.println(temp);
				int common = temp.indexOf("//");
				if(common > 0) {
					temp = temp.substring(0, temp.indexOf("//"));
				}
				builder.append(temp);
		    }
			json = builder.toString();
			json = StringUtils.strip(json);
			if (json.startsWith("[")) {
				return JSONArray.parseArray(json);
			} else {
				return JSONObject.parseObject(json, JSONObject.class);
			}
		} catch (Exception e) {
			System.err.println("输入的不是标准的json,请重新输入Json字符串！");
			return getInputJson();
		}finally {
			fr.close();
		}
	}

	private static String getPkgOrClassName(String promotion) throws IOException {
		System.out.println("请输入" + promotion + "");
		String json = br.readLine();
		if (StringUtils.isNotEmpty(json)) {
			return json;
		} else {
			System.out.println("输入的" + promotion + "为空,请重新输入!");
			return getPkgOrClassName(promotion);
		}
	}

	private static void genJavaBean(JSON json, String pkgName, String className) throws IOException {
		List<String> readLines = ResouceUtil.getResouceLines("ClassTempldate.txt");
		readLines = LineProcessor.changeLines(readLines, "###pkgName###", pkgName);
		readLines = LineProcessor.changeLines(readLines, "###VoName###", StringUtils.capitalize(className));

		StringBuilder propertyBuilder = new StringBuilder();
		if (JSONObject.class.equals(json.getClass())) {
			JSONObject jsonObject = (JSONObject) json;
			for (String key : jsonObject.keySet()) {
				Object object = jsonObject.get(key);

				String matchType = matchType(object, key);
				propertyBuilder.append("public ").append(matchType).append(" ").append(key).append(";\r\n\t");
				if (JSONObject.class.equals(object.getClass())) {
					genJavaBean((JSONObject) object, pkgName, StringUtils.capitalize(key));
				} else if (JSONArray.class.equals(object.getClass())) {
					JSONArray jsonArray = (JSONArray) object;
					Iterator<Object> iterator = jsonArray.iterator();
					if (iterator.hasNext()) {// 如果集合的内容不为空的话，判定里面的内容的类型
						Object next = iterator.next();
						if (JSONObject.class.equals(next.getClass()) || JSONArray.class.equals(next.getClass())) {
							String innerClassName = getPkgOrClassName(next.toString());
							genJavaBean((JSON) next, pkgName, innerClassName);
						}
					}
				}
			}
		} else if (JSONArray.class.equals(json.getClass())) {
			JSONArray jsonArray = (JSONArray) json;
			Iterator<Object> iterator = jsonArray.iterator();
			if (iterator.hasNext()) {// 如果集合的内容不为空的话，判定里面的内容的类型
				Object next = iterator.next();
				System.out.println(next.getClass().getName());
				String matchType = matchType(next.getClass(), "");
				if (JSONObject.class.equals(next.getClass()) || JSONArray.class.equals(next.getClass())) {
					String innerClassName = getPkgOrClassName(next.toString());
					if (StringUtils.isEmpty(matchType)) {
						propertyBuilder.append("public ").append(StringUtils.capitalize(innerClassName)).append("[] ;\r\n\t");
					}
					genJavaBean((JSON) next, pkgName, innerClassName);
				} else {
					propertyBuilder.append("public ").append(matchType).append("[] ;\r\n\t");
				}

			} else {
				propertyBuilder.append("public String[] ").append(" ;\r\n\t");
			}
		} else {
			System.err.println("错误的类型，请修改程序支持");
		}
		readLines = LineProcessor.changeLines(readLines, "###columns###", propertyBuilder.toString());
		FileWriter fileWriter = getFileWriter(className);
		IOUtils.writeLines(readLines, null, fileWriter);
		fileWriter.close();
	}

	public static String matchType(Object object, String key) {
		if(object == null) {
			return "String";
		}
		Class<?> clazz = object.getClass();
		if (String.class.equals(clazz)) {
			return "String";
		} else if (Integer.class.equals(clazz)) {
			return "int";
		} else if (Float.class.equals(clazz)) {
			return "float";
		} else if (Date.class.equals(clazz)) {
			return "Date";
		} else if (Boolean.class.equals(clazz)) {
			return "boolean";
		} else if (Long.class.equals(clazz) || BigDecimal.class.equals(clazz)) {
			return "long";
		} else if (JSONObject.class.equals(clazz)) {
			return StringUtils.capitalize(key);
		} else if (JSONArray.class.equals(clazz)) {
			JSONArray jsonArray = (JSONArray) object;
			Iterator<Object> iterator = jsonArray.iterator();
			if (iterator.hasNext()) {// 如果集合的内容不为空的话，判定里面的内容的类型
				Object next = iterator.next();
				return matchType(next, key) + "[]";
			} else {// 如果集合为空的话，默认为空即可
				return "String[]";
			}
		}
		throw new RuntimeException("未知类型" + clazz);
	}

	public static void main(String[] args) throws IOException {
		JSON json = getInputJson();
		// String pkgName = getPkgOrClassName("包名");
		String pkgName = "com.test.cn";
		// String className = getPkgOrClassName("类名");
		String className = "query";
		cleanTargetPath();
		System.out.println(json.getClass());
		genJavaBean(json, pkgName, className);
	}

}