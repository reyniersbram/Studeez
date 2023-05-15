package be.ugent.sel.studeez.screens.profile.public_profile

import be.ugent.sel.studeez.domain.UserDAO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SelectedProfileState @Inject constructor(
    userDAO: UserDAO
) {
    var selectedUserId: String = userDAO.getCurrentUserId()
}