package com.app.common.controller;

import com.app.common.entity.AppConfig;
import com.app.common.entity.Response;
import com.app.common.util.Util;
import com.app.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

import static java.net.URLEncoder.encode;

/**
 * 通用Controller，页面跳转等功能
 */
@Controller
public class CommonController {

    @Autowired
    private AppConfig appConfig;

    @RequestMapping("/{page}.html")
    public String showPage(@PathVariable String page) {
        return page;
    }

    /**
     * 跳转到首页,首页路径在application.yml文件中配置
     * @param model 可以向请求中放值
     * @return
     */
    @RequestMapping("/")
    public String index(Model model) {
        User user = new User();
        user.setUsername("username1");
        user.setPassword("password1");
        user.setLoginDt(new Date());
        System.out.println(Util.beanToJson(user));
        model.addAttribute("user", Util.beanToJson(user));
        return appConfig.getIndex();
    }

    /**
     * 实现文件上传
     * */
    @RequestMapping("/fileUpload")
    @ResponseBody
    public Response fileUpload(@RequestParam("fileName") MultipartFile file, HttpServletRequest request){
        if(file.isEmpty()){
            return new Response().failure();
        }
        String realPath=request.getServletContext().getRealPath("/templates");
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);

        File dest = new File(realPath + "/" + fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            return new Response().success();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return new Response().failure();
        }
    }

    @RequestMapping("/download")
    public void downLoad(HttpServletResponse response, HttpServletRequest request, @RequestParam("fileName") String fileName) throws UnsupportedEncodingException {
        String realPath=request.getServletContext().getRealPath("/templates");
        File file = new File(realPath + "/" + fileName);
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + encode(fileName,"UTF-8"));
            FileInputStream fis = null; //文件输入流
            OutputStream toClient = null;
            try {
                toClient=new BufferedOutputStream(response.getOutputStream());//获取二进制输出流
                fis = new FileInputStream(file);
                byte[] buffer = new byte[fis.available()];
                int i = fis.read(buffer);
                while(i != -1){
                    toClient.write(buffer);
                    i = fis.read(buffer);
                    toClient.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    if(toClient != null) {
                        toClient.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping("/getData")
    public Response getData() {
        List<Map<String, String>> list = new ArrayList<>();
        for(int i=0; i<10; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("fileName", i+"");
            list.add(map);
        }
        return new Response().success(list);
    }

}
