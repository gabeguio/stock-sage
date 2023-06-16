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
        document.getElementById('loadStocks').addEventListener('click', this.createQuery);

        this.header.addHeaderToPage();
        this.client = new StockSageClient();
    }

    // sortByDateAscending(stockList) {
    //     stockList.sort((stockA, stockB) => stockA.date.localeCompare(stockB.date));
    //     return stockList;
    // }

    // sortByDateDescending(stockList) {
    //     stockList.sort((stockA, stockB) => stockB.date.localeCompare(stockA.date));
    //     return stockList
    // }

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


    async createQuery(event) {
        //Prevent page refresh
        event.preventDefault();

        //button set to 'Loading...'
        const button = document.getElementById('loadStocks');
        const origButtonText = button.innerText;
        button.innerText = 'Loading...';

        //get partition, sort key, and data fields
        const username = (await this.client.authenticator.getCurrentUserInfo()).email
        const startDate = document.getElementById('startDateInput').value;
        const endDate = document.getElementById('endDateInput').value;
        const frequency = document.getElementById('frequencyInput').value;
        const symbol = document.getElementById('symbolInput').value;
    

        console.log("Data from HTML retrieved.")

        //api request
        const stockList = await this.client.createQuery(username, startDate, endDate, frequency, symbol);
        console.log("createQuery API called and stockList returned.")
        console.log("Unsorted: " + stockList);

        // var stockList = sortByDateDescending(stockList);
        // console.log("Sorted: " + stockList);

        //store response
        this.dataStore.set('stockList', stockList);
        console.log("stock list stored in datastore.")

        //button done loading
        button.innerHTML = "Load Stocks"

        console.log("Before the table is made the data is: "+ stockList);
        document.getElementById('viewStocksTable').innerHTML = this.createStocksTable(stockList);
    }
}

const main = async () => {
    const createQuery = new CreateQuery();
    createQuery.mount();
};

window.addEventListener('DOMContentLoaded', main);
