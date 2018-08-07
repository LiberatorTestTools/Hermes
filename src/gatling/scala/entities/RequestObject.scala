package entities

import scala.collection.mutable

/**
  * A request object
  */
class RequestObject {

  /**
    * Constructor for a Request Object
    * @param description The description to post with the request through Gatling
    * @param target The target endpoint for the request
    * @param headerMap The headers to be used for the request
    * @param expectedStatus The expected status returned in the response
    */
  def this(description:String, target:String, headerMap:mutable.HashMap[String, String], expectedStatus:Int) {
    this()
    this.message = description
    this.endpoint = target
    this.headers = headerMap
    this.status = expectedStatus
  }

  /**
    * Constructor for a Request Object
    * @param description The description to post with the request through Gatling
    * @param target The target endpoint for the request
    * @param headerMap The headers to be used for the request
    * @param requestBody The body of the request in string format
    * @param expectedStatus The expected status returned in the response
    */
  def this(description:String, target:String, headerMap:mutable.HashMap[String, String], requestBody:String ,expectedStatus:Int) {
    this()
    this.message = description
    this.endpoint = target
    this.headers = headerMap
    this.body = requestBody
    this.status = expectedStatus
  }

  /**
    * Constructor for a Request Object
    * @param description The description to post with the request through Gatling
    * @param target The target endpoint for the request
    * @param headerMap The headers to be used for the request
    * @param requestBody The body of the request in string format
    * @param expectedStatus The expected status returned in the response
    * @param passedObject A copy of an object containing data required within the scenario
    */
  def this(description:String, target:String, headerMap:mutable.HashMap[String, String], requestBody:String ,expectedStatus:Int, passedObject:InfoObject) {
    this()
    this.message = description
    this.endpoint = target
    this.headers = headerMap
    this.body = requestBody
    this.status = expectedStatus
    this.dataObject = passedObject
  }

  /**
    * The message text to pass for Gatling to report
    */
  var message:String = _

  /**
    * The endpoint to send the request to
    */
  var endpoint:String = _

  /**
    * A mutable Hash Map containing the headers for the request
    */
  var headers:mutable.HashMap[String,String] = new mutable.HashMap[String, String]

  /**
    * The body of the request in string format
    */
  var body:String = _

  /**
    * The expected status in the response
    */
  var status:Int = 200

  /**
    * A data object, passed to the Scenario, to allow reference to base data
    */
  var dataObject:InfoObject = _
}
