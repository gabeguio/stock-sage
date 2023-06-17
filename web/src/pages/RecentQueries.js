import StockSageClient from '../api/stockSageClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class RecentQueries extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'loadRecentQueries'], this);
        this.dataStore = new DataStore;
        this.header = new Header(this.dataStore);
        console.log("RecentQueries constructor")
    }

    mount () {
        document.getElementById('loadRecentQueries').addEventListener('click', this.loadRecentQueries);

        this.header.addHeaderToPage();
        this.client = new StockSageClient();
    }

    async loadRecentQueries(event) {
        // prevent page refresh
        event.preventDefault();

        // button set to 'Loading...' when clicked
        const button = document.getElementById('loadRecentQueries');
        button.innerText = 'Loading...';

        //get username for recent queries
        const username = (await this.client.authenticator.getCurrentUserInfo()).email

        // API request/response
        const recentQueriesList = await this.client.getRecentQueriesByUsername(username);

        // store recentQueriesList in dataStore
        this.dataStore.set('recentQueriesList', recentQueriesList);

        //reset button text
        button.innerHTML = "Load Recent Queries";

        // retrieve list of recent queries, iterate through the list adding each object to an <ol>
        var olElement = document.getElementById("viewRecentQueriesList");
        var queries = this.dataStore.get('recentQueriesList');
        queries.sort(function(a, b) {
            return b.queryId.localeCompare(a.queryId);
        });
        for (var i = 0; i < queries.length; i++) {
            var listItem = document.createElement("li");
            listItem.textContent = 
            queries[i].queryId
            + ", " + queries[i].startDate
            + ", " + queries[1].endDate
            + ", " + queries[1].frequency
            + ", " + queries[1].symbol
            + ", " + queries[1].saved;
            olElement.appendChild(listItem);
          }
    }
}

const main = async () => {
    const recentQueries = new RecentQueries();
    recentQueries.mount();
};

window.addEventListener('DOMContentLoaded', main);