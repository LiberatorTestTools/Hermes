package utilities

import scala.collection.{immutable, mutable}

//import play.api.libs.json.Json

/**
  * Additional utility methods
  */
object Utilities {
/*
  /**
    * Builds an object from a JSON response
    * @param json The JSON blob to be converted to an object
    * @param cls The class of the object to be used
    * @return An object of the class specified with property values from the JSON blob
    */
  def buildObjectFromJson(json:String, cls:Class[_]):Any = {
    val obj = Json.fromJson(Json.toJson(json))
    obj
  }*/

  /**
    * Checks to see if the request has an empty header map
    * @param map The map of headers
    * @return A boolean stating whether the map is empty
    */
  def isEmptyMap(map:immutable.Map[String, String]): Boolean = { if(map.nonEmpty) { false } else { true } }
}