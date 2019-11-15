package com.github.common.util;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class ResouceUtil {

	public static List<String> getResouceLines(String fileName){
		try(InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);) {
			return IOUtils.readLines(resourceAsStream);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
