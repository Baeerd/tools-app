package com.app.generator.build;

public interface BuildJava {

    // 加载配置文件，转换成map
    void init();
    // 创建数据模型
    void generateDataList();
    // 生成文件
    void buildFile();
    // 生成
    void generate();
}
