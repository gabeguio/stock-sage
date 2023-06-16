import StockSageClient from '../api/stockSageClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view playlist page of the website.
 */
class GetQuery extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'getQuery'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        console.log("getQuery constructor");
    }

    mount() {
        document.getElementById('getQuery').addEventListener('click', this.getQuery);

        this.header.addHeaderToPage();
        this.client = new StockSageClient();
    }


    async getQuery(event) {
        //Prevent page refresh
        event.preventDefault();

        //button set to loading...
        const button = document.getElementById('getQuery');
        const origButtonText = button.innerText;
        button.innerText = 'Loading...';

        //get partition and sort key
        const username = (await this.client.authenticator.getCurrentUserInfo()).email
        console.log("email: " + username);
        const queryId = document.getElementById('queryIdInput').value;
        console.log("retrieving api data from page...")

        //api request
        const query = await this.client.getQuery(username, queryId);
        console.log("calling get query endpoint...")

        //store response
        this.dataStore.set('query', query);
        console.log("get query response stored in datastore")
        console.log("Query Result: " + query);

        //button done loading
        button.innerHTML = "Get Query"

        //post get query response to page
        let response = ` 
        <li>username: ${query.username}</li> 
        <li>queryId: ${query.queryId}</li> 
        <li>dateRequest: ${query.dateRequested}</li>
        <li>fromDate: ${query.fromDate}</li> 
        <li>toDate: ${query.toDate}</li> 
        <li>frequency: ${query.frequency}</li> 
        <li>symbol: ${query.symbol}</li> 
        <li>saved: ${query.saved}</li>`
        document.getElementById('get-query-container').innerHTML = response;
    }
}

const main = async () => {
    const getQuery = new GetQuery();
    getQuery.mount();
};

window.addEventListener('DOMContentLoaded', main);
