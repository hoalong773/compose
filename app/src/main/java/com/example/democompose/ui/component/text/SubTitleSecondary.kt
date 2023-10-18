package com.example.democompose.ui.component.text

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.democompose.ui.theme.subTitleSecondary

@Composable
fun SubTitleSecondary(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.subTitleSecondary
    )
}

@Composable
@Preview(showBackground = true, showSystemUi = false)
fun PreviewSubtitleSecondary() {
    SubTitleSecondary(text = "Demo SubtitleSecondary")
}