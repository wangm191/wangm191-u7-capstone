import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the EarTrackerService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
  */
export default class EarTrackerClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'addListeningSession', 'deleteListeningSession', 'editListeningSession', 'getListeningSessionByDate', 'getListeningSessionByType'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();;
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    /**
     * Get the identity of the current user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user information for the current user.
     */
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

    /**
     * Gets the playlist for the given ID.
     * @param id Unique identifier for a playlist
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The playlist's metadata.
     */
    async getPlaylist(id, errorCallback) {
        try {
            const response = await this.axiosClient.get(`playlists/${id}`);
            return response.data.playlist;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    } 

    /**
     * Get the songs on a given playlist by the playlist's identifier.
     * @param id Unique identifier for a playlist
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The list of songs on a playlist.
     */
    async getPlaylistSongs(id, errorCallback) {
        try {
            const response = await this.axiosClient.get(`playlists/${id}/songs`);
            return response.data.songList;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Add a new listeningSession owned by the current user.
     * @param startSession the starting LocalDateTime of the current session.
     * @param endSession the ending LocalDateTime of the current session. 
     * @param listeningType the type of current session. 
     * @param notes of the current session.
     * @return The listeningSession that has been created.
     */
    async addListeningSession(startSession, endSession, listeningType, notes, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create listening sessions.");
            const response = await this.axiosClient.post('listeningSessions/add', {
                startSession: startSession,
                endSession: endSession,
                listeningType: listeningType,
                notes: notes
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.listeningSession;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Deletes the listeningSession owned by the current user.
     * @param startSession The LocalDateTime of the start of the session.
     * @param listeningType The type of listening associated with the session.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The listeningSession that has been deleted.
     */
    async deleteListeningSession(startSession, listeningType, errorCallback) {
        try{
            const token = await this.getTokenOrThrow("Only authenticated users can delete listening sessions.");
            const response = await this.axiosClient.delete('listeningSessions/delete', {data:{
                startSession: startSession,
                listeningType: listeningType,
            },
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        return response.data.listeningSession;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Edits the listeningSession owned by the current user.
     * @param startSession The LocalDateTime of the start of the session.
     * @param newStartSession The new LocalDateTime of the start of session to be updated.
     * @param endSession The LocalDateTime of the end of the session (Optional to be updated).
     * @param listeningType The type of listening associated with the session (Optional to be updated).
     * @param notes The notes of the session (Optional to be updated).
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The listeningSession that has been deleted.
     */
    async editListeningSession(startSession, newStartSession, endSession, listeningType, notes, errorCallback) {
        try{
            const token = await this.getTokenOrThrow("Only authenticated users can edit listening sessions.");
            const response = await this.axiosClient.put('listeningSessions/edit', {
                startSession: startSession,
                newStartSession: newStartSession, 
                endSession: endSession, 
                listeningType: listeningType,
                notes: notes
        }, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        return response.data.listeningSession;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    // /**
    //  * Gets all the listeningSessions for the email.
    //  * @param email Unique identifier for the listeningSessions 
    //  * @param errorCallback (Optional) A function to execute if the call fails.
    //  * @returns The listeningSessions's metadata.
    //  */
    // async getAllListeningSessions(email, errorCallback) {
    //     try {
    //         const response = await this.axiosClient.get(`listeningSessions/${email}`);
    //         return response.data.listeningSession;
    //     } catch (error) {
    //         this.handleError(error, errorCallback)
    //     }
    // } 

     /**
     * Gets all the listeningSessions corresponding to the correct startSession date time.
     * @param startSession Unique RANGE key identifier for the listeningSessions
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The queried listeningSessions's metadata.
     */
    async getListeningSessionByDate(startSession, errorCallback) {
        try{
            const token = await this.getTokenOrThrow("Only authenticated users can get listening sessions.");
            const response = await this.axiosClient.get(`listeningSessions/date/${startSession}`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
            return response.data.listeningSessions;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    } 

     /**
     * Gets all the listeningSessions corresponding to the correct listeningType.
     * @param listeningType Unique RANGE key identifier for the listeningSessions
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The queried listeningSessions's metadata.
     */
     async getListeningSessionByType(listeningType, errorCallback) {
        try{
            const token = await this.getTokenOrThrow("Only authenticated users can get listening sessions.");
            const response = await this.axiosClient.get(`listeningSessions/type/${listeningType}`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
            return response.data.listeningSessions;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    } 



    /**
     * Create a new playlist owned by the current user.
     * @param name The name of the playlist to create.
     * @param tags Metadata tags to associate with a playlist.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The playlist that has been created.
     */
    async createPlaylist(name, tags, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create playlists.");
            const response = await this.axiosClient.post(`playlists`, {
                name: name,
                tags: tags
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.playlist;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Add a song to a playlist.
     * @param id The id of the playlist to add a song to.
     * @param asin The asin that uniquely identifies the album.
     * @param trackNumber The track number of the song on the album.
     * @returns The list of songs on a playlist.
     */
    async addSongToPlaylist(id, asin, trackNumber, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can add a song to a playlist.");
            const response = await this.axiosClient.post(`playlists/${id}/songs`, {
                id: id,
                asin: asin,
                trackNumber: trackNumber
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.songList;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Search for a soong.
     * @param criteria A string containing search criteria to pass to the API.
     * @returns The playlists that match the search criteria.
     */
    async search(criteria, errorCallback) {
        try {
            const queryParams = new URLSearchParams({ q: criteria })
            const queryString = queryParams.toString();

            const response = await this.axiosClient.get(`playlists/search?${queryString}`);

            return response.data.playlists;
        } catch (error) {
            this.handleError(error, errorCallback)
        }

    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
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