import StockSageClient from '../api/stockSageClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class SavedQueries extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'loadSavedQueries', 'unsaveQuery', 'submitEditQuery', 'setStatusBar'], this);
        this.dataStore = new DataStore;
        this.header = new Header(this.dataStore);
        console.log("SavedQueries constructor")
    }

    mount () {
        document.getElementById('loadSavedQueries').addEventListener('click', this.loadSavedQueries);
        document.getElementById('submitEditQuery').addEventListener('click', this.submitEditQuery);
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


    async loadSavedQueries(event) {
        // prevent page refresh
        event.preventDefault();

        // button set to 'Loading...' when clicked
        const loadButton = document.getElementById('loadSavedQueries');
        loadButton.innerText = 'Loading...';

        //get username for Saved queries
        const username = (await this.client.authenticator.getCurrentUserInfo()).email

        // API request/response
        const SavedQueriesList = await this.client.getSavedQueriesByUsername(username);

        // store SavedQueriesList in dataStore
        this.dataStore.set('SavedQueriesList', SavedQueriesList);

        //reset button text
        loadButton.innerHTML = "Load Saved Queries";

        // retrieve list of Saved queries, iterate through the list adding each object to an <ol>
        var olElement = document.getElementById("viewSavedQueriesList");

        // retrieve queries from the datastore
        var queries = this.dataStore.get('SavedQueriesList');
        
        // sort queries in descending order by queryId
        queries.sort(function(a, b) {
            return b.queryId.localeCompare(a.queryId);
        });

        // iterate through each item in the savedQueries response
        for (var i = 0; i < queries.length; i++) {
            // create an element for each item in the ordered list
            var listItem = document.createElement("li");

            // create a delete button for each item on the list
            var deleteButton = document.createElement("button");

            // name each button 'Delete'
            deleteButton.textContent = 'Delete';

            // create an edit button for each item on the list
            var editButton = document.createElement("button");

            // name each button 'Edit'
            editButton.textContent = 'Edit';

            // store query id on each delete button
            deleteButton.setAttribute('data-query-id', queries[i].queryId);
            editButton.setAttribute('data-query-id', queries[i].queryId);

            // add event listeners for each delete button
            deleteButton.addEventListener('click', this.unsaveQuery);
            deleteButton.addEventListener('click', this.editQuery);

            // populate each item in the list with each query attribute
            listItem.textContent = queries[i].startDate
            + ", " + queries[i].endDate
            + ", " + queries[i].frequency
            + ", " + queries[i].symbol
            + ", " + queries[i].title
            + ", " + queries[i].content

            // add the delete button node to the list item
            listItem.appendChild(deleteButton);

            // add the list item node to the ordered list
            olElement.appendChild(listItem);
          }
    }

    async unsaveQuery(event) {
        // Grab queryId off the button that is being clicked
        const username = (await this.client.authenticator.getCurrentUserInfo()).email;
        const queryId = event.target.getAttribute('data-query-id');

        // Submit request with username and query id
        await this.client.unsaveQuery(username, queryId);

        // Notify the user that the query has been unsaved
        alert("Query has been unsaved from your list.");
    }

    async submitEditQuery(event) {
        event.preventDefault();
        console.log("starting submitEditQuery function");
      
        // Get the form values
        const username = (await this.client.authenticator.getCurrentUserInfo()).email
        const queryId = document.getElementById('queryId').value;
        const title = document.getElementById('title').value;
        const content = document.getElementById('content').value;

        // API request/response
        await this.client.updateTitleAndContent(username, queryId, title, content);
      }
}

const main = async () => {
    const savedQueries = new SavedQueries();
    savedQueries.mount();
    savedQueries.setStatusBar();
};

window.addEventListener('DOMContentLoaded', main);