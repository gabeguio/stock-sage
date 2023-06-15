import StockSageClient from '../api/stockSageClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view playlist page of the website.
 */
class CreateQuery extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'createQuery', 'createStocksTable'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        console.log("createQuery constructor");
    }

    mount() {
        document.getElementById('createQuery').addEventListener('click', this.createQuery);

        this.header.addHeaderToPage();
        this.client = new StockSageClient();
    }

    createStocksTable(stocks) {
        if (stocks === 0) {
            return 
        }
        
        let html = `
        <form>
            <table>
                <tr>
                    <th>Date</th>
                    <th>Open</th>
                    <th>High</th>
                    <th>Low</th>
                    <th>Close</th>
                    <th>Volume</th>
                </tr>`;

        for (const stock of stocks) {
            html += `
                <tr>
                    <td>${stock.date}</td>
                    <td>${stock.open}</td>
                    <td>${stock.high}</td>
                    <td>${stock.low}</td>
                    <td>${stock.close}</td>
                    <td>${stock.volume}</td>
                </tr>`
        }
        return html;
    }


    async createQuery(event) {
        //Prevent page refresh
        event.preventDefault();

        //button set to 'Loading...'
        const button = document.getElementById('createQuery');
        const origButtonText = button.innerText;
        button.innerText = 'Loading...';

        //get partition, sort key, and data fields
        const username = (await this.client.authenticator.getCurrentUserInfo()).email
        const queryId = document.getElementById('queryIdInput').value;
        const dateRequested = document.getElementById('dateRequestedInput').value;
        const startDate = document.getElementById('startDateInput').value;
        const endDate = document.getElementById('endDateInput').value;
        const frequency = document.getElementById('frequencyInput').value;
        const symbol = document.getElementById('symbolInput').value;
        const saved = document.getElementById('savedInput').value;
        console.log("Data from HTML retrieved.")

        //api request
        const stockList = await this.client.createQuery(username, queryId, dateRequested, startDate, endDate, frequency, symbol, saved);
        console.log("createQuery API called and stockList returned.")

        //store response
        // this.dataStore.set('stockList', stockList);
        // console.log("stock list stored in datastore.")

        //button done loading
        button.innerHTML = "Load Stocks"

        //post get query response to page
        // const stockListResult = this.dataStore.get('stockList');
        console.log("Before the table is made the data is: "+ stockList);
        document.getElementById('viewStocksTable').innerHTML = this.createStocksTable(stockList);
    }
}

const main = async () => {
    const createQuery = new CreateQuery();
    createQuery.mount();
};

window.addEventListener('DOMContentLoaded', main);
