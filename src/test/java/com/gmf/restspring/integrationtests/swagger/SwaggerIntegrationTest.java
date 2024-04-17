package com.gmf.restspring.integrationtests.swagger;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.gmf.restspring.integrationtests.testcontainers.AbstractIntegrationTest;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest{

	@LocalServerPort
	private String port;
	
	@BeforeEach
	void setUp() {
		RestAssured.port = Integer.parseInt(port);
	}
	
	@Test
	public void shouldDisplaySwaggerUiPage() {
		
		var content =
		given()
			.basePath("/swagger-ui/index.html")
			.when()
				.get()
			.then()
				.statusCode(200)
			.extract()
				.body()
					.asString();
		assertTrue(content.contains("Swagger UI"));
	}

}
