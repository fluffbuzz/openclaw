package com.fluffbuzz.android.ui

import androidx.compose.runtime.Composable
import com.fluffbuzz.android.MainViewModel
import com.fluffbuzz.android.ui.chat.ChatSheetContent

@Composable
fun ChatSheet(viewModel: MainViewModel) {
  ChatSheetContent(viewModel = viewModel)
}
