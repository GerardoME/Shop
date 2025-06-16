Feature: Lists price rate for specific product

  Scenario Outline: List price rate for an existing product
    When making a request to get a product's price rate with productId <productId> and brandId <brandId> and application date <applicationDate>
    Then the response status is 200
    And the response contains the product <productId>, brand <brand>, rate <rate>, start date <startDate>, final date <endDate> and price <finalPrice>
    Examples:
      | productId | brandId | applicationDate        | brand  | rate              | startDate              | endDate                | finalPrice |
      | 35455     | 1       | "2020-06-14T10:00:00Z" | "ZARA" | "GENERAL_TARIFF"  | "2020-06-14T00:00:00Z" | "2020-12-31T23:59:59Z" | 35.5       |
      | 35455     | 1       | "2020-06-14T16:00:00Z" | "ZARA" | "SUMMER_SALE"     | "2020-06-14T15:00:00Z" | "2020-06-14T18:30:00Z" | 25.45      |
      | 35455     | 1       | "2020-06-14T21:00:00Z" | "ZARA" | "GENERAL_TARIFF"  | "2020-06-14T00:00:00Z" | "2020-12-31T23:59:59Z" | 35.5       |
      | 35455     | 1       | "2020-06-15T10:00:00Z" | "ZARA" | "LAST_DAYS"       | "2020-06-15T00:00:00Z" | "2020-06-15T11:00:00Z" | 30.5       |
      | 35455     | 1       | "2020-06-16T21:00:00Z" | "ZARA" | "AUTUMN_CAMPAIGN" | "2020-06-15T16:00:00Z" | "2020-12-31T23:59:59Z" | 38.95      |

  Scenario: List price rate for an non existing product
    When making a request to get a product's price rate with
      | productId | brandId | applicationDate        |
      | 00000     | 1       | 2020-06-14T10:00:00Z |
    Then the response status is 404

  Scenario: List price rate with wrong parameters
    When making a request to get a product's price rate with
      | productId    | brandId | applicationDate        |
      | bad-parametr | 1       | 2020-06-14T10:00:00Z |
    Then the response status is 400