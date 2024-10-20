# NishaAshrayPublic

This project is a Test Automation Suite for the Flipkart website using Selenium WebDriver and TestNG. The suite includes tests for validating sorting functionality and add-to-cart functionality.

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Maven 3.6.0 or higher
- ChromeDriver (compatible with your Chrome browser version)
- IntelliJ IDEA (recommended IDE)

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/nisha-kumari_clv/NishaAshrayPublic.git
    ```
2. Navigate to the project directory:
    ```sh
    cd NishaAshrayPublic
    ```
3. Install the dependencies:
    ```sh
    mvn clean install
    ```

## Project Structure

```plaintext
NishaAshrayPublic/
├── src/
│   ├── main/
│   └── test/
│       ├── java/
│       │   ├── Pages/
│       │   │   ├── FlipkartHomePage.java
│       │   │   ├── ProductPage.java
│       │   ├── testcases/
│       │   │   ├── FlipkartTests.java
│       │   ├── utilities/
│       │       ├── BaseTest.java
│       │       ├── Utilities.java
│       └── resources/
│           ├── config.properties
│           ├── temp-testng-customsuite.xml
├── pom.xml
└── README.md
```

## Running Tests

1. Open the project in IntelliJ IDEA.
2. Ensure that the TestNG plugin is installed and enabled.
3. Run the tests using Maven:
    ```sh
    mvn test
    ```
4. Alternatively, you can run the tests directly from IntelliJ IDEA by right-clicking on the `temp-testng-customsuite.xml` file and selecting "Run".

## Configuration

The `config.properties` file contains configuration settings for the tests. Update the following properties as needed:

```properties
searchTerm=your_search_term
pageLimit=number_of_pages_to_test
webdriver.chrome.driver=path_to_chromedriver
```

## Utilities

- `BaseTest.java`: Contains setup and teardown methods for initializing and quitting the WebDriver instance.
- `Utilities.java`: Contains utility methods for common actions like waiting for elements to load, switching windows, etc.

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Open a pull request.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.
