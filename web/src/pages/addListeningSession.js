import EarTrackerClient from '../api/earTrackerClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class AddListeningSession extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit'], this)
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);

        document.getElementById('add').disabled = true;

        document.getElementById('startSession').addEventListener("change", (event) => {
            if (event.target.value) {
                document.getElementById("add").disabled = false;
            }
            else { 
                document.getElementById("add").disabled = true;
            }
          });
    }

    /**
     *  Add the header to the page and load the EarTrackerClient.
     */
    mount() {
        document.getElementById('add').addEventListener('click', this.submit);

        document.getElementById('back').addEventListener('click', this.redirectToMainMenu)
    
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
            //createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
            successMessageDisplay.classList.add('hidden');
        });
        this.dataStore.set('listeningSession', listeningSession);
        const successMessageDisplay = document.getElementById('success-message');
        successMessageDisplay.innerText = 'Successfully added listening session';
        successMessageDisplay.classList.remove('hidden')
    }

    redirectToMainMenu() {
        window.location.href = `/index.html`;
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const addListeningSession = new AddListeningSession();
    addListeningSession.mount();
};

window.addEventListener('DOMContentLoaded', main);
