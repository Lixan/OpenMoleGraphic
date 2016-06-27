package DataClean
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

}
