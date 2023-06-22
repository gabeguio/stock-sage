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
        const username = (await this.client.authenticator.getCurrentUserInfo()).email

        const SavedQueriesList = await this.client.getSavedQueriesByUsername(username);

        SavedQueriesList.sort(function(a, b) {
            return b.queryId.localeCompare(a.queryId);
        });

        this.dataStore.set('SavedQueriesList', SavedQueriesList);

        var queries = this.dataStore.get('SavedQueriesList');

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
        savedRequestCard.classList.add('save-request-card');
        savedRequestCard.setAttribute('id', savedRequest.queryId);
      
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
        editButton.setAttribute('title', savedRequest.title)
        editButton.setAttribute('content', savedRequest.content)
      
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

      editSavedRequest(savedRequest) {
        const title = savedRequest.target.getAttribute('title');
        const content = savedRequest.target.getAttribute('content');
        const queryId = savedRequest.target.getAttribute('queryId');
      
        // Create a form for editing the saved request
        const form = document.createElement('form');
      
        const titleLabel = document.createElement('label');
        titleLabel.textContent = 'Title:';
        const titleInput = document.createElement('input');
        titleInput.type = 'text';
        titleInput.value = title;
      
        const contentLabel = document.createElement('label');
        contentLabel.textContent = 'Content:';
        const contentInput = document.createElement('textarea');
        contentInput.value = content;

        const buttonContainer = document.createElement('div');
        buttonContainer.classList.add('button-container');
      
        const saveButton = document.createElement('button');
        saveButton.classList.add('save-button');
        buttonContainer.appendChild(saveButton);
        saveButton.textContent = 'Save';
        saveButton.addEventListener('click', (event) => {
          event.preventDefault();
      
          const newTitle = titleInput.value;
          const newContent = contentInput.value;
      
          this.performPutRequest(queryId, newTitle, newContent);
      
          form.remove();
          this.loadSavedQueries();
        });
      
        const cancelButton = document.createElement('button');
        cancelButton.classList.add('cancel-button');
        cancelButton.textContent = 'Cancel';
        cancelButton.addEventListener('click', (event) => {
          event.preventDefault();
      
          form.remove();
          this.loadSavedQueries();
        });
      
        form.appendChild(titleLabel);
        form.appendChild(titleInput);
        form.appendChild(contentLabel);
        form.appendChild(contentInput);
        form.appendChild(buttonContainer)
        buttonContainer.appendChild(saveButton);
        buttonContainer.appendChild(cancelButton);
      
        const queryIdToPrint = savedRequest.queryId;
        const savedRequestCard = document.getElementById(savedRequest.target.getAttribute('queryId'));
        savedRequestCard.appendChild(form);
      }
      
    async deleteSavedRequest(event) {
        const confirmed = window.confirm("Are you sure you want to delete this saved request?")

        if (confirmed) {
          const username = (await this.client.authenticator.getCurrentUserInfo()).email
          const queryId = event.target.getAttribute('queryId');
          await this.client.unsaveQuery(username, queryId);
          this.loadSavedQueries();
        }
    }

    async performPutRequest(queryId, newTitle, newContent) {
        const username = (await this.client.authenticator.getCurrentUserInfo()).email
        await this.client.updateTitleAndContent(username, queryId, newTitle, newContent);
        this.loadSavedQueries();
    }
}

const main = async () => {
    const savedQueries = new SavedQueries();
    savedQueries.mount();
    savedQueries.setStatusBar();
    savedQueries.loadSavedQueries();
};

window.addEventListener('DOMContentLoaded', main);