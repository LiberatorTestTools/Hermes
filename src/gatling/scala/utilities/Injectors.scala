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
    * Builds the injector for a constant load Gatling test
    *
    * @return A Gatling injector statement
    */
  def constantLoading(): mutable.Iterable[InjectionStep] = {
    atOnce(loadProfile.usersAtOnce)
    rateInjection(loadProfile.usersAtOnce, loadProfile.plateauDuration)
    Environment.injectionSteps
  }


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
