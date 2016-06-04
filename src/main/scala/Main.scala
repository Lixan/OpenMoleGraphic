package Main

import DataCreation.DataCreation
import io.continuum.bokeh._

import scala.collection.mutable.ListBuffer

object Main extends App {
  //source.displayVector1D(DataCreation.getData1D(100,10))
  //source.displayMatrix2D(DataCreation.getData2Dpow(100))

  val data = DataCreation.getVectorOfVector()
  val data2 = DataCreation.getVectorOfVectorTest()
  //val data = DataCreation.getVectorOfVectorFromCSV("/home/alexandre/Documents/cours/ZZ1/projet/bokeh/test.csv")
  source.displayVectorOfVector(data)
  source.displayVectorOfVector(data2)
}

object source extends ColumnDataSource with Tools{


  /**
    * Initialise et affiche un graphique 1 dimension à partir de données présentes dans un vecteur
    *
    * @param myData : vecteur de données
    */
  def displayVector1D(myData:Array[Double]): Unit={

    val plot = initializePlot()
    val x = column(myData)

    val xaxis = new LinearAxis().plot(plot).location(Location.Below)
    plot.below <<= (xaxis :: _)

    val glyph = new Circle().x(x).y(0.1).size(5).fill_color(Color.Red).line_color(Color.Black)
    val circle = new GlyphRenderer().data_source(source).glyph(glyph)

    plot.renderers := List(xaxis, circle)

    webDisplay(plot)
  }

  /**
    * Initialise et affiche un graphique 2 dimensions à partir de données présentes dans une matrice
    *
    * @param myData : matrice de données (2 lignes et N colonnes)
    */
  def displayMatrix2D(myData:Array[Array[Double]]): Unit={

    val plot = initializePlot()
    val x = column(myData(0)) //données aléatoires
    val y = column(myData(1)) //germes aléatoires

    val xaxis = new LinearAxis().plot(plot).location(Location.Below)
    val yaxis = new LinearAxis().plot(plot).location(Location.Left)
    plot.below <<= (xaxis :: _)
    plot.left <<= (yaxis :: _)

    val glyph = new Circle().x(x).y(y).size(5).fill_color(Color.Red).line_color(Color.Black)

    val circle = new GlyphRenderer().data_source(source).glyph(glyph)

    plot.renderers := List(xaxis, yaxis, circle)

    webDisplay(plot)
  }

  /**
    * Affiche un vecteur de vecteur du type [ [germe1, algo1, res1.1], [germe2, algo1, res1.2], ... [germeN, algoN, resN.N] ]
    *
    * @param myData : le vecteur de vecteur à afficher
    */
  def displayVectorOfVector(myData:Vector[Vector[Any]]): Unit={

    /** Initialisation du graphique **/
    val plot = initializePlot()
    val xaxis = new LinearAxis().plot(plot).location(Location.Below).axis_label("Résultats aléatoires")
    val yaxis = new LinearAxis().plot(plot).location(Location.Left).axis_label("Germes aléatoires")

    plot.below <<= (xaxis :: _)
    plot.left <<= (yaxis :: _)

    var listRenderers = List(xaxis, yaxis)
    plot.renderers :=  listRenderers

    /** Récupération des données **/
    val colors = Array[Color](Color.Red,Color.Blue,Color.Green,Color.Yellow,Color.Orange,Color.Brown,
      Color.Black, Color.Magenta, Color.Pink, Color.Purple) //Ajouter des couleurs si beaucoup de courbes à afficher
    var indicColor = 0 //index du tableau color pour savoir quelle couleur afficher pour une courbe
    var nomAlgo = ""
    var glyph = new Circle()
    var circle = new GlyphRenderer()
    var leg = ListBuffer.empty[(String,List[GlyphRenderer])] //Légende du graphique : un nom de légende associé à des points
    var data = Vector[Any]() //Vecteur contenant les informations du type [Double,String,Double]
    val nbData=myData.length //nombre de données
    var i=0 //itérateur des données

    while(i < nbData)
    {
      nomAlgo = myData(i)(1).toString()

      while(i < nbData && myData(i)(1).toString() == nomAlgo)
      {
        //Récupération des données
        data = myData(i)

        //Création d'un point
        glyph = new Circle().x(data(0).asInstanceOf[Double]).y(data(2).asInstanceOf[Double]).size(5).fill_color(colors(indicColor)).line_color(Color.Black)
        circle = new GlyphRenderer().data_source(source).glyph(glyph)

        //ajout du point
        plot.renderers.<<=(_ :+ circle)

        //passage à la donnée suivante
        i += 1
      }

      leg = leg :+(nomAlgo, List(circle)) //ajout de la légende dans la liste des légendes (nom de légende associé une à liste de points)
      indicColor = (indicColor + 1) % colors.length //changement de couleur si on a des valeurs provenant d'autres algo (permettra de différencier les courbes)

    }

    val legend = new Legend().legends(leg.toList) //récupération de la liste des légendes afin de l'insérer
    plot.renderers.<<=(_ :+ legend) //affichage de la légende
    webDisplay(plot)
  }


  /**
    * Initialise un graphique
    *
    * @return : le graphique
    */
  def initializePlot(): Plot ={
    val xdr = new DataRange1d()
    val ydr = new DataRange1d()

    //Options du graphique
    val plot = new Plot().x_range(xdr).y_range(ydr).tools(Pan|WheelZoom|Resize|Reset|PreviewSave)

    return plot
  }

  /**
    * Affichage d'un graphique dans une page web
    *
    * @param plot : le graphique à afficher
    */
  def webDisplay(plot: Plot): Unit ={
    val document = new Document(plot)
    val html = document.save("sample.html")
    println(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
    html.view()
  }

}