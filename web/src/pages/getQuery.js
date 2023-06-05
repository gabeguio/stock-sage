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
        this.bindClassMethods(['getQueryForPage', 'mount', 'addQueryToPage'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.dataStore.addChangeListener(this.addQueryToPage);
        console.log("getQuery constructor");
    }

    async getQueryForPage() {
        const urlParams = new URLSearchParams(window.location.search);
        const username = urlParams.get('username');
        const queryId = urlParams.get('queryId');
        const query = await this.client.getQuery(username, queryId);
        this.dataStore.set('query', query);
        console.dir(query);
    }

    mount() {
        this.header.addHeaderToPage();
        this.client = new StockSageClient();
        this.getQueryForPage();
    }

    addQueryToPage() {
        const query = this.dataStore.get('query');
        if (query == null) {
            console.log("query returned undefined from data store")
            return;
        }

        console.log("This is the query object: " + query)

        let html = `<p> 
        Username: ${query.username}, 
        ${query.queryId},
        ${query.dateRequested},
        ${query.fromDate},
        ${query.toDate},
        ${query.frequency},
        ${query.symbol},
        ${query.saved}`

        document.getElementById('response').innerHTML = html;
    }
}

const main = async () => {
    const getQuery = new GetQuery();
    getQuery.mount();
};

window.addEventListener('DOMContentLoaded', main);
