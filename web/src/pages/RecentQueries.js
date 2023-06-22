import StockSageClient from '../api/stockSageClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class RecentQueries extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'setStatusBar', 'loadRecentQueries', 'renderRecentRequests', 'createRecentRequestCard', 'saveRequest',], this);
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
        const username = (await this.client.authenticator.getCurrentUserInfo()).email

        const recentQueriesList = await this.client.getRecentQueriesByUsername(username);

        recentQueriesList.sort(function(a, b) {
            return b.queryId.localeCompare(a.queryId);
        });

        this.dataStore.set('RecentQueriesList', recentQueriesList);

        var queries = this.dataStore.get('RecentQueriesList');

        this.renderRecentRequests();
    }

      renderRecentRequests() {
        const recentRequests = this.dataStore.get('RecentQueriesList');
        const recentRequestList = document.getElementById('recent-request-list');
      
        for (const recentRequest of recentRequests) {
          const recentRequestCard = this.createRecentRequestCard(recentRequest);
          recentRequestList.appendChild(recentRequestCard);
        }
      }

      createRecentRequestCard(recentRequest) {
        const recentRequestCard = document.createElement('div');
        recentRequestCard.classList.add('recent-request-card');
        recentRequestCard.setAttribute('id', recentRequest.queryId);
      
        const timeStamp = document.createElement('h3');
        timeStamp.textContent = `Time Stamp: ${recentRequest.queryId}`;
      
        const startDate = document.createElement('p');
        startDate.textContent = `Start Date: ${recentRequest.startDate}`;
      
        const endDate = document.createElement('p');
        endDate.textContent = `End Date: ${recentRequest.endDate}`;

        const frequency = document.createElement('p');
        frequency.textContent = `Frequency: ${recentRequest.frequency}`;
      
        const symbol = document.createElement('p');
        symbol.textContent = `Symbol: ${recentRequest.symbol}`;
      
        const saveButton = document.createElement('button');
        saveButton.textContent = 'Save';
        saveButton.addEventListener('click', this.saveRequest);
        saveButton.setAttribute('queryId', recentRequest.queryId)
      
        recentRequestCard.appendChild(timeStamp);
        recentRequestCard.appendChild(startDate);
        recentRequestCard.appendChild(endDate);
        recentRequestCard.appendChild(symbol);
        recentRequestCard.appendChild(frequency);
        recentRequestCard.appendChild(saveButton);
      
        return recentRequestCard;
      }

      async saveRequest(recentRequest) {
        const username = (await this.client.authenticator.getCurrentUserInfo()).email
        const queryId = recentRequest.target.getAttribute('queryId');
        await this.client.saveQuery(username, queryId);
        console.log("success");
      }

}

const main = async () => {
    const recentQueries = new RecentQueries();
    recentQueries.mount();
    recentQueries.setStatusBar();
    recentQueries.loadRecentQueries();
};

window.addEventListener('DOMContentLoaded', main);