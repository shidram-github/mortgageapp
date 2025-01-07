Here is a `README.md` file with the application details:

```markdown
# Mortgage Application

This is a Spring Boot application for checking the feasibility of a mortgage based on user inputs such as income, loan value, home value, and maturity period.

## Features

- Fetch predefined mortgage rates
- Check mortgage feasibility
- Calculate monthly mortgage costs

## Technologies Used

- Java
- Spring Boot
- Maven

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.8.1 or higher

### Installation

1. Clone the repository (Need to push the code to the repository: TODO):
    ```sh
    git clone https://github.com/todo-repo/mortgageapp.git
    ```
2. Navigate to the project directory:
    ```sh
    cd mortgageapp
    ```
3. Build the project using Maven:
    ```sh
    mvn clean install
    ```

### Running the Application

1. Run the application:
    ```sh
    mvn spring-boot:run
    ```
2. The application will start on port 8085 by default. You can access it at `http://localhost:8085`.

### Running Tests

To run the tests, use the following command:
```sh
mvn test
```

## API Endpoints

### Get Interest Rates

- **URL:** `/api/interest-rates`
- **Method:** `GET`
- **Response:**
    ```json
    [
      {
        "maturityPeriod": 10,
        "interestRate": 2.5,
        "lastUpdate": "2024-12-31T14:21:26.9949595"
      },
      {
        "maturityPeriod": 15,
        "interestRate": 3.0,
        "lastUpdate": "2024-12-31T14:21:26.9949595"
      }
    ]
    ```

### Check Mortgage Feasibility

- **URL:** `/api/mortgage-check`
- **Method:** `POST`
- **Request Body:**
    ```json
    {
        "income": 50000,
        "maturityPeriod": 30,
        "loanValue": 200000,
        "homeValue": 250000
    }
    ```
- **Response:**
    ```json
    {
        "feasible": true,
        "monthlyCost": "1234.56"
    }
    ```

## License

This project is licensed under the MIT License.
```

Feel free to customize the content as needed.