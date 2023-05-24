# StockSage-Design-Document-v4

Stock Market Data Aggregator - StockSage

## 1. Problem Statement

StockSage is a tool for investors to analyze historical pricing data by weekly and monthly increments. An investor may query data for the following metrics:

- Given a range of data, return either weekly or monthly information for a stock.
    - Open
    - Highs
    - Lows
    - Close
    - Volume

My application will be a dashboard for investors to access historical pricing data using AlphaAdvantage’s public API. Queries may be saved to a list for referencing in the future. For each query, an investor will add a name for the query, and an opportunity to add notes for documenting their thoughts about a data response. These notes will be accessible from a list of queries from the API. Investors will also be able to review their recent queries if they feel like going back to review and save a query.

## 2. Top Questions to Resolve in Review

- How will I parse the apiResponse to contain the ranges for the mainResponse?
- What will be the endpoint for GetQuery, since I’ll be creating and returning a query.
- How will I run a routine clean up for all the old requests from users in my system?
    - `DeleteOldQueriesFromRecentQueries`
- How will a note be referenced from a row in the query table?
    - `GetNoteFromSavedQuery`
    - A. I can have a GSI on the requests tables to access the note from a query.

## 3. Use Cases

1. As a customer, I want to view data on a specific stocks monthly and weekly pricing data with a provided time range.
2. As a customer, I want to be able to save a specific query with a name and note.
3. As a customer, I want to be able to view a list of the 10 most recent requests.
4. As a customer, I want to be able to save a specific request from the list of recent requests.
5. As a customer, I want to view a list of my saved requests.
6. As a customer, I want to be able to delete a request from my list of saved requests.
7. As a customer, I want to be able to update a request from my list of saved requests by adjusting the name or note.
8. As a customer, I want to be able to save and load queries from the recent queries list.
9. As a customer, I want to be able to view the note of a specific request.

## 4. Project Scope

### 4.1. In Scope

- Creating, retrieving, updating and deleting a query from either a list of recent or saved queries.
- Managing a users saved list of queries, which can be n-long.
- Managing a users queries, which can only be 10 long.
- Adding the ability to personalize a query with a name and a note.
- Adding a calendar on the front-end for easy identification for date range.
- Storing my API key in a remote secrets folder using AWS manager.
- Learning about parsing JSON objects from an API.
- Learning about a Java framework that can make calls to a 3rd party API.
- Provided the last trading day opening price, monthly or weekly high, monthly or weekly close, lat trading day closing price, and the total volume for specific week or month.

### 4.2. Out of Scope

- Tracking and capturing daily, intraday, or real-time pricing data for a stock.
- Providing graphs or charts for a queries.
- Creating autocomplete functionality for a symbol in a request.
- Implementating multiple notes for a single query .
- Buying and selling stocks.
- The ability to search for existing songs either through the website or the API
- Storing a list of the user’s favorite stocks on a watchlist.

## 5. Proposed Architecture Overview

This initial iteration will provide the minimum lovable product (MLP) including
creating, retrieving, updating, and deleting query to the AlphaAdvantage API. Upon retrieving each query, the user will be able to retrieve a stored list of saved and recent queries.

We will use API Gateway and Lambda to create seven endpoints

1. `CreateQuery`
2. `GetRecentQueries`
3. `GetSavedQueries`
4. `AddQueryToSavedQueries`
5. `DeleteQueryFromSavedQueries`
6. `GetNoteFromSavedQuery`
7. `UpdateNoteByTitleAndContents`
8. `DeleteOldQueriesFromRecentQueries`

To view any request from the saved list, a link will be provided to check out historical data, nad make another call to the API.

The back-end will communicate with the AlphaAdvantage’s API, and return the correct information for the front-end to display. The front-end will send user information and queries to the back-end for updating the DynamoDB table.

The back-end will make a request to AlphaAdvantage’s API to retrieve all the historical stock data and return the specific data range sent from the front-end. This access object will contain an SDKHTTPCLIENT object to make the call, and attributes on the class will manage the raw API response, and the data response for the user.

My personal API key with AlphaAdvantage’s API will be kept secret using the AWS System Manager or AWS Secrets Manager?

I will be managing the data ranges based on ISO 8901 which will be embeded in the queryId.

## 6. API

### Public Models

