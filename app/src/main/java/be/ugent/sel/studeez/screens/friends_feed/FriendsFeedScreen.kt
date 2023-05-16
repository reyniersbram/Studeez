package be.ugent.sel.studeez.screens.friends_feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.common.composable.DateText
import be.ugent.sel.studeez.common.composable.PrimaryScreenTemplate
import be.ugent.sel.studeez.common.composable.drawer.DrawerActions
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarActions
import be.ugent.sel.studeez.data.local.models.FeedEntry
import be.ugent.sel.studeez.data.local.models.timer_functional.HoursMinutesSeconds
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.R.string as AppText

@Composable
fun FriendsFeedRoute(
    viewModel: FriendsFeedViewModel,
    drawerActions: DrawerActions,
    navigationBarActions: NavigationBarActions
) {
    FriendsFeedScreen(
        drawerActions = drawerActions,
        navigationBarActions = navigationBarActions,
        viewModel = viewModel
    )
}

@Composable
fun FriendsFeedScreen(
    drawerActions: DrawerActions,
    navigationBarActions: NavigationBarActions,
    viewModel: FriendsFeedViewModel
) {
    PrimaryScreenTemplate(
        title = resources().getString(AppText.friends_feed),
        drawerActions = drawerActions,
        navigationBarActions = navigationBarActions
    ) {

        val friendsSessions = viewModel.getFriendsSessions().collectAsState(initial = emptyList())
            



        LazyColumn() {
            // Default Timers, cannot be edited
            items(friendsSessions.value) {
                val (day, feedEntries) = it
                DateText(date = day)
                feedEntries.forEach { (name, feedEntry) ->
                    FriendsFeedEntry(name = name, feedEntry = feedEntry)
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

    }
}

@Composable
fun FriendsFeedEntry(
    name: String,
    feedEntry: FeedEntry
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(11f)
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color(feedEntry.argb_color)),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(0.dp),
                        modifier = Modifier.weight(10f),
                    ) {
                        Text(
                            text = "$name studied for ${feedEntry.subJectName}",
                            fontWeight = FontWeight.Medium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )
                        Text(
                            text = feedEntry.taskName,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )
                    }
                    Text(
                        text = HoursMinutesSeconds(feedEntry.totalStudyTime).toString(),
                        modifier = Modifier.weight(3f).padding(start = 5.dp),
                    )
                }
            }

        }
    }
}