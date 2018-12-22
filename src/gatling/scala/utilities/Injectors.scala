/*
 * Copyright (c) 2018 Totally Ratted Ltd T/A Totally Ratted Developments
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package utilities

import entities.LoadProfile
import io.gatling.core.Predef._
import io.gatling.core.controller.inject._

import scala.collection.mutable
import scala.concurrent.duration._

/**
  * An abstract base class for Gatling injectors
  *
  * @param loadProfile The load profile to be used
  */
abstract class Injectors(loadProfile: LoadProfile) {

  //region Step Builders

  /**
    * Adds a pause between injections
    * @param secs The number of seconds to pause
    * @return An injection step representing a pause
    */
  def pauseFor(secs:Int):InjectionStep = {
    val pause = nothingFor(secs)
    Environment.injectionSteps ++= Set(pause)
    pause
  }

  /**
    * Builds a Gatling injector step for atOnceUsers
    *
    * @param userCount The number of At Once Users
    * @return An injection step
    */
  def atOnce(userCount: Int): InjectionStep = {
    val numberOfUsers: InjectionStep = atOnceUsers(userCount)
    Environment.injectionSteps ++= Set(numberOfUsers)
    numberOfUsers
  }

  /**
    * Builds a Gatling injector step for Constant Users Per Second
    *
    * @param userCount The number of At Once Users
    * @param duration  The duration of any constant load
    * @return An injection step
    */
  def rateInjection(userCount: Double, duration: Int): InjectionStep = {
    val rate: InjectionStep = {
      constantUsersPerSec(userCount) during (duration seconds)
    }
    Environment.injectionSteps ++= Seq(rate)
    rate
  }

  /**
    * Builds a Gatling injector step for Ramp Users Per Second
    *
    * @param userStart    The number of Users at the start of the ramp
    * @param userEnd      The number of users at the end of the ramp
    * @param rampDuration The duration of the ramp
    * @return An injection step
    */
  def rampUsers(userStart: Int, userEnd: Int, rampDuration: Int): InjectionStep = {
    val ramp: InjectionStep = {
      rampUsersPerSec(userStart) to userEnd during rampDuration
    }
    Environment.injectionSteps ++= Seq(ramp)
    ramp
  }


  //endregion

  //region Injections

  /**
    * Builds the injector for a single shot Gatling test
    *
    * @return A Gatling injector statement
    */
  def runOnce(): mutable.Iterable[InjectionStep] = {
    atOnce(loadProfile.usersAtOnce)
    Environment.injectionSteps
  }


  /**
    * Builds the injector for a single shot Gatling test, preceded by a pause
    * @param secs
    * @return
    */
  def runOnceWithPause(secs:Int): mutable.Iterable[InjectionStep] = {
    pauseFor(secs)
    atOnce(loadProfile.usersAtOnce)
    Environment.injectionSteps
  }

  /**
    * Builds the injector for a constant load Gatling test
    *
    * @return A Gatling injector statement
    */
  def constantLoading(): mutable.Iterable[InjectionStep] = {
    atOnce(loadProfile.usersAtOnce)
    rateInjection(loadProfile.usersAtOnce, loadProfile.plateauDuration)
    Environment.injectionSteps
  }

  /**
    * Builds the injector for a set number of users per minute in a Gatling test
    * @return A Gatling injector statement
    */
  def usersPerMinute: mutable.Iterable[InjectionStep] = {
    atOnce(loadProfile.usersAtOnce)
    rateInjection(Environment.rpm / 60, loadProfile.plateauDuration)
    Environment.injectionSteps
  }

  /**
    * Builds the injector for a stepped load Gatling test
    *
    * @return A Gatling injector statement
    */
  def steppedLoading(): mutable.Iterable[InjectionStep] = {
    var _users = loadProfile.usersAtOnce
    atOnce(_users)

    for (_ <- 1 to loadProfile.steps) {
      rateInjection(_users, loadProfile.plateauDuration)
      rampUsers(_users, _users + loadProfile.userIncrement, loadProfile.rampDuration)
      _users = _users + loadProfile.userIncrement
    }

    Environment.injectionSteps
  }

  /**
    * Builds the injector for a peak load Gatling test
    *
    * @return A Gatling injector statement
    */
  def peakLoading(): mutable.Iterable[InjectionStep] = {
    atOnce(loadProfile.usersAtOnce)

    for (_ <- 1 to loadProfile.steps) {
      rateInjection(loadProfile.usersAtOnce, loadProfile.plateauDuration)
      rateInjection(loadProfile.peakUsers, loadProfile.peakDuration)
      rateInjection(loadProfile.usersAtOnce, loadProfile.plateauDuration)
    }

    Environment.injectionSteps
  }

  /**
    * Builds the injector for a regular simulation of a "daily load" profile (e.g. non-real world)
    * This profile builds from the no users to the highest constant load at the halfway point
    * before reducing back to zero at the end. During this period, peak loads are interspersed
    *
    * @return A Gatling injector statement
    */
  def dailyProfile(): mutable.Iterable[InjectionStep] = {
    var _users: Int = loadProfile.usersAtOnce
    var _endUsers: Int = loadProfile.usersAtOnce
    atOnce(_users)
    rampUsers(0, _users, loadProfile.rampDuration)

    for (_ <- 1 to loadProfile.steps) {
      _endUsers = _users + loadProfile.userIncrement
      rateInjection(_users, loadProfile.plateauDuration)
      rampUsers(_users, _endUsers, loadProfile.rampDuration)
      rateInjection(_endUsers, loadProfile.plateauDuration)
      rateInjection(loadProfile.peakUsers, loadProfile.peakDuration)
    }

    for (_ <- 1 to loadProfile.steps) {
      _users = _endUsers - loadProfile.userIncrement
      rateInjection(_endUsers, loadProfile.plateauDuration)
      rampUsers(_endUsers, _users, loadProfile.rampDuration)
      rateInjection(_users, loadProfile.plateauDuration)
      rateInjection(loadProfile.peakUsers, loadProfile.peakDuration)
    }

    rampUsers(_users, 0, loadProfile.rampDuration)
    Environment.injectionSteps
  }

  //endregion
}
