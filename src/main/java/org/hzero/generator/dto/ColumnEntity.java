package org.hzero.generator.dto;

/**
 * 列的属性
 * 
 * @name ColumnEntity
 * @description
 * @author xianzhi.chen@hand-china.com 2018年1月31日下午5:19:15
 * @version
 */
public class ColumnEntity {
	// 列名
	private String columnName;
	// 列名（全大写）
	private String upperColumnName;
	// 列名类型
	private String dataType;
	// 列名备注
	private String comments;
	// 属性名称(第一个字母大写)，如：user_name => UserName
	private String attrName;
	// 属性名称(第一个字母小写)，如：user_name => userName
	private String attrname;
	// 属性类型
	private String attrType;
	// auto_increment
	private String extra;
	// JDBC类型
	private String jdbcType;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getUpperColumnName() {
		return upperColumnName;
	}

	public void setUpperColumnName(String upperColumnName) {
		this.upperColumnName = upperColumnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getAttrname() {
		return attrname;
	}

	public void setAttrname(String attrname) {
		this.attrname = attrname;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrType() {
		return attrType;
	}

	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

}
