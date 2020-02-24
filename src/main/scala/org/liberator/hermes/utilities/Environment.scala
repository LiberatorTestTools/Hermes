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

package org.liberator.hermes.utilities

import io.gatling.core.controller.inject.open.OpenInjectionStep
import io.gatling.core.structure.PopulationBuilder

import scala.collection.mutable

/**
  * A singleton used to provide the framework with operational constants and global storage
  */
//noinspection VarCouldBeVal
object Environment {

  /**
    * The currently used list of injection steps which is used for a Gatling performance test
    */
  var injectionSteps: mutable.Iterable[OpenInjectionStep] = new mutable.Queue[OpenInjectionStep]
  var stepsCompiled: mutable.Iterable[OpenInjectionStep] = new mutable.Queue[OpenInjectionStep]

  /**
    * Base URL for the host for the requests
    */
  var baseUrl: String = if (!System.getProperty("host").isEmpty)
    System.getProperty("host") else "http://127.0.0.1"

  /**
    * Endpoint at the host for the request
    */
  var endpoint: String = if(!System.getProperty("endpoint").isEmpty)
    System.getProperty("endpoint") else "csv"

  /**
    * The ID of the client
    */
  var clientId: String = if(!System.getProperty("clientId").isEmpty)
    System.getProperty("clientId") else "100"

  /**
    * The expected status within the response
    */
  var expectedStatus: Int = if (!System.getProperty("expectedStatus").isEmpty)
    System.getProperty("expectedStatus").toInt else 200

  /**
    * Duration for the entire test run
    */
  var duration:Int = if(!System.getProperty("duration").isEmpty)
    System.getProperty("duration").toInt else 60

  /**
    * Number of requests per minute
    */
  var rpm:Double = if(!System.getProperty("rpm").isEmpty)
    System.getProperty("rpm").toDouble else 60.0

  /**
    * How many users to access the enpoint at once
    */
  var atOnce: Int = if (!System.getProperty("atOnce").isEmpty)
    System.getProperty("atOnce").toInt else 1

  /**
    * The expected response content type
    */
  var accept:String =
    if(!System.getProperty("accept").isEmpty) System.getProperty("accept")
  else if (System.getProperty("accept").contains("csv")) "text/csv"
  else if (System.getProperty("accept").contains("xml")) "text/xml"
  else "application/json"

  /**
    * Number of requests required
    */
  val requestsRequired: Int = ((duration / 60) * rpm.toLong).toInt

  /**
    * The length of pause to use
    */
  val pauseLength: Int = duration / requestsRequired

  /**
    * The stack of population builder requests
    */
  var requestStack: mutable.ListBuffer[PopulationBuilder] = new mutable.ListBuffer[PopulationBuilder]
}
