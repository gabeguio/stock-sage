import StockSageClient from '../api/stockSageClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class SavedQueries extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'setStatusBar', 'loadSavedQueries', 'renderSavedRequests', 'createSavedRequestCard', 'deleteSavedRequest', 'editSavedRequest'], this);
        this.dataStore = new DataStore;
        this.header = new Header(this.dataStore);
        console.log("SavedQueries constructor")
    }

    mount () {
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

    async loadSavedQueries() {
        //get username for Saved queries
        const username = (await this.client.authenticator.getCurrentUserInfo()).email

        // API request/response
        const SavedQueriesList = await this.client.getSavedQueriesByUsername(username);

        // Sort the list in descending order before storing
        SavedQueriesList.sort(function(a, b) {
            return b.queryId.localeCompare(a.queryId);
        });

        // store SavedQueriesList in dataStore
        this.dataStore.set('SavedQueriesList', SavedQueriesList);

        // retrieve queries from the datastore
        var queries = this.dataStore.get('SavedQueriesList');
        
        // sort queries in descending order by queryId

        this.renderSavedRequests();
    }

      renderSavedRequests() {
        const savedRequests = this.dataStore.get('SavedQueriesList');
        const savedRequestList = document.getElementById('saved-request-list');
        savedRequestList.innerHTML = '';
      
        for (const savedRequest of savedRequests) {
          const savedRequestCard = this.createSavedRequestCard(savedRequest);
          savedRequestList.appendChild(savedRequestCard);
        }
      }

      createSavedRequestCard(savedRequest) {
        const savedRequestCard = document.createElement('div');
        savedRequestCard.classList.add('object-card');
      
        const title = document.createElement('h3');
        title.textContent = savedRequest.title;
      
        const startDate = document.createElement('p');
        startDate.textContent = `Start Date: ${savedRequest.startDate}`;
      
        const endDate = document.createElement('p');
        endDate.textContent = `End Date: ${savedRequest.endDate}`;
      
        const symbol = document.createElement('p');
        symbol.textContent = `Symbol: ${savedRequest.symbol}`;
      
        const frequency = document.createElement('p');
        frequency.textContent = `Frequency: ${savedRequest.frequency}`;
      
        const content = document.createElement('p');
        content.textContent = savedRequest.content;
      
        const editButton = document.createElement('button');
        editButton.textContent = 'Edit';
        editButton.addEventListener('click', this.editSavedRequest);
        editButton.setAttribute('queryId', savedRequest.queryId)
      
        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'Delete';
        deleteButton.addEventListener('click', this.deleteSavedRequest);
        deleteButton.setAttribute('queryId', savedRequest.queryId)
      
        savedRequestCard.appendChild(title);
        savedRequestCard.appendChild(startDate);
        savedRequestCard.appendChild(endDate);
        savedRequestCard.appendChild(symbol);
        savedRequestCard.appendChild(frequency);
        savedRequestCard.appendChild(content);
        savedRequestCard.appendChild(editButton);
        savedRequestCard.appendChild(deleteButton);
      
        return savedRequestCard;
      }

    async editSavedRequest(event) {
        // event.preventDefault();
        // console.log("starting submitEditQuery function");
        console.log("editSaveRequest");
      
        // Get the form values
        const username = (await this.client.authenticator.getCurrentUserInfo()).email
        const queryId = event.target.getAttribute('queryId')
        const title = document.getElementById('title').value;
        const content = document.getElementById('content').value;

        // API request/response
        await this.client.updateTitleAndContent(username, queryId, title, content);
      }
      
      // Function to handle delete button click
    async deleteSavedRequest(event) {
        const username = (await this.client.authenticator.getCurrentUserInfo()).email
        const queryId = event.target.getAttribute('queryId');
        await this.client.unsaveQuery(username, queryId);
        // renderSavedRequests();
    }
}

const main = async () => {
    const savedQueries = new SavedQueries();
    savedQueries.mount();
    savedQueries.setStatusBar();
    savedQueries.loadSavedQueries();
};

window.addEventListener('DOMContentLoaded', main);