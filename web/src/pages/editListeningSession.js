import EarTrackerClient from '../api/earTrackerClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class EditListeningSession extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewListeningSession'], this)
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewListeningSession)
        this.header = new Header(this.dataStore);

        document.getElementById('edit').disabled = true;

        document.getElementById('startSession').addEventListener("change", (event) => {
            if (event.target.value) {
                document.getElementById("edit").disabled = false;
            }
            else { document.getElementById("edit").disabled = true;
        }
          });
    }

    /**
     *  Add the header to the page and load the EarTrackerClient.
     */
    mount() {
        document.getElementById('edit').addEventListener('click', this.submit);

        document.getElementById('back').addEventListener('click', this.redirectToMainMenu);
    
        this.header.addHeaderToPage();

        this.client = new EarTrackerClient();
    }

    /**
     * Method to run when the edit listening session submit button is pressed. Call the EarTrackerService to create the listeningSession.
     */
    async submit(evt) {
        evt.preventDefault();
        console.log("anyString")
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const startSession = document.getElementById('startSession').value;
        const newStartSession = document.getElementById('newStartSession').value;
        const endSession = document.getElementById('endSession').value;
        const listeningType = document.getElementById('listeningType').value;
        const notes = document.getElementById('notes').value;

        const listeningSession = await this.client.editListeningSession(startSession, newStartSession, endSession, listeningType, notes, (error) => {
            // createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
            successMessageDisplay.classList.add('hidden');
        });
        
        this.dataStore.set('listeningSession', listeningSession);
        const successMessageDisplay = document.getElementById('success-message');
        successMessageDisplay.innerText = 'Successfully edited listening session';
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
    const editListeningSession = new EditListeningSession();
    editListeningSession.mount();
};

window.addEventListener('DOMContentLoaded', main);
