package com.example.democompose.ui.component.text

import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.democompose.ui.theme.titleText

@Composable
fun TitleText(text:String) {
    Text(
        text = text,
        style = typography.titleText
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewBioText(){
    TitleText(text = "Demo BioText")
}