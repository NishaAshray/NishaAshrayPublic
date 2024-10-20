# Flipkart Sorting and Cart Functionality Test

This project is an automated test suite for verifying the sorting and add-to-cart functionalities on the Flipkart e-commerce website. 
The tests are implemented using Selenium WebDriver with TestNG and Jackson for JSON data handling.

## Features

- **Search Products**: Search for products on Flipkart.
- **Sort Products**: Validate sorting functionality by price.
- **Add to Cart**: Test adding products to the cart and validate cart totals.

## Prerequisites

Before running the tests, ensure you have the following installed:

- Java Development Kit (JDK) 8 or higher
- Apache Maven
- Google Chrome browser
- [ChromeDriver](https://chromedriver.chromium.org/downloads) (automatically managed by WebDriverManager)

**json**
Copy code
{
    "searchTerm": "laptop",
    "sortOption": "Price -- Low to High",
    "pageLimit": 3
}

**Test Cases**
1. validateSortFunctionality
Purpose: Validate the sorting functionality on Flipkart by ensuring that the products are sorted in ascending order by price.
Actions:
Search for a specified term.
Click on the sorting option.
Collect prices from multiple pages and verify the sorted order.
2. validateAddToCartFunctionality
Purpose: Validate the add-to-cart functionality and ensure the cart total is calculated correctly.
Actions:
Search for a specified term.
Click on the sorting option.
Add two products to the cart.
Retrieve the prices from the cart and validate the total.
