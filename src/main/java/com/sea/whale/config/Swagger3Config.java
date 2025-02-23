package com.sea.whale.config;/**
 * @author : YunboCheng
 * @date : 18:16 2022-08-28
 */

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class Swagger3Config {

    /**
     * 配置Swagger3。这些信息都会显示在这个Swagger3的前端页面上
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)  // 指定Swagger版本为3
                // 该Swagger3的信息，调用的是下边定义的信息方法
                .apiInfo(apiInfo())
                // 指定负责该 Swagger3 的小组。每个小组都需要有自己的  createRestApi() 以及 apiInfo()，所以说当有多个组是会有很多套
                .groupName("whale_fall")
                // 开发环境用到Swagger，正式环境一般进行关闭 true/false（开关）
                // .enable(false)
                // 接下来是过滤（指定哪些方法需要存在接口文档信息）：过滤之前必须给定 .select()
                .select()
                // 根据带有注解的方法过滤。存在@ApiOperation注解的方法保存接口描述信息。参数是一个类名，这里使用类自带的class属性获取类名
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 根据路径信息过滤。any()代表所有的路径都保存接口信息。还可以使用 PathSelectors.ant("路径信息") 指定特定的接口
                .paths(PathSelectors.any())
                // 过滤之后最后必须存在一个 build(),表示构建完成
                .build();
    }


    /**
     * 如果不配置会使用源码中默认的，一般我们都在这里进行覆盖重写
     * 配置该Swagger3的Api信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 该Swagger3的Api文档题目
                .title("鲸落")
                // 该Swagger3的Api文档描述
                .description("鲸落 - 接口管理")
                // 该Swagger3的Api文档联系方式（固定格式）
                .contact(new Contact("程云博", "", "https://github.com/yunbocheng"))
                // Swagger3的版本
                .version("1.0")
                // 最后需要使用build结尾，代表构建完成
                .build();
    }

}
