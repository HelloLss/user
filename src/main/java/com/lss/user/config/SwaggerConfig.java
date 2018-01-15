package com.lss.user.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.util.StopWatch;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author liushuaishuai
 * @date 2018/1/8 下午2:23
 *
 * Swagger Configuration
 */
@Configuration
@EnableSwagger2
@Profile(Constants.SPRING_PROFILE_SWAGGER)
public class SwaggerConfig {

//    private final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);
//
    @Value("${swagger.project.title}")
    private String title;
    @Value("${swagger.project.description}")
    private String description;
    @Value("${swagger.project.version}")
    private String version;
//
//    @Bean
//    public Docket createRestApi(){
//        logger.info("Start swagger");
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//
//        ApiInfo apiInfo = new ApiInfoBuilder()
//                .title(title)
//                .description(description)
//                .termsOfServiceUrl("")
//                .version(version)
//                .build();
//
//        Docket docket = new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.lss.user.web.rest"))
//                .paths(PathSelectors.any())
//                .build();
//
//        stopWatch.stop();
//        logger.info("Start Swagger in {} ms",stopWatch.getLastTaskTimeMillis());
//
//        return docket;
//    }


    @Autowired
    private Environment environment;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(environment.getProperty("swagger.enable", Boolean.class))
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lss.user.web.rest"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl("")
                .version(version)
                .build();
    }

}
