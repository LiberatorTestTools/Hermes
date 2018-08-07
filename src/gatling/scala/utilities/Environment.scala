package utilities

import io.gatling.core.controller.inject.InjectionStep

import scala.collection.mutable

/**
  * A singleton used to provide the framework with operational constants and global storage
  */
object Environment {

  /**
    * The currently used list of injection steps which is used for a Gatling performance test
    */
  var injectionSteps: mutable.Iterable[InjectionStep] = new mutable.Queue[InjectionStep]
  var stepsCompiled: mutable.Iterable[InjectionStep] = new mutable.Queue[InjectionStep]

  var baseUrl: String = if (!System.getProperty("host").isEmpty)
    System.getProperty("host") else "http://127.0.0.1"

  var endpoint: String = if(!System.getProperty("endpoint").isEmpty)
    System.getProperty("endpoint") else "csv"

  var clientId: String = if(!System.getProperty("clientId").isEmpty)
    System.getProperty("clientId") else "100"

  var expectedStatus: Int = if (!System.getProperty("expectedStatus").isEmpty)
    System.getProperty("expectedStatus").toInt else 200

  var duration:Int = if(!System.getProperty("duration").isEmpty)
    System.getProperty("duration").toInt else 60

  var rpm:Double = if(!System.getProperty("rpm").isEmpty)
    System.getProperty("rpm").toDouble else 60.0

  var accept:String =
    if(!System.getProperty("accept").isEmpty) System.getProperty("accept")
  else if (System.getProperty("accept").contains("csv")) "text/csv"
  else if (System.getProperty("accept").contains("xml")) "text/xml"
  else "application/json"

  val requestsRequired: Int = ((duration / 60) * rpm.toLong).toInt

  val pauseLength: Int = duration / requestsRequired
}
