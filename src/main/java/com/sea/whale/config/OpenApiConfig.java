package com.sea.whale.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class OpenApiConfig {

    @Bean
    @Primary
    public OpenAPI whaleOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Whale Platform API")
                        .description("综合管理平台接口文档")
                        .version("1.0.0")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://sea.com")));
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("用户管理")
                .pathsToMatch("/api/users/**")
                .build();
    }

    @Bean
    public GroupedOpenApi orderApi() {
        return GroupedOpenApi.builder()
                .group("订单管理")
                .pathsToMatch("/api/orders/**")
                .build();
    }
}
