package com.demo.service.serviceimpl;

import com.demo.dao.LogPointerMapper;
import com.demo.pojo.LogPointer;
import com.demo.service.LogPointerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
/**
 *
 * @author zje
 *
 */
@Service
public class LogPointerServiceImpl implements LogPointerService {

    @Resource
    private LogPointerMapper logPointerMapper;
    @Override
    public Long select() {
        LogPointer logPointer =  logPointerMapper.select();
        return logPointer==null?0L:logPointer.getPointer();
    }
}
