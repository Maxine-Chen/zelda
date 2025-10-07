package com.pmx.zelda_java.config;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

public class AutoCode {
    public static void main(String[] args) {
        String url="jdbc:mysql://localhost:3306/zelda?serverTimezone=Asia/Shanghai&useSSL=false&characterEncoding=utf8";
        // 项目基础路径（当前项目的根目录）
        String projectPath = System.getProperty("user.dir");
        // Java 代码输出目录（src/main/java）
        String javaOutputDir = projectPath + "/src/main/java";
        // Mapper XML 输出目录（src/main/resources/mapper）
        String xmlOutputDir = projectPath + "/src/main/resources/mapper/";
        String mouduleName = "appinfo";


        FastAutoGenerator.create(url, "root", "123456")
                .globalConfig(builder -> {
                    builder.author("pmx") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .outputDir(javaOutputDir); // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder ->
                        builder.parent("com.pmx.zelda_java") // 设置父包名
                                .moduleName(mouduleName) // 设置父包模块名
                                .pathInfo(Collections.singletonMap(OutputFile.xml, xmlOutputDir+mouduleName)) // 设置mapperXml生成路径
                )
                .strategyConfig(builder ->
                                builder.addInclude("content") // 设置需要生成的表名
//                                .addTablePrefix("t_", "c_") // 设置过滤表前缀
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
