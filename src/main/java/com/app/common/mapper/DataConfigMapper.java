package com.app.common.mapper;

import com.app.common.entity.DataConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DataConfigMapper extends BaseMapper<DataConfig> {

    /**
     * 获取显示所需要的值
     * @return
     * @param params
     */
    public List<DataConfig> getData(Map<String, String> params);
}
