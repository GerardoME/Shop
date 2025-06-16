package com.shop.products.steps;

import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceRateSteps {

    public static final String PRODUCT_ID = "productId";
    public static final String BRAND_ID = "brandId";
    public static final String APPLICATION_DATE = "applicationDate";

    public static final String BRAND = "brand";
    public static final String RATE = "rateToApply";
    public static final String START_DATE = "applicationStartDate";
    public static final String END_DATE = "applicationEndDate";
    public static final String PRICE = "finalPriceToApply";
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ResponseEntity<String> latestResponse;

    @ParameterType("(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(?:\\.\\d+)?(?:Z|[+-]\\d{2}:\\d{2}))")
    public OffsetDateTime offsetDateTime(String dateString) {
        try {
            return OffsetDateTime.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Invalid RFC 3339 date-time format in Gherkin: '" + dateString + "'. " +
                            "Expected 'yyyy-MM-ddTHH:mm:ssZ' or 'yyyy-MM-ddTHH:mm:ss[+|-]HH:MM'", e);
        }
    }

    @When("making a request to get a product's price rate with productId {int} and brandId {int} and application date {string}")
    public void requestAPI(Integer productId, Integer brandId, String applicationDate) {

        assertNotNull(productId);
        assertNotNull(brandId);
        assertNotNull(applicationDate);
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(applicationDate);
        URI uri = UriComponentsBuilder.fromPath("/products")
                .queryParam(PRODUCT_ID, productId)
                .queryParam(BRAND_ID, brandId)
                .queryParam(APPLICATION_DATE, offsetDateTime)
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

    @And("the response contains the product {int}, brand {string}, rate {string}, start date {string}, final date {string} and price {float}")
    public void validateResponseFields(Integer productId, String brand, String rate, String startDate, String endDate, Float price) throws IOException {
        assertNotNull(productId);
        assertNotNull(brand);
        assertNotNull(rate);
        assertNotNull(startDate);
        assertNotNull(endDate);
        assertNotNull(price);
        JsonNode body = readBody();
        assertEquals(productId, Integer.parseInt(body.get(PRODUCT_ID).asText()));
        assertEquals(brand, body.get(BRAND).asText());
        assertEquals(rate, body.get(RATE).asText());
        assertEquals(startDate, body.get(START_DATE).asText());
        assertEquals(endDate, body.get(END_DATE).asText());
        assertEquals(price, Float.parseFloat(body.get(PRICE).asText()));
    }

    @And("the response contains the {word}")
    public void responseContainsField(String field) throws IOException {
        TreeNode body = readBody();
        assertNotNull(body.get(field));
    }

    private JsonNode readBody() throws IOException {
        assertNotNull(latestResponse.getBody());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(latestResponse.getBody());
    }
}