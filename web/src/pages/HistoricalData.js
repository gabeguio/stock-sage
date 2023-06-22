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
        this.bindClassMethods(['mount', 'retrieveQueryResultsAndAddToPage', 'updateStocksTable', 'setStatusBar', 'sortTable', 'parseNumber'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        console.log("HistoricalData constructor");
    }

    mount() {
        document.getElementById("form-submit-btn").addEventListener("click", this.retrieveQueryResultsAndAddToPage);

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

    async retrieveQueryResultsAndAddToPage(event) {
        const button = document.getElementById('form-submit-btn');
        button.innerText = 'Loading...';

        const username = (await this.client.authenticator.getCurrentUserInfo()).email
        var startDate = document.getElementById("start-date").value;
        var endDate = document.getElementById("end-date").value;
        var aggregation = document.getElementById("aggregation-period").value;
        var stockSymbol = document.getElementById("stock-symbol").value;

        const stockList = await this.client.createQuery(username, startDate, endDate, aggregation, stockSymbol);

        this.dataStore.set('stockList', stockList);

        button.innerHTML = "Apply";

        this.updateStocksTable(stockList);
    }

    async updateStocksTable(stocks) {
        // Get the table body element
        var tableBody = document.querySelector("#stock-table tbody");

        // Loop through the stocks and create table rows
        for (var i = 0; i < stocks.length; i++) {
        var stock = stocks[i];
        var newRow = tableBody.insertRow();

        // Create table cells for each property in the object
        var cell1 = newRow.insertCell();
        cell1.textContent = stock.date;
        var cell2 = newRow.insertCell();
        cell2.textContent = Number(stock.open).toFixed(2);
        var cell3 = newRow.insertCell();
        cell3.textContent = Number(stock.high).toFixed(2);
        var cell4 = newRow.insertCell();
        cell4.textContent = Number(stock.low).toFixed(2);
        var cell5 = newRow.insertCell();
        cell5.textContent = Number(stock.close).toFixed(2);
        var cell6 = newRow.insertCell();
        cell6.textContent = Number(stock.volume).toLocaleString();
        }
        
        const headers = document.querySelectorAll('th[data-sortable="true"]');
        headers.forEach(header => {
            header.addEventListener('click', () => {
                const columnIndex = Array.from(headers).indexOf(header);
                this.sortTable(columnIndex);
            });
        });
    }

    sortTable(columnIndex) {
        const table = document.getElementById('stock-table');
        const headers = table.querySelectorAll('th[data-sortable="true"]');
        const isAscending = headers[columnIndex].classList.contains('asc');
        const rows = Array.from(table.tBodies[0].rows);
      
        rows.sort((rowA, rowB) => {
            const valueA = this.parseNumber(rowA.cells[columnIndex].innerText.trim());
            const valueB = this.parseNumber(rowB.cells[columnIndex].innerText.trim());
          
            if (isAscending) {
              return valueA - valueB;
            } else {
              return valueB - valueA;
            }
          });
      
        // Clear existing table rows
        table.tBodies[0].innerHTML = '';
      
        // Append sorted rows to table body
        rows.forEach(row => {
          table.tBodies[0].appendChild(row);
        });
      
        // Toggle sorting order
        headers.forEach(header => {
          header.classList.remove('asc', 'desc');
        });
      
        if (isAscending) {
          headers[columnIndex].classList.add('desc');
        } else {
          headers[columnIndex].classList.add('asc');
        }
      }

      parseNumber(value) {
        return Number(value.replace(/,/g, ''));
      }
    }

const main = async () => {
    const historicalData = new HistoricalData();
    historicalData.mount();
    historicalData.setStatusBar();
};

window.addEventListener('DOMContentLoaded', main);
