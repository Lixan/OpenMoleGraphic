package GraphDisplay

import DataDisplay.DataDisplay
import breeze.linalg._
import io.continuum.bokeh.{Circle, Color, ColumnDataSource, GlyphRenderer, Legend, Line, LinearAxis, Location}

import scala.Vector
import scala.collection.mutable.ListBuffer

/**
  * Created by alexandre on 24/06/16.
  * Display a classic graph
  *
  * It doesn't function
  */
class GraphDisplay extends DataDisplay {

  /**
    * Display a vector of vector [ [germ1, algo1, res1.1], [germ2, algo1, res1.2], ... [germN, algoN, resN.N] ]
    *
    * @param myData : The vector of vector to display
    */
  override def displayVectorOfVector(myData: Vector[Vector[Any]]): Unit = {
    /** Initialization of the graph **/
    val plot = initializePlot()

    val xaxis = new LinearAxis().plot(plot).location(Location.Below).axis_label("Germes aléatoires")
    val yaxis = new LinearAxis().plot(plot).location(Location.Left).axis_label("Résultats aléatoires")

    plot.below <<= (xaxis :: _)
    plot.left <<= (yaxis :: _)

    var listRenderers = List(xaxis, yaxis)
    plot.renderers :=  listRenderers

    /** get data **/
    val colors = Array[Color](Color.Red,Color.Blue,Color.Green,Color.Yellow,Color.Orange,Color.Brown,
      Color.Black, Color.Magenta, Color.Pink, Color.Purple) //Add colors here if you have a lot of algorithms
    var indicColor = 0 //Index of colors array to know which color to use to display a group of data from an algorithm
    var nomAlgo = ""
    var glyph = new Line()
    var line = new GlyphRenderer()
    var leg = ListBuffer.empty[(String,List[GlyphRenderer])] //Legend of the graph: a name is associated with an algorithm
    var data = Vector[Any]() //Vector with informations like [Double,String,Double]
    var data2 = Vector[Any]()
    val nbData=myData.length //Number of data
    var i=0 //Iterator of data

    // column to display
    var source = new ColumnDataSource()

    // horizontal position of the column
    var x1 = 0.0
    var x2 = 0.0

    // height of the column
    var y1 = 0.0
    var y2 = 0.0

    while(i+1 < nbData)
    {
      nomAlgo = myData(i)(1).toString()

      while(i+1 < nbData && myData(i)(1).toString() == nomAlgo)
      {
        //Get the data
        data = myData(i)
        data2 = myData(i+1)

        //Create a data point
        x1 = data(0).asInstanceOf[Double]
        y1 = data(2).asInstanceOf[Double]

        x2 = data2(0).asInstanceOf[Double]
        y2 = data(2).asInstanceOf[Double]

        //save of the current column
        source.addColumn(Symbol("x" + i),linspace(x1,x2,10))
        source.addColumn(Symbol("y" + i),linspace(y1,y2,10))

        //we add the current column
        glyph = new Line().x(Symbol("x" + i)).y(Symbol("y" + i)).line_width(2).line_color(colors(indicColor))
        line = new GlyphRenderer().data_source(source).glyph(glyph)

        //add the data point
        plot.renderers.<<=(_ :+ line)

        //next data
        i += 1
      }

      leg = leg :+(nomAlgo, List(line)) //Add the legend to the list of legends (name of the legend associated to an algorithm)
      indicColor = (indicColor + 1) % colors.length //Change the color because we change the algorithm

    }

    val legend = new Legend().legends(leg.toList) //Get list of legends in order to insert it
    plot.renderers.<<=(_ :+ legend) //Display the legend
    webDisplay(plot)
  }
}
