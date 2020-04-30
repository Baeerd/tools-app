package com.app.generator.service;

import com.app.common.entity.PageModel;
import com.app.common.service.BaseService;
import com.app.generator.entity.Generator;
import com.app.generator.entity.TableDetail;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface GeneratorService extends BaseService<Generator> {

    void generatorCode(HttpServletResponse response, String filePath, Map<String, String> params);

    List<TableDetail> getTableDetail(String tableName);

    List<Generator> getTables(String tableName);

    void saveJdbcConfig(Map<String, String> params);

    Map<String, String> getJdbcConfig();

}
