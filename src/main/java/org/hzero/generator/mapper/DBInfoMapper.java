package org.hzero.generator.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 数据库对比Mapper
 * 
 * @author xianzhi.chen@hand-china.com 2018年9月17日上午11:21:08
 */
@Mapper
public interface DBInfoMapper {

    /**
     * 
     * 查询表
     * 
     * @param dbname
     * @return
     */
    List<String> selectDatabaseTable(String dbname);

    /**
     * 
     * 查询字段
     * 
     * @param dbname
     * @return
     */
    List<Map<String, String>> selectDatabaseColumn(String dbname);

    /**
     * 
     * 查询索引
     * 
     * @param dbname
     * @return
     */
    List<Map<String, String>> selectDatabaseIndex(String dbname);

}
