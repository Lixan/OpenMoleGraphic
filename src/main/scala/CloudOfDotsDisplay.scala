package CloudOfDotsDisplay

import DataDisplay.DataDisplay
import io.continuum.bokeh.{Circle, Color, GlyphRenderer, Legend, LinearAxis, Location}

import scala.collection.mutable.ListBuffer

/**
  * Created by alexandre on 24/06/16.
  * Display a cloud of dots
  */
class CloudOfDotsDisplay extends  DataDisplay {

  /**
    * Display a vector of vector [ [germ1, algo1, res1.1], [germ2, algo1, res1.2], ... [germN, algoN, resN.N] ]
    * as a cloud of dots
    *
    * @param myData : The vector of vector to display
    */
  override def displayVectorOfVector(myData:Vector[Vector[Any]]): Unit={

    /** Initialization of the graph **/
    val plot = initializePlot()
    val xaxis = new LinearAxis().plot(plot).location(Location.Below).axis_label("Random seeds")
    val yaxis = new LinearAxis().plot(plot).location(Location.Left).axis_label("Results")

    plot.below <<= (xaxis :: _)
    plot.left <<= (yaxis :: _)

    var listRenderers = List(xaxis, yaxis)
    plot.renderers :=  listRenderers

    /** get data **/
    val colors = Array[Color](Color.Red,Color.Blue,Color.Green,Color.Yellow,Color.Orange,Color.Brown,
      Color.Black, Color.Magenta, Color.Pink, Color.Purple) //Add colors here if you have a lot of algorithms
    var indicColor = 0 //Index of colors array to know which color to use to display a group of data from an algorithm
    var nomAlgo = ""
    var glyph = new Circle()
    var circle = new GlyphRenderer()
    var leg = ListBuffer.empty[(String,List[GlyphRenderer])] //Legend of the graph: a name is associated with an algorithm
    var data = Vector[Any]() //Vector with informations like [Double,String,Double]
    val nbData=myData.length //Number of data
    var i=0 //Iterator of data

    while(i < nbData)
    {
      nomAlgo = myData(i)(1).toString()

      while(i < nbData && myData(i)(1).toString() == nomAlgo)
      {
        //Get the data
        data = myData(i)

        //Create a data point
        glyph = new Circle().x(data(0).asInstanceOf[Double]).y(data(2).asInstanceOf[Double]).size(5).fill_color(colors(indicColor)).line_color(Color.Black)
        circle = new GlyphRenderer().data_source(this).glyph(glyph)

        //add the data point
        plot.renderers.<<=(_ :+ circle)

        //next data
        i += 1
      }

      leg = leg :+(nomAlgo, List(circle)) //Add the legend to the list of legends (name of the legend associated to an algorithm)
      indicColor = (indicColor + 1) % colors.length //Change the color because we change the algorithm

    }

    val legend = new Legend().legends(leg.toList) //Get list of legends in order to insert it
    plot.renderers.<<=(_ :+ legend) //Display the legend
    webDisplay(plot)

  }
}
