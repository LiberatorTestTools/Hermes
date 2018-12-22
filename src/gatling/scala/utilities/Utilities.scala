package utilities

import java.security.MessageDigest

import scala.collection.immutable
/**
  * Additional utility methods
  */
object Utilities {

  /**
    * Builds an object from a JSON response
    * @param json The JSON blob to be converted to an object
    * @param cls The class of the object to be used
    * @return An object of the class specified with property values from the JSON blob
    */
  //TODO: json de-serialise
  def buildObjectFromJson(json:String, cls:Class[_]):Any = {

  }

  /**
    * Checks to see if the request has an empty header map
    * @param map The map of headers
    * @return A boolean stating whether the map is empty
    */
  def isEmptyMap(map:immutable.Map[String, String]): Boolean = { if(map.nonEmpty) { false } else { true } }

  /**
    * Tests a member of an array of strings for out of bounds elements
    *
    * @param index The index number to be checked
    * @param array The base array being checked
    * @return A string value
    */
  def getStringOrDefault(index: Int, array: Array[String]): String = {
    try {
      array(index)
    } catch {
      case _: ArrayIndexOutOfBoundsException =>
        ""
    }
  }


  /**
    * Creates an MD% digest from first principles
    * @param string to for which the digest is to be created
    * @return The digest
    */
  def md5HashString(string: String): String = {
    val hash: Array[Byte] = md5(string)
    val encoded = Base64.encodeBytes(hash)
    encoded
  }

  /**
    *
    * @param string
    * @return
    */
  def md5(string: String): Array[Byte] = {
    MessageDigest.getInstance("MD5").digest(string.getBytes)
  }

}