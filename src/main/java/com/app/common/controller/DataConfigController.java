package com.app.common.controller;

import com.app.common.entity.DataConfig;
import com.app.common.entity.Response;
import com.app.common.service.DataConfigService;
import com.app.common.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 对配置表进行操作的Controller
 */
@RestController
@RequestMapping("/dataConfig")
public class DataConfigController extends BaseController<DataConfig>{

    @Autowired
    private DataConfigService dataConfigService;

    @RequestMapping("/getData")
    public Response getData(HttpServletRequest request) {
        String jsonStr = this.getJsonFromRequest(request);
        Map<String, String> params = Util.jsonToMap(jsonStr);
        String result = dataConfigService.getData(params);
        return new Response().success(result);
    }
}
