package org.hzero.generator.service;

import java.util.List;
import java.util.Map;

import org.hzero.generator.dto.GeneratorInfo;

/**
 * 代码生成器服务接口类
 * description
 *
 * @author xianzhi.chen@hand-china.com 2018年6月19日下午3:21:43
 */
public interface IGeneratorService {

	List<Map<String, Object>> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);

	Map<String, String> queryTable(String tableName);

	List<Map<String, String>> queryColumns(String tableName);

	/**
	 * DDD模型代码生成
	 * description
	 * @param info
	 * @return
	 */
	byte[] generatorCodeByDDD(GeneratorInfo info);

	/**
	 * MVC模型代码生成
	 * description
	 * @param info
	 * @return
	 */
	byte[] generatorCodeByMVC(GeneratorInfo info);

	/**
	 * 执行SQL语句
	 * description
	 * @param sql
	 */
	void executeDDL(String sql);

}