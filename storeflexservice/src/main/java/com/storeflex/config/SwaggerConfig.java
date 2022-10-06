package com.storeflex.config;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.service.Parameter;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import lombok.val;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {
	private static final Logger log = LoggerFactory.getLogger(SwaggerConfig.class);
	
	// pass the global parameter list here - this will get added in all apis (in swagger)
	  // automatically
	/*
	 * private List<Parameter> globalParameterList() {
	 * 
	 * val contentType = new ParameterBuilder() .name("Content-Type") .modelRef(new
	 * ModelRef("string")) .required(true) .parameterType("header")
	 * .description("Media-Type") .build();
	 * 
	 * val apiVersion = new ParameterBuilder() .name("X-API-VERSION") .modelRef(new
	 * ModelRef("string")) .required(true) .parameterType("header")
	 * .description("X-API-VERSION") .build();
	 * 
	 * return Arrays.asList(contentType,apiVersion); }
	 */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry
                .addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket apiDocket() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                //.globalOperationParameters(globalParameterList())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.storeflex.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiInfo() {

        return new ApiInfoBuilder()
                .title("Swagger API Doc For StoreFlex Service")
                .description("More description about the API")
                .version("1.0.0")
                .build();
    }
}