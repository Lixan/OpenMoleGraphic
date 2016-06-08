package Main

import DataCreation.DataCreation
import DataDisplay.DataDisplay
import DataClean.DataClean

import io.continuum.bokeh._

import scala.collection.mutable.ListBuffer

object Main extends App {
  //source.displayVector1D(DataCreation.getData1D(100,10))
  //source.displayMatrix2D(DataCreation.getData2Dpow(100))
  //val data = DataCreation.getVectorOfVector()
  //val data2 = DataCreation.getVectorOfVectorTest()
  //source.displayVectorOfVector(data2)
  ///home/alexandre/Documents/cours/ZZ1/projet/bokeh/test.csv


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
    println("run --CSV filename")
  }

}

