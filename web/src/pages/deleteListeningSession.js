import EarTrackerClient from "../api/earTrackerClient";
import Header from "../components/header";
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class DeleteListeningSession extends BindingClass {
    constructor(params) {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewListeningSession'], this)
        this.dataStore = new DataStore();
        this.header = new Header(this.DataStore);
    }

     /**
     *  Add the header to the page and load the EarTrackerClient.
     */
     mount() {
        document.getElementById('delete').addEventListener('click', this.submit);
        
        document.getElementById('back').addEventListener('click', this.redirectToMainMenu);
    
        this.header.addHeaderToPage();

        this.client = new EarTrackerClient();
    }

    /**
     * Method to run when the delete listening session submit button is pressed. Call the EarTrackerService to delete the listeningSession.
     */
    async submit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden')

        const startSession = document.getElementById('startSession').value;
        const listeningType = document.getElementById('listeningType').value;

        const listeningSession = await this.client.deleteListeningSession(startSession, listeningType, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('listeningSession', listeningSession);
        const successMessageDisplay = document.getElementById('success-message');
        successMessageDisplay.innerText = 'Successfully removed listening session';
        successMessageDisplay.classList.remove('hidden')
    }

    /**
     * When the listening session is updated in the datastore, redirect to the view listening sessions page.
     */
    redirectToViewListeningSession() {
        const listeningSession = this.dataStore.get('listeningSession');
        if (listeningSession != null) {
            window.location.href = `/viewListeningSession.html?id=${listeningSession}`;
        }
    }

    redirectToMainMenu() {
        window.location.href = `/index.html`;
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const deleteListeningSession = new DeleteListeningSession();
    deleteListeningSession.mount();
};

window.addEventListener('DOMContentLoaded', main);