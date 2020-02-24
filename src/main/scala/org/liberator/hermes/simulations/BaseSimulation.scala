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

package org.liberator.hermes.simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.collection.mutable

/**
  * The abstract call providing a base simulation for performance testing
  */
abstract class BaseSimulation extends Simulation {
  /**
    * The base URL to be used by the HTTP Protocol Builder
    */
  var baseUrl: String = _

  /**
    * The base header to be used for all calls to the service
    */
  var baseHeader: mutable.Map[String, String] = new mutable.HashMap[String, String]

  /**
    * Sets the base variables for the simulation
    * @param service The service's base url
    * @param header The base header for calls to the service
    */
  def setVariables(service:String, header:mutable.Map[String,String]): Unit ={
    this.baseUrl = service
    this.baseHeader = header
    this.httpConfig = http
      .baseUrl(baseUrl)
      .headers(this.baseHeader.toMap)
      .disableWarmUp
  }

  /**
    * Temporary method for header-less calls
    * @param service The service URL
    */
  def setServiceOnly(service:String): Unit ={
    this.baseUrl = service
    this.httpConfig = http
      .baseUrl(baseUrl)
      .disableWarmUp
  }

  /**
    * Sets the http protocol
    */
  var httpConfig:HttpProtocolBuilder = http
    .baseUrl(baseUrl)
    .headers(this.baseHeader.toMap)
    .disableWarmUp
}
