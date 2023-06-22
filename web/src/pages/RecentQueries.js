import StockSageClient from '../api/stockSageClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class RecentQueries extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'loadRecentQueries', 'addRecentQueriesListToPage', 'setStatusBar', 'saveTitleAndContent'], this);
        this.dataStore = new DataStore;
        this.header = new Header(this.dataStore);
        console.log("RecentQueries constructor")
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

    async loadRecentQueries() {
        //get username for recent queries
        const username = (await this.client.authenticator.getCurrentUserInfo()).email

        // API request/response
        const recentQueriesList = await this.client.getRecentQueriesByUsername(username);

        // store recentQueriesList in dataStore
        this.dataStore.set('recentQueriesList', recentQueriesList);

        this.addRecentQueriesListToPage();
    }

    async addRecentQueriesListToPage() { 
    const requestList = document.querySelector('.request-list');
    const requests = this.dataStore.get('recentQueriesList')
    
    for (let i = 0; i < requests.length; i++) {
        const request = requests[i];
    
        const requestItem = document.createElement('div');
        requestItem.classList.add('request-item');
    
        const requestData = document.createElement('div');
        requestData.classList.add('request-data');
    
        const date = document.createElement('p');
        date.textContent = `Date: ${request.dateRequested}`;
        requestData.appendChild(date);
    
        const startDate = document.createElement('p');
        startDate.textContent = `Start Date: ${request.startDate}`;
        requestData.appendChild(startDate);
    
        const endDate = document.createElement('p');
        endDate.textContent = `End Date: ${request.endDate}`;
        requestData.appendChild(endDate);
    
        const aggregationPeriod = document.createElement('p');
        aggregationPeriod.textContent = `Aggregation Period: ${request.frequency}`;
        requestData.appendChild(aggregationPeriod);
    
        const stockTicker = document.createElement('p');
        stockTicker.textContent = `Stock Ticker: ${request.symbol}`;
        requestData.appendChild(stockTicker);
    
        requestItem.appendChild(requestData);
    
        const requestActions = document.createElement('div');
        requestActions.classList.add('request-actions');
    
        const saveButton = document.createElement('button');
        saveButton.textContent = 'Save';
        saveButton.classList.add('save-button');
        saveButton.dataset.title = request.title; // Save the title as data on the button
        saveButton.dataset.content = request.content; // Save the content as data on the button
        requestActions.appendChild(saveButton);
    
        const saveForm = document.createElement('form');
        saveForm.classList.add('save-form');
        saveForm.innerHTML = `
          <input type="text" name="title" placeholder="Title" value="${request.title}">
          <textarea name="content" placeholder="Content">${request.content}</textarea>
          <button type="submit">Submit</button>
        `;
        requestActions.appendChild(saveForm);
    
        requestItem.appendChild(requestActions);
    
        requestList.appendChild(requestItem);

        const saveButtons = document.querySelectorAll('.save-button');
        saveButtons.forEach(button => {
        button.addEventListener('click', (event) => {
            const saveForm = button.nextElementSibling;
            saveForm.style.display = 'block';
            });
        });

        const saveForms = document.querySelectorAll('.save-form');
        saveForms.forEach(form => {
            form.setAttribute('queryId', request.queryId)
        form.addEventListener('submit', this.saveTitleAndContent);
      });
    }
}

    async saveTitleAndContent(event) {
        console.log("hola");
        const username = (await this.client.authenticator.getCurrentUserInfo()).email
        const queryId = event.target.getAttribute('queryId');
        const title = form.elements.title.value;
        const content = form.elements.content.value;

        const helli = await this.client.saveQuery(username, queryId, title, content);
        console.log("hello");
        event.preventDefault();

        // event.form.style.display = 'none';
    }

    pullTitleAndContent(event) {
        // Access the request data from the event target (save button)
        const requestData = event.target.dataset.requestData;
        if (requestData) {
            const { title, content } = JSON.parse(requestData);
            // Display the form with pre-populated data for editing
            this.displayForm(title, content);
        }
    }
}

const main = async () => {
    const recentQueries = new RecentQueries();
    recentQueries.mount();
    recentQueries.setStatusBar();
    recentQueries.loadRecentQueries();
};

window.addEventListener('DOMContentLoaded', main);