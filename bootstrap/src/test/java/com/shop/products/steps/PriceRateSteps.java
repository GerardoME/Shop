package com.shop.products.steps;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceRateSteps {

    public static final String PRODUCT_ID = "productId";
    public static final String BRAND_ID = "brandId";
    public static final String APPLICATION_DATE = "applicationDate";
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ResponseEntity<String> latestResponse;

    @When("making a request to get a product's price rate with")
    public void requestAPI(List<Map<String, String>> dataTable) {
        Map<String, String> requestParams = dataTable.get(0);

        String productId = requestParams.get(PRODUCT_ID);
        String brandId = requestParams.get(BRAND_ID);
        String applicationDate = requestParams.get(APPLICATION_DATE);
        assertNotNull(productId);
        assertNotNull(brandId);
        assertNotNull(applicationDate);
        URI uri = UriComponentsBuilder.fromPath("/products")
                .queryParam(PRODUCT_ID, productId)
                .queryParam(BRAND_ID, brandId)
                .queryParam(APPLICATION_DATE, applicationDate)
                .scheme("http")
                .host("localhost")
                .port(port)
                .encode()
                .build().toUri();

        latestResponse = restTemplate.getForEntity(uri, String.class);
    }

    @Then("the response status is {int}")
    public void responseStatusIs(int statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode), latestResponse.getStatusCode());
    }

    @And("the response contains the {string}")
    public void responseContainsField(String field) throws IOException {
        assertNotNull(latestResponse.getBody());
        ObjectMapper mapper = new ObjectMapper();
        JsonParser jsonParser = mapper.createParser(latestResponse.getBody()
                .getBytes());
        TreeNode treeNode = mapper.readTree(jsonParser);
        assertNotNull(treeNode.get(field));
    }
}