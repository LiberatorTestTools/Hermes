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
