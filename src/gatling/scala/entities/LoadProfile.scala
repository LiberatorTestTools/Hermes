package entities

import utilities.Environment

/**
  * A load profile is a series of settings that are used to create an Injector
  */
class LoadProfile {

  /**
    * The number of users to be passed to the injector
    */
  var usersAtOnce: Int  = _

  /**
    * The number of users to add during a ramp
    */
  var userIncrement: Int = _

  /**
    * The number of users during the peaks
    */
  var peakUsers: Int = _

  /**
    * The number of ramps to be used
    */
  var steps: Int = _

  /**
    * The duration of the constant load
    */
  var plateauDuration: Int = Environment.duration

  /**
    * The duration in seconds of each ramp
    */
  var rampDuration: Int = _

  /**
    * The duration in seconds of each peak
    */
  var peakDuration: Int = _

  /**
    * The number of requests expected in a minute
    */
  var requestsPerMinute:Int = _

  def this(requestsPerMinute: Double) = {
    this()
    this.usersAtOnce = 1
    this.userIncrement = 1
    this.peakUsers = 1
    this.steps = 1
    this.plateauDuration = Environment.duration
    this.rampDuration = Environment.duration
    this.peakDuration = Environment.duration
    this.requestsPerMinute = requestsPerMinute.toInt
  }

  /**
    * The constructor used to create a run once Gatling injector
    *
    * @param userCount The number of users to be passed to the injector
    */
  def this(userCount: Int) = {
    this()
    this.usersAtOnce = userCount
    this.userIncrement = 1
    this.peakUsers = 1
    this.steps = 1
    this.plateauDuration = Environment.duration
    this.rampDuration = Environment.duration
    this.peakDuration = Environment.duration
    this.requestsPerMinute = Environment.rpm.toInt
  }

  /**
    * The constructor used to create a constant load Gatling injector
    *
    * @param userCount The number of users to be passed to the injector
    * @param plateau   The duration of the constant load
    */
  def this(userCount: Int, plateau: Int) = {
    this()
    this.usersAtOnce = userCount
    this.userIncrement = 1
    this.peakUsers = 1
    this.steps = 1
    this.plateauDuration = plateau
    this.rampDuration = Environment.duration
    this.peakDuration = Environment.duration
    this.requestsPerMinute = Environment.rpm.toInt
  }

  /**
    * The constructor used to create a stepped loading gatling injector
    *
    * @param userCount  The number of users to be passed to the injector
    * @param increments The number of users to add during a ramp
    * @param stepCount  The number of ramps to be used
    * @param stepTime   The duration in seconds of each ramp
    */
  def this(userCount: Int, increments: Int, stepCount: Int, stepTime: Int) = {
    this()
    this.usersAtOnce = userCount
    this.userIncrement = increments
    this.peakUsers = 1
    this.steps = stepCount
    this.plateauDuration = stepTime
    this.rampDuration = stepTime
    this.peakDuration = Environment.duration
    this.requestsPerMinute = Environment.rpm.toInt
  }

  /**
    * The constructor used to create a peak loading Gatling injector
    *
    * @param userCount The number of users to be passed to the injector
    * @param peak      The number of users during the peaks
    * @param stepCount The number of ramps to be used
    * @param plateau   The duration of each constant load
    * @param peakTime  The duration in seconds of each peak
    */
  def this(userCount: Int, peak: Int, stepCount: Int, plateau: Int, peakTime: Int) = {
    this()
    this.usersAtOnce = userCount
    this.userIncrement = 1
    this.peakUsers = peak
    this.steps = stepCount
    this.plateauDuration = plateau
    this.rampDuration = Environment.duration
    this.peakDuration = peakTime
    this.requestsPerMinute = Environment.rpm.toInt
  }

  /**
    * The full Load Profile object for complex Gatling injectors
    *
    * @param userCount  The number of users to be passed to the injector
    * @param increments The number of users to add during a ramp
    * @param peak       The number of users during the peaks
    * @param stepCount  The number of ramps to be used
    * @param plateau    The duration of each constant load
    * @param rampTime   The duration in seconds of each ramp
    * @param peakTime   The duration in seconds of each peak
    */
  def this(userCount: Int, increments: Int, peak: Int, stepCount: Int,
           plateau: Int, rampTime: Int, peakTime: Int) = {
    this()
    this.usersAtOnce = userCount
    this.userIncrement = increments
    this.peakUsers = peak
    this.steps = stepCount
    this.plateauDuration = plateau
    this.rampDuration = rampTime
    this.peakDuration = peakTime
    this.requestsPerMinute = Environment.rpm.toInt
  }
}
