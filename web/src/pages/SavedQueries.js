import StockSageClient from '../api/stockSageClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class SavedQueries extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'loadSavedQueries'], this);
        this.dataStore = new DataStore;
        this.header = new Header(this.dataStore);
        console.log("SavedQueries constructor")
    }

    mount () {
        document.getElementById('loadSavedQueries').addEventListener('click', this.loadSavedQueries);

        this.header.addHeaderToPage();
        this.client = new StockSageClient();
    }

    async loadSavedQueries(event) {
        // prevent page refresh
        event.preventDefault();

        // button set to 'Loading...' when clicked
        const button = document.getElementById('loadSavedQueries');
        button.innerText = 'Loading...';

        //get username for Saved queries
        const username = (await this.client.authenticator.getCurrentUserInfo()).email

        // API request/response
        const SavedQueriesList = await this.client.getSavedQueriesByUsername(username);

        // store SavedQueriesList in dataStore
        this.dataStore.set('SavedQueriesList', SavedQueriesList);

        //reset button text
        button.innerHTML = "Load Saved Queries";

        // retrieve list of Saved queries, iterate through the list adding each object to an <ol>
        var olElement = document.getElementById("viewSavedQueriesList");
        var queries = this.dataStore.get('SavedQueriesList');
        queries.sort(function(a, b) {
            return b.queryId.localeCompare(a.queryId);
        });
        for (var i = 0; i < queries.length; i++) {
            var listItem = document.createElement("li");
            listItem.textContent = 
            queries[i].queryId
            + ", " + queries[i].startDate
            + ", " + queries[i].endDate
            + ", " + queries[i].frequency
            + ", " + queries[i].symbol
            + ", " + queries[i].title
            + ", " + queries[i].content
            olElement.appendChild(listItem);
          }
    }
}

const main = async () => {
    const savedQueries = new SavedQueries();
    savedQueries.mount();
};

window.addEventListener('DOMContentLoaded', main);