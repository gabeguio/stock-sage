import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

export default class StockSageClient extends BindingClass {
    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'getQuery', 'createQuery'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();;
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    async getIdentity(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async login() {
        this.authenticator.login();
    }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

    async getQuery(username, queryId, errorCallback) {
        try {
            const response = await this.axiosClient.get(`query/${username}/${queryId}`);
            return response.data.query;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async createQuery(username, startDate, endDate, frequency, symbol, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create playlists.");
            const response = await this.axiosClient.post(`createquery`, {
                username: username,
                startDate: startDate,
                endDate: endDate,
                frequency: frequency,
                symbol: symbol,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.stockModels;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async getRecentQueriesByUsername(username, errorCallback) {
        try {
            const response = await this.axiosClient.get(`/recent-queries/${username}`);
            return response.data.queryModelList;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async getSavedQueriesByUsername(username, errorCallback) {
        try {
            const response = await this.axiosClient.get(`/saved-queries/${username}`);
            console.log(response);
            return response.data.queryModelList;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async saveQuery(username, queryId, title, content, errorCallback) {
        try {
            const response = await this.axiosClient.get(`/save-query`, {
                username: username,
                queryId: queryId,
                title: title,
                content: content,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            console.log(response);
            return response.data.queryModel;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }
}