```
// QueryModel

String username (Key)
String queryId; (Sort) (GSI, SavedIndex - Key)
String dateRequested;
String fromDate;
String toDate;
String frequency;
String symbol;
String noteId;
String saved; (GSI, SavedIndex - Sort)
```

```
// UserModel

String username; (Key)
```

```
// StockAPIModel

// SdkHttpClient sdkHttpClient;
// String apiResponse
// String stockSageResponse;
```

```
// NoteModel

String nodeId; (Key)
String title;
String dateCreated;
String contents;
```

### 1. Create Query Endpoint

- Accepts `POST` requests to either:
    - `/stockQueries?dateTo=&dateFrom=&frequency=&symbol=`
    - `/query/:userId/:queryId`
- Accepts a query parameters and returns the corresponding QueryModel.
    - If the given data range is invalid, will throw a
        - `InvalidDataRangeException` will branch off of `InvalidQueryException`
        - `InvalidSymbolException` will branch of of `InvalidQueryException`
    - If the API returns a bad response, due to pricing not avilable will throw
        - `PricingDataNotFoundException` will branch off `QueryUnavailableException`
        - If the data does not cover the entire range, return what’s available in the selected range.
    - If the API returns a response tell the user cannot make a request yet, then throw
        - `QueryUnavailableException`
        - 5 requests per minute and 500 requests per day.
        - I could short circuit this ont he front-end, and only make the user able to send requests no short than 15 seconds apart.

### 2. Get Recent Queries Endpoint

- Accepts `GET` requests to `/recentQueries/:userId`
- Returns a list of the last 10 queries based on the current time. There may be more than 10 queries in the database for a single user, but we only want the 10 most recent.
- If the user has not made any request, then throw a
    - `NoRecentQueriesException` will branch of of `QueriesNotFoundException` will branch off `QueryUnavailableException`

### 3. Get Saves Queries Endpoint

- Accepts `GET` requests to `/savedQueries/:userId`
- Returns a list of all the queries a user has saved.
- If the user has not saved any queries, then throw a
    - `NoSavedQueriesException` will branch of of `QueriesNotFoundException` will branch off `QueryUnavailableException`

### 4. Add Query to Saved Queries Endpoint

- Accepts `PUT` requests to `/addQuery/:userId/:queryId`
- Changes the attribute save from null to true
    - If the attribute for a GSI key is null, then the key will not be populated, saving space in dynamo db.
- If the queryId is not found, will throw
    - `QueryIdNotFoundException` will branch off `QueryUnavailableException`
- If the queryId is already on the saved list, will throw
    - `QueryAlreadyAddedByUserException` will branch off of `InvalidQuerySaveException`
- If the title for the note is required
    - `QueryAddedWithoutTitleException` will branch off of `InvalidQuerySaveException`

### 5. Remove Query from Saved Queries Endpoint

- Accepts `PUT` requests to `/removeQuery/:userId/:queryId`
- Changes the attribute save from true to false
- If the queryId is not found, will throw
    - `QueryIdNotFoundException` will branch off `QueryUnavailableException`
- If the queryId is not already on the users list, then throw
    - `QueryDoesNotExistException` will branch off `QueryUnavailableException`

### 6. Get Note By QueryId Endpoint

- Accepts GET requests to `/getNote/:noteId`
    - noteId lives on the query table
    - GSI for query as the key and note as the sort

### 6. Update Query from Saved Queries Endpoint

- Accepts `PUT` requests to `/updateNote/:nodeId`
- User is required to provide a name
    - `QueryUpdatedWithoutTitleException` will branch off of `InvalidQuerySaveException`
- If the queryId is not found, will throw
    - `QueryIdNotFoundException` will branch off `QueryUnavailableException`

### 7. Remove Old Queries from Recent Queries Endpoint

- This endpoint is not exposed to the user through the front-end client.
- Accepts `PUT` requests to `/updateRecentQueries/:userId`

## 7. Tables

### 7.1. `users`

```
String username; (Key)
```

### 7.2. `queries`

```
String username (Key)
String queryId; (Sort) (GSI, SavedIndex - Key) (GSI, NoteIndex - Key)
String dateRequested;
String fromDate;
String toDate;
String frequency;
String symbol;
String noteId; (GSI, NoteIndex - Sort)
String saved; (GSI, SavedIndex - Sort)
```

### 7.2.1 `notes`

```java
String nodeId; (Key)
String title;
String dateCreated;
String contents;
```