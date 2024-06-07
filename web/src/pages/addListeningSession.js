import EarTrackerClient from '../api/earTrackerClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class AddListeningSession extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewListeningSession'], this)
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewListeningSession)
        this.header = new Header(this.dataStore);
    }

    /**
     *  Add the header to the page and load the EarTrackerClient.
     */
    mount() {
        document.getElementById('add').addEventListener('click', this.submit);
    
        this.header.addHeaderToPage();

        this.client = new EarTrackerClient();
    }

    /**
     * Method to run when the add listening session submit button is pressed. Call the EarTrackerService to create the listeningSession.
     */
    async submit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const startSession = document.getElementById('startSession').value;
        const endSession = document.getElementById('endSession').value;
        const listeningType = document.getElementById('listeningType').value;
        const notes = document.getElementById('notes').value;

        const listeningSession = await this.client.addListeningSession(startSession, endSession, listeningType, notes, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('listeningSession', listeningSession);
    }

    /**
     * When the listening session is updated in the datastore, redirect to the view listening sessions page.
     */
    redirectToViewPlaylist() {
        const listeningSession = this.dataStore.get('listeningSession');
        if (listeningSession != null) {
            window.location.href = `/listeningSession.html?id=${listeningSession}`;
        }
    }
}