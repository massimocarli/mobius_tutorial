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

package com.massimocarli.android.mobiustutorial.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.massimocarli.android.mobiustutorial.mobius.concepts.CardGameEvent
import com.massimocarli.android.mobiustutorial.mobius.concepts.FlipCard
import com.massimocarli.android.mobiustutorial.mobius.model.CardGameModel
import com.massimocarli.android.mobiustutorial.ui.components.PlayingCard
import com.spotify.mobius.functions.Consumer

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun GameBoard(model: CardGameModel, eventConsumer: Consumer<CardGameEvent>) {
  LazyVerticalGrid(
    cells = GridCells.Fixed(4)
  ) {
    items(model.board.size) {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        PlayingCard(model = model.board[it]) { cardId ->
          eventConsumer.accept(FlipCard(cardId))
        }
      }
    }
  }
//  Column {
//    PlayingCard(model = PlayingCardModel(1, 1)) {
//      eventConsumer.accept(FlipCard(1))
//    }
//    PlayingCard(model = PlayingCardModel(2, 2)) {
//      eventConsumer.accept(FlipCard(2))
//    }
//    PlayingCard(model = PlayingCardModel(3, 3)) {
//      eventConsumer.accept(FlipCard(3))
//    }
//    PlayingCard(model = PlayingCardModel(4, 4)) {
//      eventConsumer.accept(FlipCard(4))
//    }
//    PlayingCard(model = PlayingCardModel(5, 5)) {
//      eventConsumer.accept(FlipCard(5))
//    }
//  }
}