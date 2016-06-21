package DataCreation

import java.io.IOException

import scala.collection.JavaConverters._
import scala.io.Source


/**
  * Created by clem on 09/04/16.
  * Contains functions which generate data arrays
  * in order to test display functions
  */
object DataCreation {

  /**
    * Generate vector of random numbers
    *
    * @param nbData : Number of data generated
    * @param limit : Max number generated
    * @return : Vector of random numbers
    */
  def getData1D(nbData:Int, limit:Int): Array[Double]= {
    val r = scala.util.Random
    var i = 0
    val myData = Array.ofDim[Double](nbData)

    for(i <- 0 to nbData-1){
      myData(i) = r.nextDouble()*limit
    }

    return myData
  }

  /**
    * Generate a matrix with 2 lines and nbData columns with exponential form :
    * - Line 0 = random germ generated
    * - Line 1 = random number generated based on the germ
    *
    * @param nbData : Number of data generated
    * @return : Matrix with random numbers/germs
    */
  def getData2Dpow(nbData:Int): Array[Array[Double]]= {
    val germ = scala.util.Random
    val currentData = 3.14
    var myData = Array.ofDim[Double](2,nbData)
    var i = 0
    var currentGerm = 0.0

    for(i <- 0 to nbData-1){
      currentGerm = germ.nextDouble()

      myData(0)(i) = currentGerm
      myData(1)(i) = (currentGerm*currentGerm)*currentData
    }

    return myData
  }

  /**
    * Generate a matrix with 2 lines and nbData columns with linear form :
    * - Line 0 = random germ generated
    * - Line 1 = random number generated based on the germ
    *
    * @param nbData : Number of data generated
    * @return : Matrix with random numbers/germs
    */
  def getData2Dlinear(nbData:Int): Array[Array[Double]]= {
    val germ = scala.util.Random
    val currentData = 3.14
    var myData = Array.ofDim[Double](2,nbData)
    var i = 0
    var currentGerm = 0.0

    for(i <- 0 to nbData-1){
      currentGerm = germ.nextDouble()

      myData(0)(i) = currentGerm
      myData(1)(i) = currentGerm*currentData
    }

    return myData
  }

  /**
    * Generate a vector of vector with the following form:
    * [ [germ1, algo1, res1.1], [germ2, algo1, res1.2], ... [germN, algoN, resN.N] ]
    *
    * @return : Vector of vector generated
    */
  def getVectorOfVector():Vector[Vector[Any]]={
    val r = scala.util.Random
    var myData = Vector[Vector[Any]]()
    var i = 0
    var currentData = 0.0

    myData = add2DMatrix("Generation linéaire", 5, myData,getData2Dlinear)
    myData = add2DMatrix("Generation second ordre", 5, myData,getData2Dpow)

    return myData
  }

  /**
    * Generate a vector of vector with the following form:
    * [ [germ1, algo1, res1.1], [germ2, algo1, res1.2], ... [germN, algoN, resN.N] ]
    *
    * @param nameFile Path of the csv file
    * @return Vector of vector generated
    */
  def getVectorOfVectorFromCSV(nameFile:String): Vector[Vector[Any]] ={
    var myData = Vector[Vector[Any]]()
    try {
      val source = Source.fromFile(nameFile).getLines()

      val headerLine = source.take(1).next()

      for (line <- source) {
        val tuple = line.split(",")
        myData = myData :+ Vector(tuple(2).toDouble, tuple(1), tuple(0).toDouble)
      }
    }
    catch {
      case e: IOException => println("IO exception");
    }

    return myData
  }

  /**
    * Add a 2D matrix to a vector of vector
    *
    * @param nomAlgo : Name of the algorithm used to generate random numbers
    * @param nbData : Number of data generated
    * @param myData : Vector of vector[ [germ1, algo1, res1.1], [germ2, algo1, res1.2], ... [germN, algoN, resN.N] ]
    * @param callback : Function which generate nbData of random data
    * @return : The vecteur of vector with the new random data added
    */
  def add2DMatrix(nomAlgo:String, nbData:Int, myData:Vector[Vector[Any]], callback: (Int) => Array[Array[Double]]): Vector[Vector[Any]]={
    var i = 0
    val values = callback(nbData) //Get data
    var matrixData = myData

    for(i <- 0 to nbData-1){
      matrixData = matrixData :+ Vector(values(0)(i), nomAlgo, values(1)(i)) // Add a vector like [germ i, algoName, data i]
    }

    return matrixData
  }
}
