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

package org.liberator.hermes.scenarios

import org.liberator.hermes.entities._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder._
import org.liberator.hermes.utilities.CallType.CallType
import org.liberator.hermes.utilities.{CallType, Utilities}

import scala.collection._

/**
  * The base request object, which is to be extended
  * @param request The Request Object passed by the Simulation
  */
//noinspection VarCouldBeVal
abstract class BaseRequest(request: RequestObject) {

  /**
    * Converts the passed mutable header to an immutable for use in the class
    */
  var immutableHeader: immutable.Map[String, String] = new mutable.HashMap[String, String].toMap

  /**
    * Sets the header, ensuring that a null header does not cause an error
    */
  var setHeader : Any =  if(!Utilities.isEmptyMap(immutableHeader)) { immutableHeader = request.headers.toMap }

  /**
    * Message to be reported in the console
    */
  var httpMessage: Http = http(request.message)

  /**
    * The body of the request
    */
  var reqBody: String = request.body

  /**
    * Builds an HTTP request based on a Call Type
    * @param callType A request type chosen from the Call Type enumeration
    * @return The HTTP Request Builder
    */
  def makeRequest(callType:CallType): HttpRequestBuilder = {

    val requestType: HttpRequestBuilder = callType match {
      case CallType.Delete => deleteRequest(request)
      case CallType.Get => getRequest(request)
      case CallType.Head => headRequest(request)
      case CallType.Option => optionRequest(request)
      case CallType.Patch => patchRequest(request)
      case CallType.Post => postRequest(request)
      case CallType.Put => putRequest(request)
    }

    var requestVerb: HttpRequestBuilder = requestType
    if(!Utilities.isEmptyMap(immutableHeader)){ requestVerb = requestType.headers(immutableHeader) }
    if(!request.body.isEmpty) { requestVerb.body(StringBody(reqBody)) }
    requestVerb.check(status.is(request.status))
  }

  /**
    * Builds a GET request
    * @param request The request object
    * @return An HttpRequestBuilder to be used in simulations
    */
  def getRequest(request:RequestObject):HttpRequestBuilder = {
      httpMessage.get(request.endpoint)
  }

  /**
    * Builds a POST request
    * @param requestObject The request object
    * @return An HttpRequestBuilder to be used in simulations
    */
  def postRequest(requestObject: RequestObject):HttpRequestBuilder = {
    httpMessage.post(request.endpoint)
  }

  /**
    * Builds a PUT request
    * @param requestObject The request object
    * @return An HttpRequestBuilder to be used in simulations
    */
  def putRequest(requestObject: RequestObject):HttpRequestBuilder = {
    httpMessage.put(request.endpoint)
  }

  /**
    * Builds a PATCH request
    * @param requestObject The request object
    * @return An HttpRequestBuilder to be used in simulations
    */
  def patchRequest(requestObject: RequestObject):HttpRequestBuilder = {
    httpMessage.patch(request.endpoint)
  }

  /**
    * Builds a DELETE request
    * @param requestObject The request object
    * @return An HttpRequestBuilder to be used in simulations
    */
  def deleteRequest(requestObject: RequestObject):HttpRequestBuilder = {
    httpMessage.delete(request.endpoint)
  }

  /**
    * Builds a OPTION request
    * @param requestObject The request object
    * @return An HttpRequestBuilder to be used in simulations
    */
  def optionRequest(requestObject: RequestObject):HttpRequestBuilder = {
    httpMessage.options(request.endpoint)
  }

  /**
    * Builds a HEAD request
    * @param requestObject The request object
    * @return An HttpRequestBuilder to be used in simulations
    */
  def headRequest(requestObject: RequestObject):HttpRequestBuilder = {
    httpMessage.head(request.endpoint)
  }
}
