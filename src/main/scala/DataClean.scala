package DataClean
/**
  * Created by alexandre on 08/06/16.
  */
object DataClean {

  /**
    * Vérifie que la donnée passée en paramètre est bien un fichier CSV
    *
    * @param filename : nom de fichier csv
    */
  def filenIsCSV(filename:String): Boolean={
    var ok = false
    if(filename.endsWith(".csv")) {
      ok = true
    }
    return ok
  }

}
