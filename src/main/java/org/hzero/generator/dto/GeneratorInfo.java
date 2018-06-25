package org.hzero.generator.dto;

/**
 * 代码生成信息
 * 
 * @name GeneratorInfo
 * @description
 * @author xianzhi.chen@hand-china.com 2018年3月8日下午10:28:51
 * @version
 */
public class GeneratorInfo {

	private String moduleName;

	private String tablePrefix;

	private String pkg;

	private String author;

	private String[] tableNames;

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getTablePrefix() {
		return tablePrefix;
	}

	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}

	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String[] getTableNames() {
		return tableNames;
	}

	public void setTableNames(String[] tableNames) {
		this.tableNames = tableNames;
	}

}
