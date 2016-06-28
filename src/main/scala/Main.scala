package Main

import DataCreation.DataCreation
import DataDisplay.DataDisplay
import HistogramDisplay.HistogramDisplay
import GraphDisplay.GraphDisplay
import CloudOfDotsDisplay.CloudOfDotsDisplay
import DataClean.DataClean
import io.continuum.bokeh._

import scala.collection.immutable.HashMap
import scala.collection.mutable.ListBuffer

object Main extends App {
  if(args.length > 1) {

    val listOfDisplay = Map[String,DataDisplay]("histo" -> new HistogramDisplay(), "cloud" -> new CloudOfDotsDisplay())

    if(DataClean.filenIsCSV(args(0))) {
      if(DataClean.displayExists(listOfDisplay, args(1))) {
        val data = DataCreation.getVectorOfVectorFromCSV(args(0))
        val display = listOfDisplay.get(args(1))

        display.get.displayVectorOfVector(data)
      }
      else {
        println("Invalid display, write one of the following words :\nhisto, cloud")
      }
    }
    else {
      println("A CSV file is required.")
    }

  }
  else {
    println("> run [file.CSV] [display]\nWrite one of the following words for the display :\nhisto, cloud ")
  }
}

