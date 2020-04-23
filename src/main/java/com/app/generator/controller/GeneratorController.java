package com.app.generator.controller;

import com.app.common.controller.BaseController;
import com.app.common.entity.AppConfig;
import com.app.common.entity.Response;
import com.app.common.exception.MessageException;
import com.app.generator.entity.Generator;
import com.app.generator.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.net.URLEncoder.encode;

/**
 * 注册登陆相关操作Controller
 */
@RequestMapping("/generator")
@RestController
public class GeneratorController extends BaseController<Generator>{

    @Autowired
    private AppConfig appConfig;
    @Autowired
    private GeneratorService generatorService;

    @RequestMapping("/getConfigData")
    @ResponseBody
    public Response getConfigData(HttpServletRequest request) {
        List<Map<String, String>> list = new ArrayList<>();
        String realPath=request.getServletContext().getRealPath("/templates");
        File file = new File(realPath);
        File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中
        for(File f:fs){
            if(!f.isDirectory()) {
                Map<String, String> map = new HashMap<>();
                map.put("fileName", f.getName());
                list.add(map);
            }
        }
        return new Response().success(list);
    }

    /**
     * 实现文件上传
     * */
    @RequestMapping("/fileUpload")
    @ResponseBody
    public Response fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        if(file.isEmpty()){
            return new Response().failure();
        }
        String realPath=request.getServletContext().getRealPath("/templates");
        String fileName = file.getOriginalFilename();
        if(!fileName.endsWith(".ftl")) {
            throw new MessageException("请上传正确模板（.ftl文件）");
        }
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);

        File dest = new File(realPath + "/" + fileName);
        if(!dest.getParentFile().exists()){
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

    @RequestMapping("/downLoadTemplate/{fileName:.+}")
    public void downLoad(HttpServletResponse response, HttpServletRequest request, @PathVariable("fileName") String fileName) throws UnsupportedEncodingException {
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

    @RequestMapping("/getTables")
    public Response getTables() {
        List<Generator> tables = generatorService.findAll();
        return new Response().success(tables);
    }
}
