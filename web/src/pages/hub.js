import MusicPlaylistClient from '../api/musicPlaylistClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view playlist page of the website.
 */
class Hub extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'addMonthAndTarget','logout', 'deleteMonthAndTarget','displayResults','getHTMLForResults', 'addDateAndTracker', 'compareJobTypesToPage', ], this);

        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.compareJobTypes)
        this.header = new Header(this.dataStore);
        this.dataStore.addChangeListener(this.displayResults)
    }

    async clientLoaded() {
        const compareJobTypes = await this.client.compareJobTypes(compareJobTypesId)
        this.dataStore.set('compareJobTypes', compareJobTypes)
    }

    mount() {

        document.getElementById('submit').addEventListener('click', this.addMonthAndTarget);
        document.getElementById('addTracker').addEventListener('click', this.addDateAndTracker);
        document.getElementById('compareJobTypes').addEventListener('click', this.compareJobTypesToPage);

        this.header.addHeaderToPage();
        this.client = new MusicPlaylistClient();

        }

        /**
         * Method to run when the add month and target 'addTarget' button is pressed. Call the hubs to add month and target to the
         * target DDB.
         */
    async addMonthAndTarget(evt) {
        evt.preventDefault();

        document.getElementById('submit').innerText = 'Added';
        const month = document.getElementById('month').value;
        const target = document.getElementById('target').value;

        const monthAndTarget = await this.client.addMonthAndTarget(month, target, errorCallback => {
        errorMessageDisplay.innerText = `Error: ${error.message}`;
        errorMessageDisplay.classList.remove('hidden');
        });

        this.dataStore.set('targets', target);
        }

    async deleteMonthAndTarget(evt) {
        evt.preventDefault();

        document.getElementById('delete').innerText = 'Deleted';
        const month = document.getElementById('month').value;
        const target = document.getElementById('target').value;

        const deleteMonthAndTarget = await this.client.deleteMonthAndTarget(month, target, errorCallback => {
        console.error ('This request is not good')
        });

        this.dataStore.set('targets', target);
        }

        /**
         * Method to run when the add date and tracker 'addTracker' button is pressed. Call the hubs to add date and tracker to the
         * target DDB.
         */
    async addDateAndTracker(evt) {
        evt.preventDefault();

        document.getElementById('addTracker').innerText = 'Added';
        const date = document.getElementById('date').value;
        const jobType = document.getElementById('jobType').value;
        const hoursWorked = document.getElementById('hoursWorked').value;
        const totalEarnings = document.getElementById('totalEarnings').value;

        const tracker = await this.client.addTracker(date, jobType, hoursWorked, totalEarnings, errorCallback => {
        errorMessageDisplay.innerText = `Error: ${error.message}`;
        errorMessageDisplay.classList.remove('hidden');
        });

        this.dataStore.set('trackers', tracker)


    }

    async compareJobTypesToPage(evt) {
        evt.preventDefault();

        const date = document.getElementById('compareDate').value;

        const compareJobTypes = await this.client.compareJobTypes(date, console.error)

        if (compareJobTypes == null) {
            return;
        }

        const list = document.getElementById('jobs');
            for (const keys in compareJobTypes) {
              const listItem = document.createElement('li');
              listItem.textContent = keys + ': ' + compareJobTypes[keys];
              list.appendChild(listItem);
            }

    }

    async logout() {
        document.getElementById('logoutButton').addEventListener('click', () => {this.authenticator.logout();
    });
    }

    /**
     * Pulls added data from the datastore and displays them on the html page.
     */
    displayResults() {
        const showMonth = this.dataStore.get("month");
        const showTarget = this.dataStore.get("target_amount");

        if (showMonth === '' || showTarget === '') {

            searchCriteriaDisplay.innerHTML = '';
            searchResultsDisplay.innerHTML = '';
        } else {
           document.getElementById('results').innerHTML = this.getHTMLForResults();
           //TODO get the results endpoints (list) to display the result table.

        }
    }

    /**
     * Create appropriate HTML for displaying searchResults on the page.
     * @param Results An array of target objects to be displayed on the page.
     * @returns A string of HTML suitable for being dropped on the page.
     */
    getHTMLForResults(Results) {
        if (Results.length === 0) {
            return '<h4>No results found</h4>';
        }
        let html = '<table><tr><th>Name</th><th>Song Count</th><th>Tags</th></tr>';
        for (const res of Results) {
            html += `
            <tr>
                <td>${res.target_amount}</td>
                <td>${res.month.join(', ')}</td>
            </tr>`;
        }
        html += '</table>';
        return html;
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const hub = new Hub();
    hub.mount();
};

window.addEventListener('DOMContentLoaded', main);
