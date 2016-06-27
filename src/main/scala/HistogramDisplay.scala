package HistogramDisplay

import DataDisplay.DataDisplay

import io.continuum.bokeh.Tools.Pan
import io.continuum.bokeh.{Circle, Color, ColumnDataSource, DataRange1d, Document, GlyphRenderer, Legend, LinearAxis, Location, Plot, Tools}
import io.continuum.bokeh.Line

import scala.collection.mutable.ListBuffer
import breeze.linalg.{DenseVector, linspace}

import math.{Pi => pi}
/**
  * Created by alexandre on 24/06/16.
  * display an histogram
  */
class HistogramDisplay extends DataDisplay {


  /**
    * Display a vector of vector [ [germ1, algo1, res1.1], [germ2, algo1, res1.2], ... [germN, algoN, resN.N] ]
    *
    * @param myData : The vector of vector to display
    */
  override def displayVectorOfVector(myData:Vector[Vector[Any]]): Unit={

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
    val nbData=myData.length //Number of data
    var i=0 //Iterator of data

    // column to display
    var source = new ColumnDataSource()

    // horizontal position of the column
    var x = 0.0
    // height of the column
    var y = 0.0

    while(i < nbData)
    {
      nomAlgo = myData(i)(1).toString()

      while(i < nbData && myData(i)(1).toString() == nomAlgo)
      {
        //Get the data
        data = myData(i)

        //Create a data point
        x = data(0).asInstanceOf[Double]
        y = data(2).asInstanceOf[Double]

        //save of the current column
        source.addColumn(Symbol("x" + i),linspace(x,x,10))
        source.addColumn(Symbol("y" + i),linspace(0.0,y,10))

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
