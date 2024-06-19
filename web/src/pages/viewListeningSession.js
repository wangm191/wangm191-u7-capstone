import EarTrackerClient from '../api/earTrackerClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class ViewListeningSession extends BindingClass {
    constructor(params) {
        super();
        this.bindClassMethods(['mount', 'redirectToMainMenu'], this)
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        console.log("viewListeningSession constructor");
    }

    /**
     * Once the client is loaded, get the listeningSession metadata.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const listeningSession = urlParams.get('email');
        document.getElementById('listeningSessionsByDate').innerText = "Loading Listening Sessions By Date ...";
        document.getElementById('listeningSessionsByType').innerText = "Loading Listening Session By Type ..."; 
        const listeningSessionsByDate = await this.client.getListeningSessionByDate(startSession);
        this.dataStore.set('listeningSessionsByDate', listeningSessionsByDate)
        this.addListeningSessionByDateToPage();
        
    }

    /**
     *  Add the header to the page and load the EarTrackerClient.
     */
    mount() {

        document.getElementById('search-listeningSession-by-date').addEventListener('click', this.redirectToGetListeningSessionByDate);

        document.getElementById('back').addEventListener('click', this.redirectToMainMenu);
        
        this.header.addHeaderToPage();

        this.client = new EarTrackerClient();
        this.clientLoaded();
    }

    /**
     * When the listeningSession is updated in the datastore, update the listeningSession metadata on the page.
     */
    // addAllListeningSessionToPage() {
    //     const allListeningSessions = this.dataStore.get('allListeningSessions');
    //     if (allListeningSessions == null) {
    //         return;
    //     }
    //     let listeningSessionsHtml = '<table email="listeningSession-email"><tr><th>startSession</th><th>endSession</th><th>listeningType</th><th>timeElapsed</th><th>notes</th></tr>';

    //     for (var listeningSession of allListeningSessions) {
    //         listeningSessionsHtml +=
    //         <tr email="${listeningSession.email}">
    //             <td>${listeningSession.startSession}</td>
    //             <td>${listeningSession.endSession}</td>
    //             <td>${listeningSession.listeningType}</td>
    //             <td>${listeningSession.timeElapsed}</td>
    //             <td>${listeningSession.notes}</td>
    //         </tr>
    //     }

    //     document.getElementById('allListeningSessions').innerText = listeningSessionsHtml;

    //     document.getElementById(listeningSession-email).innerText = listeningSession.email;
    // }

    addListeningSessionByDateToPage() {
        const listeningSessionsByDate = this.dataStore.get('listeningSessionsByDate');
        if (listeningSessionsByDate == null) {
            return;
        }
        let listeningSessionsHtml = '<table email="listeningSession-email"><tr><th>startSession</th><th>endSession</th><th>listeningType</th><th>timeElapsed</th><th>notes</th></tr>';
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
        document.getElementById('listeningSessionsByDate').innerText = listeningSessionsHtml;
    }

    redirectToGetListeningSessionByDate(){
        window.location.href = `/getListeningSessionByDate.html`;
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