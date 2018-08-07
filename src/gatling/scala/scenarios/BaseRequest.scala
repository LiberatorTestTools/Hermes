package scenarios

import entities.RequestObject
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder._
import utilities.{CallType, Utilities}
import utilities.concord.CallType.CallType

import scala.collection._

/**
  * The base request object, which is to be extended
  * @param request The Request Object passed by the Simulation
  */
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
    if(!request.body.isEmpty) { requestVerb.body(StringBody(request.body)) }
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
