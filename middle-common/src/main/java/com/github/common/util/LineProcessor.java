package com.github.common.util;

import java.util.ArrayList;
import java.util.List;

public class LineProcessor {

	public static List<String> changeLines(List<String> readLines,String templdate, String targetValue) {
		List<String> resultLines = new ArrayList<String>();
		for (String line: readLines) {
			line = line.replace(templdate,targetValue);
			resultLines.add(line);
		}
		return resultLines;
	}

}
