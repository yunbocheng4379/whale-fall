package com.sea.whale.utils;/**
 * @author : YunboCheng
 * @date : 19:41 2022-08-24
 */

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @description: MyBatis-plus代码自动生成工具
 * @author: chengyunbo
 * @time: 19:41
 */
public class MybatisPlusGeneratorUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/whale_sea?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    private static final String USERNAME = "root";

    private static final String PASSWORD = "567cybtfboys";

    private static final String AUTHOR = "Whale";

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String OUTPUT_DIR = "D:\\JavaProject\\ whale_fall\\src\\main\\java";

    private static final String MAPPER_XML_DIR = "D:\\JavaProject\\ whale_fall\\src\\main\\resources\\mybatis\\mapper";

    private static final String PARENT = "com.sea.whale";

    private static final String ENTITY_ALIAS = "entity";

    public static void main(String[] args) {
        // 设置数据库地址
        FastAutoGenerator.create(URL, USERNAME, PASSWORD)
                // 全局配置
                .globalConfig(
                        // 设置作者
                        builder -> builder.author(AUTHOR)
                                // 设置日期
                                .commentDate(DATE_FORMAT)
                                // 开启Swagger模式
                                .enableSwagger()
                                // 覆盖已生成文件
                                .fileOverride()
                                // 禁止打开目录
                                .disableOpenDir()
                                // 指定输出目录
                                .outputDir(OUTPUT_DIR)
                )
                // 包路径设置
                .packageConfig(
                        // 设置父包名
                        builder -> builder.parent(PARENT)
                                // 指定实体类的包
                                .entity(ENTITY_ALIAS)
                                // 设置生成 mapper.xml 路径
                                .pathInfo(Collections.singletonMap(OutputFile.xml, MAPPER_XML_DIR))
                )
                // 策略设置
                .strategyConfig(
                        builder -> builder.build().entityBuilder().enableLombok().enableTableFieldAnnotation()
                                .build().controllerBuilder().enableRestStyle()
                                .build().mapperBuilder().enableMapperAnnotation()
                )
                // 使用Freemarker引擎模板，默认是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

}
