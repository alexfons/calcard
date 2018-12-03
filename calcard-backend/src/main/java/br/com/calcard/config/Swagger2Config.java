package br.com.calcard.config;

import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Predicate;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(customRequestHandlers())
				.paths(PathSelectors.any())
				.build();
	}

	private Predicate<RequestHandler> customRequestHandlers() {
		return (RequestHandler input) -> {
			Set<RequestMethod> methods = input.supportedMethods();
			return methods.contains(RequestMethod.GET) || methods.contains(RequestMethod.POST) || methods
					.contains(RequestMethod.PUT) || methods.contains(RequestMethod.DELETE);
		};
	}
}
