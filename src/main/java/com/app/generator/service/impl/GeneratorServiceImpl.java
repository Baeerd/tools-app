package com.app.generator.service.impl;

import com.app.common.service.impl.BaseServiceImpl;
import com.app.generator.build.BuildJava;
import com.app.generator.build.controller.BuildController;
import com.app.generator.build.entity.BuildEntity;
import com.app.generator.build.mapper.BuildMapperJava;
import com.app.generator.build.mapper.BuildMapperXml;
import com.app.generator.build.service.BuildService;
import com.app.generator.build.service.BuildServiceImpl;
import com.app.generator.entity.Generator;
import com.app.generator.entity.TableDetail;
import com.app.generator.service.GeneratorService;
import com.app.generator.util.JdbcUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class GeneratorServiceImpl extends BaseServiceImpl<Generator> implements GeneratorService {

    @Override
    public void generatorCode(HttpServletResponse response, String filePath, Map<String, String> params) {

        // 生成代码文件
        createFiles(filePath, params);
        // 压缩生成的文件并响应下载
        compressAllZip(filePath+"/result", params.get("tableName") +".zip", response);
        // 删除生成的文件
        deleteFiles(new File(filePath+"/result"));
    }

    private void createFiles(String filePath, Map<String, String> params) {
        String tableName = params.get("tableName");
        // 实体
        BuildJava buildEntity = new BuildEntity(filePath, params);
        buildEntity.generate();

        // mapper.xml
        buildEntity = new BuildMapperXml(filePath, params);
        buildEntity.generate();

        // mapper.java
        buildEntity = new BuildMapperJava(filePath, params);
        buildEntity.generate();

        // service
        buildEntity = new BuildService(filePath, params);
        buildEntity.generate();

        // serviceImpl
        buildEntity = new BuildServiceImpl(filePath, params);
        buildEntity.generate();

        // Controller
        buildEntity = new BuildController(filePath, params);
        buildEntity.generate();
    }

    // 压缩文件或者目录，其中：dirPath：压缩的根目录
    // dirPath目录下的所有文件包名子目录，targetName：压缩后的zip文件
    /**
     *
     * @param dirPath 压缩的根目录
     * @param targetName
     * @param response
     */
    public void compressAllZip(String dirPath, String targetName, HttpServletResponse response) {
        File baseDir = new File(dirPath);						// 判断dirPath是不是目录
        if (!baseDir.exists() || (!baseDir.isDirectory())) {
            System.out.println("压缩失败" + dirPath + "目录不存在");
            return;
        }
        String basicRootDir = baseDir.getAbsolutePath();
        File targetFile = new File(targetName);	// 创建zip文件
        try {
            // 创建一个zip输出流来压缩数据并写入到zip文件
            ZipOutputStream out = new ZipOutputStream(response.getOutputStream(), Charset.forName("GBK"));
            // 将baseDir目录下的所有文件压缩到ZIP
            compressDirToZip(basicRootDir, baseDir, out);
            out.close();
            System.out.println("文件压缩成功，压缩包的文件名为：" + targetName);
        } catch (IOException e) {
            System.out.println("压缩失败：" + e);
            e.printStackTrace();
        }
    }
    // 利用ZipOutputStream对目录的压缩
    private void compressDirToZip(String basicRootDir, File dir,ZipOutputStream out) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();// 列出dir目录下所有文件
            if (files.length == 0) {// 如果是空文件夹
                ZipEntry entry = new ZipEntry(getFileName(basicRootDir, dir));// 存储目录信息
                try {
                    out.putNextEntry(entry);
                    out.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    // 如果是文件，调用compressFileToZip方法
                    compressFileToZip(basicRootDir, files[i], out);
                } else {
                    // 如果是目录，递归调用
                    compressDirToZip(basicRootDir, files[i], out);
                }
            }
        }
    }
    // 利用ZipOutputStream对文件的压缩
    private void compressFileToZip(String basicRootDir, File file, ZipOutputStream out) {
        FileInputStream in = null;
        ZipEntry entry = null;
        int bytes_read;
        if (file.isFile()) {
            try {
                in = new FileInputStream(file);// 创建一个文件输入流
                byte[] buffer = new byte[in.available()];
                // 根据压缩文件的名字构造一个ZipEntry对象，此类表示zip包中的文件条目
                entry = new ZipEntry(getFileName(basicRootDir, file));
                out.putNextEntry(entry); 						// 存储项信息到压缩文件
                // 将文件的内容通过字节数组复制到压缩文件中
                while ((bytes_read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytes_read);
                }
                out.closeEntry();
                in.close();
                System.out.println("添加文件" + file.getAbsolutePath() + "到ZIP文件中！");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private String getFileName(String basicRootDir, File file) {	// 获取等待压缩的文件的文件名
        if (!basicRootDir.endsWith(File.separator)) {
            basicRootDir += File.separator;
        }
        String filePath = file.getAbsolutePath();

        if (file.isDirectory()) {			// 对于目录，必须在entry名字后面加上"/"，表示它将以目录项存储
            filePath += "/";
        }
        int index = filePath.indexOf(basicRootDir);
        return filePath.substring(index + basicRootDir.length());
    }

    @Override
    public List<TableDetail> getTableDetail(String tableName) {
        List<TableDetail> list = new ArrayList<>();
        List<Map<String, String>> maps = JdbcUtil.getTableInfo(tableName.toUpperCase());
        if(CollectionUtils.isNotEmpty(maps)) {
            for (Map<String, String> map : maps) {
                TableDetail tableDetail = new TableDetail();
                tableDetail.setColName(map.get("colName"));
                tableDetail.setRemark(map.get("remarks"));
                tableDetail.setDbType(map.get("dbType"));
                list.add(tableDetail);
            }
        }
        return list;
    }

    /**
     * 删除目录中的文件
     * @param file
     */
    private void deleteFiles(File file){
        if (file.isDirectory()) {
            File[] files=file.listFiles();
            if(files != null && files.length > 0) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        deleteFiles(f);
                    } else {
                        f.delete();
                    }
                }
            }
        }
        file.delete();
    }
}
