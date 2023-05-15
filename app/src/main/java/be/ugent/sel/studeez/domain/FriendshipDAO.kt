package be.ugent.sel.studeez.domain

import be.ugent.sel.studeez.data.local.models.Friendship
import kotlinx.coroutines.flow.Flow

/**
 * Should be used for interactions between friends.
 */
interface FriendshipDAO {

    /**
     * @return all friendships of a chosen user.
     */
    fun getAllFriendships(
        userId: String
    ): Flow<List<Friendship>>

    /**
     * @return the amount of friends of a chosen user.
     * This method should be faster than just counting the length of getAllFriends()
     */
    fun getFriendshipCount(
        userId: String
    ): Flow<Int>

    /**
     * @param id the id of the friendship that you want details of
     * @return the details of a Friendship
     */
    fun getFriendshipDetails(id: String): Friendship

    /**
     * Send a friend request to a user.
     * @param id of the user that you want to add as a friend
     * @return Success/faillure of transaction
     */
    fun sendFriendshipRequest(id: String): Boolean

    /**
     * Accept a friend request that has already been sent.
     * @param id of the friendship that you want to update
     * @return: Success/faillure of transaction
     */
    fun acceptFriendship(id: String): Boolean

    /**
     * Remove a friend or decline a friendrequest.
     * @param friendship the one you want to remove
     * @return: Success/faillure of transaction
     */
    fun removeFriendship(
        friendship: Friendship
    ): Boolean
}