package com.demo.service;

import com.demo.dto.response.BaseRespDto;
import com.demo.pojo.QrtzJobDetails;
import com.github.pagehelper.Page;
import java.util.List;
import java.util.Map;
/**
 *
 * @author zje
 *
 */
public interface QrtzJobDetailsService {
	//增删改
	BaseRespDto createQrtzJobDetails(QrtzJobDetails qrtzJobDetails,BaseRespDto baseRespDto) throws Exception;
	
	Map<String, Object> updateQrtzJobDetails(QrtzJobDetails qrtzJobDetails) throws Exception;

	BaseRespDto deleteQrtzJobDetails(QrtzJobDetails qrtzJobDetails,BaseRespDto baseRespDto) throws Exception;
	
	//查询
	QrtzJobDetails findQrtzJobDetailsByPrimaryKey(String id);
	BaseRespDto findListByPage(QrtzJobDetails qrtzJobDetails,BaseRespDto baseRespDto, Integer page, Integer limit);
	BaseRespDto findMapListByPage(QrtzJobDetails qrtzJobDetails,BaseRespDto baseRespDto,Integer page,Integer limit);
	List<Map<String, Object>> findMapList(QrtzJobDetails qrtzJobDetails);
	List<QrtzJobDetails> findList(QrtzJobDetails qrtzJobDetails);
	
	Map<String, Object> pauseJob(QrtzJobDetails qrtzJobDetails) throws Exception;
	Map<String, Object> resumeJob(QrtzJobDetails qrtzJobDetails) throws Exception;
	
	
}
