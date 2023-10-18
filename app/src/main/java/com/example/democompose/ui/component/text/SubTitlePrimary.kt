package com.example.democompose.ui.component.text

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.democompose.ui.theme.subTitlePrimary

@Composable
fun SubTitlePrimary(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.subTitlePrimary
    )
}

@Composable
@Preview(showBackground = true, showSystemUi = false)
fun PreviewSubtitlePrimary() {
    SubTitlePrimary(text = "Demo SubtitlePrimary")
}
