package DataClean

import DataDisplay.DataDisplay

/**
  * Created by alexandre on 08/06/16.
  * clean data entries
  */
object DataClean {


  /**
    * Check if the parameter is a valid CSV file
    *
    * @param filename : Path of the CSV file
    */
  def filenIsCSV(filename:String): Boolean={
    var ok = false
    if(filename.endsWith(".csv")) {
      ok = true
    }
    return ok
  }

  /**
    * Check if the parameter display exists in the list
    * @param list
    * @param display
    * @return
    */
  def displayExists(list:Map[String, DataDisplay], display:String) : Boolean={
    var ok = false
    if(list.contains(display)) {
      ok = true
    }
    return ok
  }

}
