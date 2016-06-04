package DataCreation

import scala.collection.JavaConverters._
import scala.io.Source


/**
  * Created by clem on 09/04/16.
  * Contient des fonctions permettant de générer des tableaux de données
  * afin de tester les fonctions d'affichage
  */
object DataCreation {

  /**
    * Génère un vecteur de nombres aléatoires
    *
    * @param nbData : nombre de données générées
    * @param limit : entier maximum généré
    * @return : Vecteur de nombres aléatoires
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
    * Génère une matrice de 2 lignes et nbData colonnes de forme exponentielle :
    * - Ligne 0 = germes aléatoires générés
    * - Ligne 1 = nombres aléatoires générés en fonction des germes
    *
    * @param nbData : nombre de données générées
    * @return : matrice de nombres/germes aléatoires
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
    * Génère une matrice de 2 lignes et nbData colonnes de firle linéaire :
    * - Ligne 0 = germes aléatoires générés
    * - Ligne 1 = nombres aléatoires générés en fonction des germes
    *
    * @param nbData : nombre de données générées
    * @return : matrice de nombres/germes aléatoires
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
    * Génère un vecteur de vecteur du type
    * [ [germe1, algo1, res1.1], [germe2, algo1, res1.2], ... [germeN, algoN, resN.N] ]
    *
    * @return : vecteur de vecteur généré
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
    * A SUPPRIMER AVANT ENVOI
    * Vecteur de vecteur de test
    * @return
    */
  def getVectorOfVectorTest():Vector[Vector[Any]]={
    var myData = Vector[Vector[Any]]()
    myData = myData :+ Vector(1.0, "FIRST", 2.0)
    myData = myData :+ Vector(2.0, "FIRST", 3.0)
    myData = myData :+ Vector(3.0, "FIRST", 4.0)
    myData = myData :+ Vector(4.0, "SEC", 5.0)
    myData = myData :+ Vector(5.0, "SEC", 6.0)
    myData = myData :+ Vector(6.0, "SEC", 7.0)
    return myData
  }

  /**
    * Génère un vecteur de vecteur du type
    * [ [germe1, algo1, res1.1], [germe2, algo1, res1.2], ... [germeN, algoN, resN.N] ]
    *
    * @param nameFile nom du chemin du fichier CSV
    * @return vecteur de vecteur généré
    */
  def getVectorOfVectorFromCSV(nameFile:String): Vector[Vector[Any]] ={
    var myData = Vector[Vector[Any]]()
    val source = Source.fromFile(nameFile).getLines()

    val headerLine = source.take(1).next()

    for(line <- source){
      val tuple = line.split(",")
      println(tuple(0)+" "+tuple(1)+" "+tuple(2))
      myData = myData :+ Vector(tuple(2).toDouble, tuple(1), tuple(0).toDouble)
    }

    return myData
  }

  /**
    * Ajoute une matrice 2D à un vecteur de vecteur
    *
    * @param nomAlgo : nom de l'algo qui a été utilisé pour générer les nombres aléatoires
    * @param nbData : nombre de données générées
    * @param myData : vecteur de vecteur de type [ [germe1, algo1, res1.1], [germe2, algo1, res1.2], ... [germeN, algoN, resN.N] ]
    * @param callback : fonction générant nbData de données aléatoires
    * @return : le vecteur de vecteur comprenant en plus les nouvelles valeurs aléatoires ajoutées
    */
  def add2DMatrix(nomAlgo:String, nbData:Int, myData:Vector[Vector[Any]], callback: (Int) => Array[Array[Double]]): Vector[Vector[Any]]={
    var i = 0
    val values = callback(nbData) //Récupération des valeurs
    var matrixData = myData

    for(i <- 0 to nbData-1){
      matrixData = matrixData :+ Vector(values(0)(i), nomAlgo, values(1)(i)) // ajout d'un vecteur [germe i, nomAlgo, valeur i]
    }

    return matrixData
  }
}
