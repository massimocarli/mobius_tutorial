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

package com.raywenderlich.android.raybius.mobius

import com.massimocarli.android.mobiustutorial.mobius.concepts.*
import com.massimocarli.android.mobiustutorial.mobius.model.CardGameModel
import com.massimocarli.android.mobiustutorial.mobius.model.CardState
import com.massimocarli.android.mobiustutorial.mobius.model.GameScreen
import com.massimocarli.android.mobiustutorial.mobius.model.PlayingCardModel
import com.spotify.mobius.Next

private fun createRandomValues() = List(20) {
  PlayingCardModel(it, it / 2, CardState.HIDDEN)
}.shuffled().toMutableList()

/** The logic for the CardGame app */
val cardGameLogic: CardGameUpdate = object : CardGameUpdate {
  override fun update(
    model: CardGameModel,
    event: CardGameEvent
  ): Next<CardGameModel, CardGameEffect> = when (event) {
    is FlipCard -> handleFlipCard(model, event)
    is ShowMenu -> handleShowMenu(model, event)
    is StartGame -> handleStartGame(model, event)
    is ShowCredits -> handleShowCredits(model, event)
    is EndGame -> handleEndGame(model, event)
    is BackPressed -> handleBack(model, event)
    else -> Next.noChange()
  }
}

fun handleFlipCard(model: CardGameModel, event: FlipCard): Next<CardGameModel,
    CardGameEffect> {
  var pos = 0
  while (model.board[pos].cardId != event.cardId) {
    pos++
  }
  // Here pos has the right position.
  val oldModel = model.board[pos]
  val newState =
    if (oldModel.state == CardState.HIDDEN) CardState.VISIBLE else CardState.HIDDEN
  val newModel = oldModel.copy(
    state = newState
  )
  model.board[pos] = newModel
  return Next.next(
    model.copy(
      moves = model.moves + 1
    )
  )
}

private fun handleShowMenu(model: CardGameModel, event: ShowMenu): Next<CardGameModel,
    CardGameEffect> {
  return Next.next(
    model.copy(
      screen = GameScreen.MENU
    )
  )
}

private fun handleStartGame(model: CardGameModel, event: StartGame): Next<CardGameModel,
    CardGameEffect> {
  return Next.next(
    model.copy(
      screen = GameScreen.BOARD,
      board = createRandomValues(),
      moves = 0
    )
  )
}

private fun handleShowCredits(model: CardGameModel, event: ShowCredits): Next<CardGameModel,
    CardGameEffect> {
  return Next.next(
    model.copy(
      screen = GameScreen.CREDITS,
    )
  )
}

private fun handleEndGame(model: CardGameModel, event: EndGame): Next<CardGameModel,
    CardGameEffect> {
  return Next.next(
    model.copy(
      screen = GameScreen.END
    )
  )
}

private fun handleBack(model: CardGameModel, event: BackPressed): Next<CardGameModel,
    CardGameEffect> =
  when (model.screen) {
    GameScreen.BOARD -> Next.noChange()// Display Alert
    GameScreen.MENU -> Next.noChange()// Close App
    else -> Next.next(model.copy(screen = GameScreen.MENU))
  }


