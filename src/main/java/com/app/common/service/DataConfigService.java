package com.app.common.service;

import com.app.common.entity.DataConfig;

import java.util.List;
import java.util.Map;

public interface DataConfigService extends BaseService<DataConfig> {
    /**
     * 获取显示所需要的值
     * @return
     * @param params
     */
    public String getData(Map<String, String> params);
}
