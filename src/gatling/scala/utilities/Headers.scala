package utilities

import scala.collection.mutable

/**
  * A base headers class to be inherited in user frameworks
  */
abstract class Headers {
  /**
    * The headers to be returned for a built header set
    */
  var headerMap:mutable.HashMap[String, String] = _

  /**
    * Adds a header to an existing header map
    * @param entityHeader The name of the header entity
    * @param entityValue The value of the header entity
    * @return The header map with the additional value
    */
  def addToHeaders(entityHeader:String, entityValue:String):mutable.HashMap[String, String] = {
    headerMap.put(entityHeader, entityValue)
    headerMap
  }

  /**
    * Concatenates two hash maps to create a header
    * @param headers The hash map to add to the base set
    * @return The resulting combined hash maps
    */
  def addToHeaders(headers:mutable.HashMap[String,String]):mutable.HashMap[String,String] = {
    for (header <- headers) {
      headerMap.put(header._1, header._2)
    }
    headerMap
  }
}
