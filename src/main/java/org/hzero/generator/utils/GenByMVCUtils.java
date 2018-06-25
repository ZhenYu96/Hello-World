package org.hzero.generator.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.hzero.generator.dto.ColumnEntity;
import org.hzero.generator.dto.GeneratorInfo;
import org.hzero.generator.dto.TableEntity;

/**
 * 代码生成器 工具类
 * 
 * @name GenUtils
 * @description
 * @author xianzhi.chen@hand-china.com 2018年1月31日下午5:22:25
 * @version
 */
public class GenByMVCUtils {

	public static List<String> getTemplates() {
		List<String> templates = new ArrayList<String>();
		templates.add("template/mvc/Dto.java.vm");
		templates.add("template/mvc/Mapper.java.vm");
		templates.add("template/mvc/Mapper.xml.vm");
		templates.add("template/mvc/IService.java.vm");
		templates.add("template/mvc/ServiceImpl.java.vm");
		templates.add("template/mvc/Controller.java.vm");
		return templates;
	}

	/**
	 * 生成代码
	 */
	public static void generatorCode(GeneratorInfo info, Map<String, String> table, List<Map<String, String>> columns,
			ZipOutputStream zip) {
		// 配置信息
		Configuration config = getConfig();
		boolean hasBigDecimal = false;
		// 表信息
		TableEntity tableEntity = new TableEntity();
		tableEntity.setTableName(table.get("tableName"));
		tableEntity.setComments(table.get("tableComment"));
		// 表名转换成Java类名
		String className = tableToJava(tableEntity.getTableName(),
				StringUtils.isBlank(info.getTablePrefix()) ? config.getString("tablePrefix") : info.getTablePrefix());
		tableEntity.setClassName(className);
		tableEntity.setClassname(StringUtils.uncapitalize(className));

		// 列信息
		List<ColumnEntity> columsList = new ArrayList<>();
		for (Map<String, String> column : columns) {
			ColumnEntity columnEntity = new ColumnEntity();
			columnEntity.setColumnName(column.get("columnName"));
			columnEntity.setUpperColumnName(
					StringUtils.upperCase(column.get("columnName").replace(info.getTablePrefix(), "")));
			columnEntity.setDataType(column.get("dataType"));
			columnEntity.setComments(column.get("columnComment"));
			columnEntity.setExtra(column.get("extra"));

			// 列名转换成Java属性名
			String attrName = columnToJava(columnEntity.getColumnName());
			columnEntity.setAttrName(attrName);
			columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

			// 列的数据类型，转换成Java类型
			String attrType = config.getString(columnEntity.getDataType(), "unknowType");
			columnEntity.setAttrType(attrType);
			if (!hasBigDecimal && attrType.equals("BigDecimal")) {
				hasBigDecimal = true;
			}
			// JDBC类型
			columnEntity.setJdbcType(config.getString(attrType));
			// 是否主键
			if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null) {
				tableEntity.setPk(columnEntity);
			}

			columsList.add(columnEntity);
		}
		tableEntity.setColumns(columsList);

		// 没主键，则第一个字段为主键
		if (tableEntity.getPk() == null) {
			tableEntity.setPk(tableEntity.getColumns().get(0));
		}

		// 设置velocity资源加载器
		Properties prop = new Properties();
		prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.init(prop);

		// 封装模板数据
		Map<String, Object> map = new HashMap<>();
		map.put("tableName", tableEntity.getTableName());
		map.put("comments", tableEntity.getComments());
		map.put("pk", tableEntity.getPk());
		map.put("className", tableEntity.getClassName());
		map.put("classname", tableEntity.getClassname());
		map.put("upperClassName", StringUtils.upperCase(tableEntity.getTableName()));
		map.put("pathName", tableEntity.getClassname().toLowerCase());
		map.put("columns", tableEntity.getColumns());
		map.put("hasBigDecimal", hasBigDecimal);
		map.put("package", StringUtils.isBlank(info.getPkg()) ? config.getString("pkg") : info.getPkg());
		map.put("moduleName",
				StringUtils.isBlank(info.getModuleName()) ? config.getString("moduleName") : info.getModuleName());
		map.put("author", StringUtils.isBlank(info.getAuthor()) ? config.getString("author") : info.getAuthor());
		map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
		VelocityContext context = new VelocityContext(map);

		// 获取模板列表
		List<String> templates = getTemplates();
		for (String template : templates) {
			// 渲染模板
			StringWriter sw = new StringWriter();
			Template tpl = Velocity.getTemplate(template, "UTF-8");
			tpl.merge(context, sw);

			try {
				// 添加到zip
				zip.putNextEntry(new ZipEntry(getFileName(template, tableEntity.getClassName(),
						map.get("package").toString(), map.get("moduleName").toString())));
				IOUtils.write(sw.toString(), zip, "UTF-8");
				IOUtils.closeQuietly(sw);
				zip.closeEntry();
			} catch (IOException e) {
				throw new GenException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
			}
		}
	}

	/**
	 * 列名转换成Java属性名
	 */
	public static String columnToJava(String columnName) {
		return WordUtils.capitalizeFully(columnName, new char[] { '_' }).replace("_", "");
	}

	/**
	 * 表名转换成Java类名
	 */
	public static String tableToJava(String tableName, String tablePrefix) {
		if (StringUtils.isNotBlank(tablePrefix)) {
			tableName = tableName.replace(tablePrefix, "");
		}
		return columnToJava(tableName);
	}

	/**
	 * 获取配置信息
	 */
	public static Configuration getConfig() {
		try {
			return new PropertiesConfiguration("generator.properties");
		} catch (ConfigurationException e) {
			throw new GenException("获取配置文件失败，", e);
		}
	}

	/**
	 * 获取文件名
	 */
	public static String getFileName(String template, String className, String packageName, String moduleName) {
		String packagePath = "main" + File.separator + "java" + File.separator;
		if (StringUtils.isNotBlank(packageName)) {
			packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
		}

		if (template.contains("Dto.java.vm")) {
			return packagePath + "dto" + File.separator + className + ".java";
		}

		if (template.contains("Mapper.java.vm")) {
			return packagePath + "mapper" + File.separator + className + "Mapper.java";
		}

		if (template.contains("IService.java.vm")) {
			return packagePath + "service" + File.separator + "I" + className + "Service.java";
		}

		if (template.contains("ServiceImpl.java.vm")) {
			return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
		}

		if (template.contains("Controller.java.vm")) {
			return packagePath + "controller" + File.separator + className + "Controller.java";
		}

		if (template.contains("Mapper.xml.vm")) {
			return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + moduleName
					+ File.separator + className + "Mapper.xml";
		}
		return null;
	}
}
