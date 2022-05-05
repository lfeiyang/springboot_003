package com.sy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 创建Swagger配置依赖
 *
 * @author lfeiyang
 * @since 2022-05-06 0:13
 */
@Configuration
public class Knife4jConfig {
    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        //.title("swagger-bootstrap-ui-demo RESTful APIs")
                        .description("# swagger-bootstrap-ui-demo RESTful APIs")
                        .version("1.0")
                        .build())

                //分组名称
                .groupName("2.X版本")
                .select()

                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.sy.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
