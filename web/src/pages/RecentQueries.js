import StockSageClient from '../api/stockSageClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class RecentQueries extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount'], this);
        this.dataStore = new DataStore;
        this.header = new Header(this.dataStore);
        console.log("RecentQueries constructor")
    }

    mount () {
        this.header.addHeaderToPage();
        this.client = new StockSageClient();
    }
}

const main = async () => {
    const recentQueries = new RecentQueries();
    recentQueries.mount();
};

window.addEventListener('DOMContentLoaded', main);