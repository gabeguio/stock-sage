# Stock Sage

## Overview

Stock Sage is a dashboard for investors to analyze historical pricing data from a variety of asset classes (e.g., stocks, ETFs, mutual funds) using AlphaAdvantage’s public API. 
Stock Sage allows investors to store and save recent queries. For each query, an investor may add a title and description to document their thoughts.

## Technologies

### Frontend

JavaScript, HTML, CSS, Webpack, AXIOS

### Backend

Java, AWS Lambda, DynamoDB

### AlphaVantage API
[AlphaVantage](https://www.alphavantage.co/#about) provides real-time and historical financial market data through a set of powerful and developer-friendly data APIs and spreadsheets.

## Data Modeling

#### Query Model (DynamoDB)

    - String username; (Primary Key)
    - String queryId; (Sort Key)
    - String dateRequested;
    - String startDate;
    - String endDate;
    - String frequency;
    - String symbol;
    - String saved; 
    - String title;
    - String content;

#### Stock Model (AlphaVantageAPI)

AlphaVantages Free API provides the following key stock metrics:

- Date
- Open
- Highs
- Lows
- Close
- Volume

## Sequence Diagram

![CreateQuerySequenceDiagram](resources/readme-images/create-query-sequence-diagram.png)
_Image 1: Sequence Diagram for Stock Sage's Primary Use Case: **Create Query**_

## Website Pictures

These pictures come from a [video demonstration](https://www.youtube.com/watch?v=rZTtFd0rAr0) of the Stock Sage's web application.

![AnalyzingAppleStock](resources/readme-images/stock-sage-demo-picture-1.png)
_Image 2: Generating a Query to analyze Apple's stock price from 1998 to 2005._

![ReviewingRecentQueries](resources/readme-images/stock-sage-demo-picture-2.png)
_Image 3: Review and save recent requests sorted by timestamp._

![ReviewingSavedQueries](resources/readme-images/stock-sage-demo-picture-3.png)
_Image 3: Review saved requests to take notes._

![UpdateASavedQuery](resources/readme-images/stock-sage-demo-picture-4.png)
_Image 4: Editing title and description for Apple's stock data query._
