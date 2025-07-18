package com.compose.base.common.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.compose.base.R

@Composable
fun SimpleAlertDialog(
    title: String? = null,
    content: String? = null,
    btnName: String = stringResource(R.string.action_close),
    btnColor: Color = MaterialTheme.colorScheme.primary,
    onDismiss: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false),
    ) {
        Card(
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(14.dp))
                .shadow(
                    elevation = 1.dp,
                    shape = RoundedCornerShape(14.dp),
                ),
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (title.isNullOrEmpty().not()) {
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        fontSize = 17.sp,
                        fontWeight = FontWeight(600),
                        color = Color.Black,
                    )
                    if (content != null) {
                        Spacer(Modifier.height(16.dp))
                    }
                }
                if (content.isNullOrEmpty().not()) {
                    Text(
                        text = content,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(400),
                        color = Color.Black,
                    )
                }
                Spacer(Modifier.height(24.dp))

                Button(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp),
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = btnColor,
                        contentColor = Color.White,
                    ),
                ) {
                    Text(
                        text = btnName,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W400,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = false, showSystemUi = false)
@Composable
fun PreviewLoadingView() {
    SimpleAlertDialog(title = "Title", content = "content")
}
