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
        this.bindClassMethods(['mount', 'createQuery'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        console.log("createQuery constructor");
    }

    mount() {
        document.getElementById('createQuery').addEventListener('click', this.createQuery);

        this.header.addHeaderToPage();
        this.client = new StockSageClient();
    }


    async createQuery(event) {
        //Prevent page refresh
        event.preventDefault();

        //button set to creating...
        const button = document.getElementById('createQuery');
        const origButtonText = button.innerText;
        button.innerText = 'Creating...';

        //get partition, sort key, and data fields
        const username = (await this.client.authenticator.getCurrentUserInfo()).email
        console.log("email: " + username);
        const queryId = document.getElementById('queryIdInput').value;
        const dateRequested = document.getElementById('dateRequestedInput').value;
        const fromDate = document.getElementById('fromDateInput').value;
        const toDate = document.getElementById('toDateInput').value;
        const frequency = document.getElementById('frequencyInput').value;
        const symbol = document.getElementById('symbolInput').value;
        const saved = document.getElementById('savedInput').value;
        console.log("retrieving api data from page...")

        //api request
        const query = await this.client.createQuery(username, queryId, dateRequested, fromDate, toDate, frequency, symbol, saved);
        console.log("Finished create query call...")

        //store response
        this.dataStore.set('query', query);
        console.log("create query response stored in datastore")
        console.log("Query Result: " + query);

        //button done loading
        button.innerHTML = "Create Query"

        //post get query response to page
        let queryResultsList = ` 
        <li>username: ${query.username}</li> 
        <li>queryId: ${query.queryId}</li> 
        <li>dateRequest: ${query.dateRequested}</li>
        <li>fromDate: ${query.fromDate}</li> 
        <li>toDate: ${query.toDate}</li> 
        <li>frequency: ${query.frequency}</li> 
        <li>symbol: ${query.symbol}</li> 
        <li>saved: ${query.saved}</li>`
        document.getElementById('createQueryContainer').innerHTML = queryResultsList;
    }
}

const main = async () => {
    const createQuery = new CreateQuery();
    createQuery.mount();
};

window.addEventListener('DOMContentLoaded', main);
