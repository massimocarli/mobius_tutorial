/*
 * Copyright (c) 2022 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.massimocarli.android.mobiustutorial.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.massimocarli.android.mobiustutorial.R
import com.massimocarli.android.mobiustutorial.mobius.model.CardState
import com.massimocarli.android.mobiustutorial.mobius.model.PlayingCardModel

val BACK_COLOR = Color(36, 139, 40, 255)
val FRONT_COLOR = Color(36, 62, 139, 255)
val DONE_COLOR = Color(255, 235, 59, 255)

@ExperimentalMaterialApi
@Composable
fun PlayingCard(
  modifier: Modifier = Modifier,
  model: PlayingCardModel,
  onClick: (Int) -> Unit,
) {
  val angle = when (model.state) {
    CardState.HIDDEN -> 0f
    else -> 180f
  }
  val rotation = animateFloatAsState(
    targetValue = angle,
    animationSpec = tween(
      durationMillis = 400,
      easing = FastOutSlowInEasing,
    )
  )
  Card(
    onClick = { onClick(model.cardId) },
    modifier = modifier
      .graphicsLayer {
        rotationY = rotation.value
        cameraDistance = 12f * density
      }
      .size(80.dp, 100.dp)
      .clip(RoundedCornerShape(8.dp))
      .border(5.dp, Color.Black),
  ) {
    if (model.state == CardState.DONE) {
      DoneCard()
    } else if (rotation.value <= 90f) {
      BackCard()
    } else {
      FrontCard("${model.value}")
    }
  }
}

@Composable
fun BackCard() {
  Box(
    Modifier
      .background(BACK_COLOR)
      .fillMaxSize()
  ) {
    Image(
      painter = painterResource(R.drawable.splash_icon),
      contentDescription = null,
      modifier = Modifier
        .fillMaxSize()
    )
  }
}

@Composable
fun FrontCard(value: String) {
  Box(
    Modifier
      .background(FRONT_COLOR)
      .fillMaxSize()
      .graphicsLayer {
        rotationY = 180f
      },
  ) {
    Text(
      text = value,
      fontWeight = FontWeight.Bold,
      color = Color.White,
      fontSize = 40.sp,
      modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
    )
  }
}

@Composable
fun DoneCard() {
  Box(
    Modifier
      .background(DONE_COLOR)
      .fillMaxSize()
      .graphicsLayer {
        rotationY = 180f
      },
  ) {

  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun TestFlipCard() {
  var cardModel by remember {
    mutableStateOf(PlayingCardModel(1, 10))
  }
  PlayingCard(
    model = cardModel,
    onClick = {
      cardModel = cardModel.copy(
        state = if (cardModel.state == CardState.HIDDEN) CardState.VISIBLE else CardState.HIDDEN
      )
    },
  )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun TestDoneCard() {
  var cardModel by remember {
    mutableStateOf(PlayingCardModel(1, 10, state = CardState.DONE))
  }
  PlayingCard(
    model = cardModel,
    onClick = {
    },
  )
}