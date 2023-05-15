package be.ugent.sel.studeez.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.ui.theme.StudeezTheme

@Composable
fun ProfilePicture() {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(MaterialTheme.colors.primary, CircleShape)
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = stringResource(id = R.string.username),
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.Center),
            tint = MaterialTheme.colors.onPrimary
        )
    }
}

@Preview
@Composable
fun ProfilePicturePreview() {
    StudeezTheme {
        ProfilePicture()
    }
}