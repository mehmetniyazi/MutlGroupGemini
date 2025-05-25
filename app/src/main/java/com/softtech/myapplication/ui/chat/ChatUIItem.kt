package com.softtech.myapplication.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ChatUIItem(modifier: Modifier, chatItem: ChatItem) {
    Row(
        modifier.padding(start = if (chatItem.type == ChatType.AI) 8.dp else 40.dp, end = if (chatItem.type == ChatType.AI) 40.dp else 8.dp),
        horizontalArrangement = if (chatItem.type == ChatType.AI) Arrangement.Start else Arrangement.End
    ) {
        Row(
            modifier = Modifier
                .background(
                    if (chatItem.type == ChatType.AI) MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.50f)
                    else MaterialTheme.colorScheme.secondary.copy(alpha = 0.50f),
                    shape = RoundedCornerShape(12.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Text(
                text = chatItem.message,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                color = if (chatItem.type == ChatType.AI) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.background
            )
        }
    }
}