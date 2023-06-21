import StockSageClient from '../api/stockSageClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class RecentQueries extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'loadRecentQueries', 'addRecentQueriesListToPage', 'submitSaveQueryForm', 'setStatusBar'], this);
        this.dataStore = new DataStore;
        this.header = new Header(this.dataStore);
        console.log("RecentQueries constructor")
    }

    mount () {
        document.getElementById('loadRecentQueries').addEventListener('click', this.loadRecentQueries);
        document.getElementById('submitSaveQuery').addEventListener('click', this.submitSaveQueryForm);
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

        this.addRecentQueriesListToPage();

        //reset button text
        button.innerHTML = "Load Recent Queries";
    }

    addRecentQueriesListToPage() {
        var olElement = document.getElementById("viewRecentQueriesList");
        var queries = this.dataStore.get('recentQueriesList');
        queries.sort(function(a, b) {
            return b.queryId.localeCompare(a.queryId);
        });
        
        for (var i = 0; i < queries.length; i++) {
            var listItem = document.createElement("li");
            var divItem = document.createElement("div");

            listItem.appendChild(divItem);

            divItem.textContent = 
            queries[i].queryId
            + ", " + queries[i].startDate
            + ", " + queries[i].endDate
            + ", " + queries[i].frequency
            + ", " + queries[i].symbol;
            olElement.appendChild(listItem);
          }

    }

    async submitSaveQueryForm(event) {
        event.preventDefault();
        console.log("starting submitSaveQueryForm function");
      
        // Get the form values
        const username = (await this.client.authenticator.getCurrentUserInfo()).email
        const queryId = document.getElementById('queryId').value;
        const title = document.getElementById('title').value;
        const content = document.getElementById('content').value;

        // API request/response
        await this.client.saveQuery(username, queryId, title, content);
      }
}

const main = async () => {
    const recentQueries = new RecentQueries();
    recentQueries.mount();
    recentQueries.setStatusBar();
};

window.addEventListener('DOMContentLoaded', main);