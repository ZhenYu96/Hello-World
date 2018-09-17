package org.hzero.generator.service;

import java.util.List;
import java.util.Map;

import org.jdom2.Document;

/**
 * 
 * 数据库差异对比接口
 * 
 * @author xianzhi.chen@hand-china.com 2018年9月17日下午1:48:54
 */
public interface IDBDiffService {

    /**
     * 
     * 数据库表信息
     * 
     * @param env
     * @param dbname
     * @return
     */
    List<String> selectDatabaseTable(String env, String dbname);

    /**
     * 
     * 数据库字段信息
     * 
     * @param env
     * @param dbname
     * @return
     */
    List<Map<String, String>> selectDatabaseColumn(String env, String dbname);

    /**
     * 
     * 数据库索引信息
     * 
     * @param env
     * @param dbname
     * @return
     */
    List<Map<String, String>> selectDatabaseIndex(String env, String dbname);
    
    /**
     * 
     * 数据库差异信息XML
     * @param sourceEnv
     * @param sourceDB
     * @param targetEnv
     * @param targetDB
     * @return
     */
    Document compareDiff(String sourceEnv, String sourceDB, String targetEnv, String targetDB);

}
