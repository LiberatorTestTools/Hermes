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

package utilities

import java.io.{FileOutputStream, PrintWriter}
import java.util.concurrent.ConcurrentLinkedQueue

/**
  * A class representing a feeder file used in simulations
  */
//noinspection VarCouldBeVal
class FeederFile {

  /**
    * Writable lines for the feeder file
    */
  var writableLines: ConcurrentLinkedQueue[String] = new ConcurrentLinkedQueue[String]

  /**
    * The print writer representing the feeder file
    */
  var feederFile: PrintWriter = _

  /**
    * Creates a new feeder file
    *
    * @param name    The name to use for the feeder file
    * @param columns A comma separated list of columns
    * @return A representation of the feeder file as a print writer
    */
  def createWriter(name: String, columns: String): PrintWriter = {
    val fos = new FileOutputStream(name)
    feederFile = new PrintWriter(fos, true)
    writableLines.clear()
    writableLines.add(columns)
    feederFile
  }


  /**
    * Writes lines of text to a file
    * @param name Name of the file to create
    */
  def writeLinesToFile(name: String): Unit = {
    val fos = new FileOutputStream(name)
    feederFile = new PrintWriter(fos, true)
    val newLine = System.getProperty("line.separator")

    while (!writableLines.isEmpty) {
      feederFile.write(writableLines.poll() + newLine)
    }

    writableLines.clear()
    feederFile.close()
  }

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
}
