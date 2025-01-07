# mortgageapp API Documentation

## Introduction
### Base URL

- LOCAL: http://localhost:8085


## getInterestRates

`GET` /api/interest-rates

### Description

None

### Request Parameters

None

### Response

- Response Parameters. JSON array format description:

| Field | Type | Required | Description |
| --- | --- | --- | --- |
| maturityPeriod | integer | Yes |  |
| interestRate | number | Yes |  |
| lastUpdate | string | Yes |  |


## checkMortgage

`POST` /api/mortgage-check

### Description

None

### Request Parameters

- Body Parameters. JSON object format description:

| Field | Type | Required | Description |
| --- | --- | --- | --- |
| income | number | Yes |  |
| maturityPeriod | integer | Yes |  |
| loanValue | number | Yes |  |
| homeValue | number | Yes |  |

### Response

- Response Parameters. JSON object format description:

| Field | Type | Required | Description |
| --- | --- | --- | --- |
| feasible | boolean | Yes |  |
| monthlyCost | string | Yes |  |


