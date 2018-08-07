package simulations

import scala.collection.mutable

/**
  * The abstract call providing a base simulation with a feeder file
  */
abstract class FedSimulation extends BaseSimulation {

  /**
    * Abstract call to be overridden allowing the loading of data from a feeder
    * @param filename The name of the file to be used
    * @return A mutable list of the InfoObject types to be used
    */
  def readFeederFile(filename:String): mutable.MutableList[_]
}
