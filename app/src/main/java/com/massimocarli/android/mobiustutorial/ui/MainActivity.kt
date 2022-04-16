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

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import com.massimocarli.android.mobiustutorial.R
import com.massimocarli.android.mobiustutorial.mobius.concepts.CardGameEvent
import com.massimocarli.android.mobiustutorial.mobius.model.CardGameModel
import com.raywenderlich.android.composelab1.ui.theme.MobiusGameTheme
import com.raywenderlich.android.raybius.mobius.CardGameMobiusController
import com.spotify.mobius.Connection
import com.spotify.mobius.functions.Consumer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Main Screen
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  @Inject
  lateinit var gameCardController: CardGameMobiusController

  private var gameModel = mutableStateOf(CardGameModel())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // Switch to AppTheme for displaying the activity
    setTheme(R.style.AppTheme)
    gameCardController.connect(::connectViews)
    setContent {
      MobiusGameTheme {
        MainScreen(gameModel.value, eventConsumer)
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    gameCardController.disconnect()
  }

  override fun onResume() {
    super.onResume()
    gameCardController.start()
  }

  override fun onPause() {
    super.onPause()
    gameCardController.stop()
  }

  lateinit var eventConsumer: Consumer<CardGameEvent>

  private fun connectViews(eventConsumer: Consumer<CardGameEvent>): Connection<CardGameModel> {
    this.eventConsumer = eventConsumer
    return object : Connection<CardGameModel> {
      override fun accept(model: CardGameModel) {
        logic(eventConsumer, model)
      }

      override fun dispose() {
      }
    }
  }

  var logic: (Consumer<CardGameEvent>, CardGameModel) -> Unit = { _, cardModel ->
    gameModel.value = cardModel
  }
}
