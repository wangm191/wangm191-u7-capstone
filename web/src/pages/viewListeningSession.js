import EarTrackerClient from '../api/earTrackerClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class ViewListeningSession extends BindingClass {
    constructor(params) {
        super();
        this.bindClassMethods(['clientLoaded', 'mount','addListeningSessionByDateToPage', 'addListeningSessionByTypeToPage', 'searchListeningSessionByDate', 'searchListeningSessionByType'], this)
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addListeningSessionByDateToPage);
        this.dataStore.addChangeListener(this.addListeningSessionByTypeToPage);
        this.header = new Header(this.dataStore);
    }

    /**
     * Once the client is loaded, get the listeningSession metadata.
     */
    async clientLoaded() {

        document.getElementById('listeningSessionsByDate').innerText = "Loading Listening Sessions By Date ...";
        document.getElementById('listeningSessionsByType').innerText = "Loading Listening Session By Type ...";  
        
    }

    /**
     *  Add the header to the page and load the EarTrackerClient.
     */
    mount() {

        document.getElementById('search-listeningSession-by-date').addEventListener('click', this.searchListeningSessionByDate);
        document.getElementById('search-listeningSession-by-type').addEventListener('click', this.searchListeningSessionByType);

        document.getElementById('back').addEventListener('click', this.redirectToMainMenu)
        
        this.header.addHeaderToPage();

        this.client = new EarTrackerClient();
        this.clientLoaded();
    }

    addListeningSessionByDateToPage() {
        const listeningSessionsByDate = this.dataStore.get('listeningSessionsByDate');
        if (listeningSessionsByDate == null) {
            return;
        }
        let listeningSessionsHtml = '<table id="listeningSession-date-index"><tr><th>Start Session</th><th>End Session</th><th>Listening Type</th><th>Time Elapsed</th><th>Notes</th></tr>';
        for (var listeningSession of listeningSessionsByDate) {
            listeningSessionsHtml += `
            <tr email="${listeningSession.email}">
                <td>${listeningSession.startSession}</td>
                <td>${listeningSession.endSession}</td>
                <td>${listeningSession.listeningType}</td>
                <td>${listeningSession.timeElapsed}</td>
                <td>${listeningSession.notes}</td>
            </tr>`;
        }
        document.getElementById('listeningSessionsByDate').innerHTML = listeningSessionsHtml;
    }

    addListeningSessionByTypeToPage() {
        const listeningSessionsByType = this.dataStore.get('listeningSessionsByType');
        if (listeningSessionsByType == null) {
            return;
        }
        let listeningSessionsHtml = '<table id="listeningSession-type-index"><tr><th>Start Session</th><th>End Session</th><th>Listening Type</th><th>Time Elapsed</th><th>notes</th></tr>';
        for (var listeningSession of listeningSessionsByType) {
            listeningSessionsHtml += `
            <tr email="${listeningSession.email}">
                <td>${listeningSession.startSession}</td>
                <td>${listeningSession.endSession}</td>
                <td>${listeningSession.listeningType}</td>
                <td>${listeningSession.timeElapsed}</td>
                <td>${listeningSession.notes}</td>
            </tr>`;
        }
        document.getElementById('listeningSessionsByType').innerHTML = listeningSessionsHtml;
    }

    async searchListeningSessionByDate() {
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        document.getElementById('search-listeningSession-by-date').innerText = 'Search Parameters...'
        const startSession = document.getElementById('startSession').value;
        
        const listeningSessionsByDate = await this.client.getListeningSessionByDate(startSession, (error) => {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');  
        })

        this.dataStore.set('listeningSessionsByDate', listeningSessionsByDate);

        document.getElementById('search-listeningSession-by-date').innerText = "Search Again"
        document.getElementById("view-listeningSession-by-date-form").reset();
    }

    async searchListeningSessionByType() {
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        document.getElementById('search-listeningSession-by-type').innerText = 'Search Parameters...'
        const listeningType = document.getElementById('listeningType').value;
        
        const listeningSessionsByType = await this.client.getListeningSessionByType(listeningType, (error) => {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');  
        })

        this.dataStore.set('listeningSessionsByType', listeningSessionsByType);

        document.getElementById('search-listeningSession-by-type').innerText = "Search Again"
        document.getElementById("view-listeningSession-by-type-form").reset();
    }

    redirectToMainMenu() {
        window.location.href = `/index.html`;
    }

}

/**
 * Main method to run when the page contents have loaded. 
 */
const main = async () => {
    const viewListeningSession = new ViewListeningSession();
    viewListeningSession.mount();
}

window.addEventListener('DOMContentLoaded', main);