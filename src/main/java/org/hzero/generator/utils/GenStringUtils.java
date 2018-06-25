package org.hzero.generator.utils;

public class GenStringUtils {
	
	public static String SqlFilter(String str) {
		String[] pattern = { "select", "insert", "delete", "from", "count\\(", "drop table", "update", "truncate",
				"asc\\(", "mid\\(", "char\\(", "xp_cmdshell", "exec   master", "netlocalgroup administrators",
				"net user", "CR", "LF", "BS", "or", "and",";" };
		for (int i = 0; i < pattern.length; i++) {
			str = str.replace(pattern[i].toString(), "");
		}
		return str;
	}

}
