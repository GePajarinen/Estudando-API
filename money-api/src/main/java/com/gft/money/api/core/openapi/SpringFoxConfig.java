package com.gft.money.api.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig implements WebMvcConfigurer{
	
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.gft.money.api"))
	//				.paths(PathSelectors.any())   //Igual ao de cima. Referência de outra opção.
	//				.paths(PathSelectors.ant("/pessoas/*")) //Outra opção pra selecionar o que dispor na Documentação (bem legal)
					.build()
				.apiInfo(apiInfo());
	}

	
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Money Controlador API")
				.description("Estudando API, OpenApi e Documentação")
				.version("1.0")
				.contact(new Contact("Gesiane","https://github.com/GePajarinen","gesaine.pajarinen@gft.com"))
				.build();
	}
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/");
		
		registry.addResourceHandler("/webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/");
		
	}
}
