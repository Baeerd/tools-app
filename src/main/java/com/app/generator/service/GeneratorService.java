package com.app.generator.service;

import com.app.common.service.BaseService;
import com.app.generator.entity.Generator;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

public interface GeneratorService extends BaseService<Generator> {

    void generatorCode(HttpServletResponse response, String filePath, String tableName);
}
