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
        this.bindClassMethods(['mount', 'createQuery', 'createStocksTable', 'setStatusBar'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        console.log("HistoricalData constructor");
    }

    mount() {
        document.getElementById('loadStocks').addEventListener('click', this.createQuery);

        this.header.addHeaderToPage();
        this.client = new StockSageClient();
    }

    setStatusBar() {
        var path = window.location.pathname;

        var statusItems = document.getElementsByClassName('status-item');

        for (var i = 0; i < statusItems.length; i++) {
        var statusItem = statusItems[i];

        var href = statusItem.getAttribute('href');
            if (path.includes(href)) {
                statusItem.classList.add('active');
            }
        }
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

        // store stockList in dataStore
        this.dataStore.set('stockList', stockList);

        //button text reverted
        button.innerHTML = "Load Stocks"

        // render stocks table and add to page
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
    historicalData.setStatusBar();
};

window.addEventListener('DOMContentLoaded', main);
