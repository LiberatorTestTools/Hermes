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

  /**
    * Sends a stable number of requests per minute from a single user
    * @param requestsPerMinute Number of requests per minute
    */
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
    * Sends a stable number of requests per minute from a specified number of users
    * @param requestsPerMinute Number of requests per minute
    * @param usersAtOnce The number of users to be passed to the injector
    */
  def this(requestsPerMinute: Double, usersAtOnce: Int) = {
    this()
    this.usersAtOnce = usersAtOnce
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
