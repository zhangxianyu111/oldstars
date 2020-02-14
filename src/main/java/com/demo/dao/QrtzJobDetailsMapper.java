package com.demo.dao;

import com.demo.pojo.QrtzJobDetails;

import java.util.List;
import java.util.Map;
/**
 *
 * @author zje
 *
 */
public interface QrtzJobDetailsMapper {
	//增加记录
	int insert(QrtzJobDetails qrtzJobDetails);
	int insertSelective(QrtzJobDetails qrtzJobDetails);
	int insertByBatch(List<QrtzJobDetails> list);
	//删除记录
	int deleteByPrimaryKey(String id);
	
	//修改记录
	int updateByPrimaryKey(QrtzJobDetails qrtzJobDetails);
	int updateByPrimaryKeySelective(QrtzJobDetails qrtzJobDetails);
	
	//查询记录
	QrtzJobDetails selectByPrimaryKey(String id);
	
	//查询记录列表
	List<QrtzJobDetails> selectList(QrtzJobDetails qrtzJobDetails);
	
	List<Map<String, Object>> selectMapList(QrtzJobDetails qrtzJobDetails);

	//查询数量
    Long count(QrtzJobDetails qrtzJobDetails);
	Long selectMapListCount(QrtzJobDetails qrtzJobDetails);
}
