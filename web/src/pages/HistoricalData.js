import StockSageClient from '../api/stockSageClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view playlist page of the website.
 */
class HistoricalData extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'createQuery', 'createStocksTable'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        console.log("HistoricalData constructor");
    }

    mount() {
        document.getElementById('loadStocks').addEventListener('click', this.createQuery);

        this.header.addHeaderToPage();
        this.client = new StockSageClient();
    }

    async createQuery(event) {
        // prevent page refresh
        event.preventDefault();

        // button set to 'Loading...' when clicked
        const button = document.getElementById('loadStocks');
        button.innerText = 'Loading...';

        // get username and fields for createQuery request
        const username = (await this.client.authenticator.getCurrentUserInfo()).email
        const startDate = document.getElementById('startDateInput').value;
        const endDate = document.getElementById('endDateInput').value;
        const frequency = document.getElementById('frequencyInput').value;
        const symbol = document.getElementById('symbolInput').value;
    

        // submit fields to createQuery api
        const stockList = await this.client.createQuery(username, startDate, endDate, frequency, symbol);
        console.log("createQuery API called and stockList returned.")
        console.log("Unsorted: " + stockList);

        // store stockList in dataStore
        this.dataStore.set('stockList', stockList);
        console.log("stock list stored in datastore.")

        //button text reverted
        button.innerHTML = "Load Stocks"

        
        // render stocks table and add to page
        console.log("Before the table is made the data is: "+ stockList);
        document.getElementById('viewStocksTable').innerHTML = this.createStocksTable(stockList);
    }

    createStocksTable(stocks) {
        if (stocks === 0) {
            return `<p>No Stocks Found</p>`
        }
        
        let html = `
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

        html += `</table>`
        return html;
    }
}

const main = async () => {
    const historicalData = new HistoricalData();
    historicalData.mount();
};

window.addEventListener('DOMContentLoaded', main);
