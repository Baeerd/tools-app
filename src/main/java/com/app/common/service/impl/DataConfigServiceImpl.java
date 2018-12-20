package com.app.common.service.impl;

import com.app.common.entity.DataConfig;
import com.app.common.mapper.DataConfigMapper;
import com.app.common.service.DataConfigService;
import com.app.common.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataConfigServiceImpl extends BaseServiceImpl<DataConfig> implements DataConfigService {

    @Autowired
    private DataConfigMapper dataConfigMapper;

    @Override
    public String getData(Map<String, String> params) {
        List<DataConfig> dataConfigList = dataConfigMapper.getData(params);
        // 转换成json数组
        return Util.arrayToJson(dataConfigList);
    }
}
