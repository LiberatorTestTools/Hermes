package simulations

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
      .baseURL(baseUrl)
      .headers(this.baseHeader.toMap)
      .disableWarmUp
      .extraInfoExtractor(extraInfo => List(println(extraInfo.request),extraInfo.response, extraInfo.session))
  }

  /**
    * Temporary method for header-less calls
    * @param service The service URL
    */
  def setServiceOnly(service:String): Unit ={
    this.baseUrl = service
    this.httpConfig = http
      .baseURL(baseUrl)
      .disableWarmUp
      .extraInfoExtractor(extraInfo => List(println(extraInfo.request),extraInfo.response, extraInfo.session))
  }

  /**
    * Sets the http protocol
    */
  var httpConfig:HttpProtocolBuilder = http
    .baseURL(baseUrl)
    .headers(this.baseHeader.toMap)
    .disableWarmUp
    .extraInfoExtractor(extraInfo => List(println(extraInfo.request),extraInfo.response, extraInfo.session))
}
