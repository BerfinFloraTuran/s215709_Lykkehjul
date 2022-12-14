package com.example.a215709_lykkehjul.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.a215709_lykkehjul.R
import com.example.a215709_lykkehjul.viewModel.FrontpageViewModel

@Composable
fun RulePage( viewModel: FrontpageViewModel, navController : NavController){
    val backgroundColor = "#fff8f6"


    Scaffold(
        backgroundColor = Color(backgroundColor.toColorInt()),
        content = {  paddingValues -> RulePageContent(viewModel, modifier = Modifier.padding(paddingValues), navController) }
    )
}

@Composable
fun RulePageContent(viewModel: FrontpageViewModel, modifier: Modifier, navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            stringResource(id = R.string.guide_title),
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text =  stringResource(id = R.string.rule_intro),
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp,
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = R.string.dropdownText),
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp,
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(id = R.drawable.dropdown), contentDescription = "",
            modifier = Modifier.size(230.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.shuffleText),
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp,
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Icon(
            painter = painterResource(id = R.drawable.shuffleicon), contentDescription = "",
            Modifier
                .size(50.dp)
                .padding(bottom = 15.dp)
        )
        Text(
            text = stringResource(id = R.string.howToWin),
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp,
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.howToLose),
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp,
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            Modifier
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .width(50.dp)
                .height(30.dp), contentAlignment = Alignment.CenterEnd
        ) {
            Row {
                Text(
                    text = "0",
                    modifier = Modifier.padding(top = 2.dp, end = 5.dp)
                )
                Icon(
                    imageVector = Icons.Outlined.Favorite,
                    contentDescription = "",
                    tint = Color.Red, modifier = Modifier
                        .padding(end = 3.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

            val buttonColor = "#FFCCB8"
            Box(contentAlignment = Alignment.Center) {
                Button(
                    onClick = {
                        viewModel.resetStates()
                        viewModel.randomCategory()
                        viewModel.resetGuessedLetters()
                        navController.navigate(Screen.GamePage.route)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(buttonColor.toColorInt())),
                    modifier = Modifier
                        .size(170.dp, 40.dp)
                ) {
                    Text(
                        stringResource(id = R.string.start_game),
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
