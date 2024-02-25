# Stock Sage

## Overview

Stock Sage is a dashboard for investors to access historical pricing data using AlphaAdvantage’s public API.
Queries may be saved to a list for referencing in the future. For each query, an investor will add a name for the query, and an opportunity to add notes for documenting their thoughts about a data response. These notes will be accessible from a list of queries from the API.
Investors will also be able to review their recent queries if they feel like going back to review and save a query.

## Technologies

### Frontend

JavaScript, HTML, CSS, Webpack, AXIOS

### Backend

Java, AWS Lambda, DynamoDB

### AlphaVantage API

[AlphaVantage About Description](https://www.alphavantage.co/#about)
"Alpha Vantage provides realtime and historical financial market data through a set of powerful and developer-friendly data APIs and spreadsheets."

## API Data Modeling

#### Query Model (DynamoDB)

    - String username; (Key)
    - String queryId; (Sort) (GSI, SavedIndex - Key)
    - String dateRequested;
    - String fromDate;
    - String toDate;
    - String frequency;
    - String symbol;
    - String noteId;
    - String saved; (GSI, SavedIndex - Sort)

#### Stock Model (AlphaVantageAPI)

AlphaVantages Free API provides the following key stock metrics:

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
_Image 3: Review recent requests sorted by timestamp._

![ReviewingSavedQueries](resources/readme-images/stock-sage-demo-picture-3.png)
_Image 3: Saved requests page to take notes._

![UpdateASavedQuery](resources/readme-images/stock-sage-demo-picture-4.png)
_Image 4: Edit title and description for Apple's stock data._
