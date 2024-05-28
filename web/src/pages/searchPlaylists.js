
import Authenticator from "../api/authenticator";


/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
   var authenticator = new Authenticator();
   authenticator.configureCognito();
   authenticator.login();
};

window.addEventListener('DOMContentLoaded', main);
