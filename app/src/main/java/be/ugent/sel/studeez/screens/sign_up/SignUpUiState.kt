package be.ugent.sel.studeez.screens.sign_up

data class SignUpUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = ""
)
