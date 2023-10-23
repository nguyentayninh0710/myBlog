package com.myclass.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title= "Spring boot  app REST Api",
				description = "Spring boot Blog App REST Api Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "NguyenHuynh",
						email = "huynhkykhoinguyen0710@gmail.com",
						url = "https://github.com/nguyentayninh0710"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.javaguides.net/2018/06/apache-maven-assembly-plugin.html"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring boot Blog App REST Api Documentation",
				url = "https://github.com/nguyentayninh0710/myBlog"
		)
)
public class BlogApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);

	}

}
