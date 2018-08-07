package utilities

import java.io.{FileOutputStream, PrintWriter}
import java.util.concurrent.ConcurrentLinkedQueue

/**
  * A class representing a feeder file used in simulations
  */
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
