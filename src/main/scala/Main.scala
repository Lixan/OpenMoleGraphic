package Main

import DataCreation.DataCreation
import DataDisplay.DataDisplay
import HistogramDisplay.HistogramDisplay
import GraphDisplay.GraphDisplay
import CloudOfDotsDisplay.CloudOfDotsDisplay
import DataClean.DataClean
import io.continuum.bokeh._

import scala.collection.mutable.ListBuffer

object Main extends App {
  if(args.length > 0) {
    if(DataClean.filenIsCSV(args(0))) {
      val data = DataCreation.getVectorOfVectorFromCSV(args(0))

      val dataH = new HistogramDisplay()
      val dataC = new CloudOfDotsDisplay()
      val dataG = new GraphDisplay()

      /*dataH.displayVectorOfVector(data)*/
      dataC.displayVectorOfVector(data)
    }
    else {
      println("A CSV file is required.")
    }

  }
  else {
    println("run filename")
  }
}

