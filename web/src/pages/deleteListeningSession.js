import EarTrackerClient from '../api/earTrackerClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class DeleteListeningSession extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit'], this)
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);

        document.getElementById('delete').disabled = true;

        document.getElementById('startSession', 'listeningType').addEventListener("change", (event) => {
            if (event.target.value) {
                document.getElementById("delete").disabled = false;
            }
            else { 
                document.getElementById("delete").disabled = true;
            }
          });
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
     * Method to run when the edit listening session submit button is pressed. Call the EarTrackerService to create the listeningSession.
     */
    async submit(evt) {
        evt.preventDefault();
        console.log("anyString")
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const startSession = document.getElementById('startSession').value;
        const listeningType = document.getElementById('listeningType').value;

        const listeningSession = await this.client.deleteListeningSession(startSession, listeningType, (error) => {
            // createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
            successMessageDisplay.classList.add('hidden');
        });
        
        this.dataStore.set('listeningSession', listeningSession);
        const successMessageDisplay = document.getElementById('success-message');
        successMessageDisplay.innerText = 'Successfully deleted listening session';
        successMessageDisplay.classList.remove('hidden');
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
