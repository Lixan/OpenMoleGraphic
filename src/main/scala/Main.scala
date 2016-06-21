package Main

import DataCreation.DataCreation
import DataDisplay.DataDisplay
import DataClean.DataClean

import io.continuum.bokeh._

import scala.collection.mutable.ListBuffer

object Main extends App {
  if(args.length > 0) {
    if(DataClean.filenIsCSV(args(0))) {
      val data = DataCreation.getVectorOfVectorFromCSV(args(0))
      DataDisplay.displayVectorOfVector(data)
    }
    else {
      println("Un fichier de type CSV est requis.")
    }

  }
  else {
    println("run filename")
  }
}

