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

package com.massimocarli.android.mobiustutorial.di

import com.massimocarli.android.mobiustutorial.mobius.concepts.CardGameEffect
import com.massimocarli.android.mobiustutorial.mobius.concepts.CardGameEvent
import com.massimocarli.android.mobiustutorial.mobius.concepts.CardGameModel
import com.raywenderlich.android.raybius.mobius.*
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.android.AndroidLogger
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.rx2.RxEventSources
import com.spotify.mobius.rx2.RxMobius
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.reactivex.Observable

@Module(includes = arrayOf(MobiusModule.Binders::class))
@InstallIn(ActivityComponent::class)
class MobiusModule {

  @Module
  @InstallIn(ActivityComponent::class)
  interface Binders {

    /*
    @Binds
    fun bindUIEffectHandler(impl: UIEffectHandlerImpl): UIEffectHandler

    @Binds
    fun bindApiRequestHandler(impl: ApiRequestHandlerImpl): ApiRequestHandler
     */
  }

  @Provides
  fun provideLogic(): CardGameUpdate = cardGameLogic

  @Provides
  fun provideEffectHandler(
    //uiHandler: CardGameEffectHandler,
    //apiRequestHandler: ApiRequestHandler
  ): CardGameEffectHandler =
    RxMobius.subtypeEffectHandler<CardGameEffect, CardGameEvent>()
      //.addTransformer(SearchTvShow::class.java, apiRequestHandler::handleSearchTvShow)
      //.addTransformer(GetTvShowDetail::class.java, apiRequestHandler::handleTvShowDetail)
      //.addTransformer(NavigateToDetail::class.java, uiHandler::handleNavigateToDetail)
      /*
    .addConsumer(
      DisplayErrorMessage::class.java, uiHandler::handleErrorMessage,
      AndroidSchedulers.mainThread()
    )
    .addConsumer(
      HideKeyboard::class.java, uiHandler::handleHideKeyboardMessage,
      AndroidSchedulers.mainThread()
    )
       */
      .build();

  @Provides
  fun provideEventSource(): CardGameEventSource {
    //val dummy: Observable<TvShowEvent> = Observable.just(StartEvent)
    val dummy: Observable<CardGameEvent> = Observable.empty()
    return RxEventSources.fromObservables(dummy);
  }

  @Provides
  fun provideMobiusLoopFactory(
    logic: CardGameUpdate, effectHandler: CardGameEffectHandler,
    eventSource: CardGameEventSource
  ): MobiusLoop
  .Factory<CardGameModel, CardGameEvent,
      CardGameEffect> =
    RxMobius.loop(logic, effectHandler)
      .eventSource(eventSource)
      .logger(AndroidLogger.tag("Raybious"))

  @Provides
  fun provideMobiusController(factory: CardGameMobiusLoopFactory): CardGameMobiusController =
    MobiusAndroid.controller(factory, CardGameModel())
}