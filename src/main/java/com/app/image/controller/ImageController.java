package com.app.image.controller;

import com.app.common.controller.BaseController;
import com.app.common.entity.AppConfig;
import com.app.common.entity.Response;
import com.app.common.exception.MessageException;
import com.app.image.entity.Image;
import com.app.image.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class ImageController extends BaseController<Image> {

    @Autowired
    private ImageService imageService;

    @Autowired
    private AppConfig appConfig;

    @RequestMapping("/test.do")
    public String test(@RequestParam Map<String, String> map) {
        if(1==1) {
            throw new MessageException(appConfig.getExceptionMessage1());
        }
        imageService.findAll();
        return "aaa2aaaaaa"+map+appConfig.getIndex();
    }

    @RequestMapping("/find")
    public ModelAndView find() {
        ModelAndView m = new ModelAndView("aaa");
        m.addObject("data", imageService.findAll());
        return m;
    }

    @RequestMapping("/testAjax")
    public Response testAjax(HttpServletRequest request) {
        String jsonFromRequest = super.getJsonFromRequest(request);
        System.out.println(jsonFromRequest);
        return new Response().success("SUCCESS!!!!!!!!!!!!!!");
    }

}
