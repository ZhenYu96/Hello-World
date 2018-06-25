package org.hzero.generator.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.hzero.generator.dto.DDLEntity;
import org.hzero.generator.dto.GeneratorInfo;
import org.hzero.generator.service.IGeneratorService;
import org.hzero.generator.utils.DateUtils;
import org.hzero.generator.utils.PageUtils;
import org.hzero.generator.utils.Query;
import org.hzero.generator.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * 代码生成器
 * 
 * @name GeneratorController
 * @description
 * @author xianzhi.chen@hand-china.com 2018年1月31日下午5:18:10
 * @version
 */
@Controller
@RequestMapping("/sys/generator")
public class GeneratorController {
	@Autowired
	private IGeneratorService generatorService;

	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Result list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<Map<String, Object>> list = generatorService.queryList(query);
		int total = generatorService.queryTotal(query);

		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());

		return Result.ok().put("page", pageUtil);
	}

	/**
	 * DDD模型生成代码
	 */
	@RequestMapping("/ddd/code")
	public void codeByDDD(HttpServletRequest request, HttpServletResponse response) throws IOException {
		GeneratorInfo info = new GeneratorInfo();
		String[] tableNames = new String[] {};
		String tables = request.getParameter("tables");
		tableNames = JSON.parseArray(tables).toArray(tableNames);
		info.setTableNames(tableNames);
		info.setTablePrefix(request.getParameter("tablePrefix"));
		info.setPkg(request.getParameter("pkg"));
		info.setAuthor(request.getParameter("author"));
		byte[] data = generatorService.generatorCodeByDDD(info);
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"code" + DateUtils.format(new Date(),DateUtils.DATETIME_PATTERN) + ".zip\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");
		IOUtils.write(data, response.getOutputStream());
	}

	/**
	 * MVC模型生成代码
	 */
	@RequestMapping("/mvc/code")
	public void codeByMVC(HttpServletRequest request, HttpServletResponse response) throws IOException {
		GeneratorInfo info = new GeneratorInfo();
		String[] tableNames = new String[] {};
		String tables = request.getParameter("tables");
		tableNames = JSON.parseArray(tables).toArray(tableNames);
		info.setTableNames(tableNames);
		info.setModuleName(request.getParameter("moduleName"));
		info.setTablePrefix(request.getParameter("tablePrefix"));
		info.setPkg(request.getParameter("pkg"));
		info.setAuthor(request.getParameter("author"));
		byte[] data = generatorService.generatorCodeByMVC(info);
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"code" + DateUtils.format(new Date(),DateUtils.DATETIME_PATTERN) + ".zip\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");
		IOUtils.write(data, response.getOutputStream());
	}
	
	/**
	 * SQL语句执行
	 * description
	 * @param request
	 * @param response
	 * @param sql
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/execute/sql")
	public Result executeDDL(@RequestBody DDLEntity ddl) {
		try {
			generatorService.executeDDL(ddl.getSql());
		} catch (Exception e) {
			//e.printStackTrace();
			return Result.error("SQL执行错误，请检查！！！");
		}
		return Result.ok();
	}
	
}
