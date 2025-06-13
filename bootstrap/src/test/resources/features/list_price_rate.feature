Feature: Lists price rate for specific product

  Scenario: List price rate for an existing product
    When making a request to get a product's price rate with
    | productId | brandId | applicationDate |
    | 1234     | 1       | 2025-06-12      |
    Then the response status is 200
    And the response contains the 'productId'
    And the response contains the 'brand'
    And the response contains the 'rateToApply'
    And the response contains the 'applicationStartDate'
    And the response contains the 'applicationEndDate'
    And the response contains the 'finalPriceToApply'

  Scenario: List price rate for an non existing product
    When making a request to get a product's price rate with
      | productId | brandId | applicationDate |
      | 00000     | 1       | 2025-06-12      |
    Then the response status is 404

  Scenario: List price rate with wrong parameters
    When making a request to get a product's price rate with
      | productId    | brandId | applicationDate |
      | bad-parametr | 1       | 2025-06-12      |
    Then the response status is 400