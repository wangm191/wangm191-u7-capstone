import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the MusicPlaylistService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
  */
export default class MusicPlaylistClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'getPlaylist', 'getPlaylistSongs', 'createPlaylist', 'addTracker', 'compareJobTypes'];
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
         * Create a new tracker owned by the current user.
         * @param date The date of the tracker to create.
         * @param jobType The job type of the tracker to create.
         * @param hoursWorked The hours worked of the tracker to create.
         * @param totalEarnings The total earnings of the tracker to create.
         * @param errorCallback (Optional) A function to execute if the call fails.
         * @returns The tracker that has been created.
         */
    async addTracker(date, jobType, hoursWorked, totalEarnings, errorCallback) {
        try {                const token = await this.getTokenOrThrow("Only authenticated users can create playlists.");
            const response = await this.axiosClient.post(`hub/tracker/create`, {
                date: date,
                jobType: jobType,
               hoursWorked: hoursWorked,
                totalEarnings: totalEarnings
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.tracker;
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
         * Add month and target goal to the DDB.
         * @param month The month that user set to the month and set as String.Not date
         * @param target the target that set to the target goal.
         * @returns The month and Target number.
         */
    async addMonthAndTarget(month, targetAmount, errorCallback) {
       try {
          const token = await this.getTokenOrThrow("Only authenticated users can set monthly goal.");
          const response = await this.axiosClient.post(`hub/target`, {
                    month: month,
                    targetAmount: targetAmount,
          }, {
              headers: {
                    Authorization: `Bearer ${token}`
                    }
                });
                return response.data.target;
          } catch (error) {
                this.handleError(error, errorCallback)
          }
        }

       /**

         * delete month and target goal to the DDB.
         * @returns The month and Target number.
       */
    async deleteMonthAndTarget(month, targetAmount, errorCallback) {
       try {
          const token = await this.getTokenOrThrow("Only authenticated users can set monthly goal.");
          const response = await this.axiosClient.delete(`hub/target/delete`, {data:{
                    month: month,
                    targetAmount: targetAmount,
          },
              headers: {
                    Authorization: `Bearer ${token}`
                    }
                });
                return response.data.target;
          } catch (error) {
                this.handleError(error, errorCallback)
          }
        }

         /**
         * Gets the compareJobTypesTable for the given date.
         * @param date Unique identifier for tracker
         * @param errorCallback (Optional) A function to execute if the call fails.
         * @returns The compareJobTypes metadata.
         */
    async compareJobTypes(date, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can set monthly goal.");
            const response = await this.axiosClient.get(`hub/tracker/compare/${date}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                    }
                });

                return response.data.compareJobTypesMap;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Search for a song.
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

        /**
         * Find
         * @param error The error received from the server.
         * @param errorCallback (Optional) A function to execute if the call fails.
         */

}
