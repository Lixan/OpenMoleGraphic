package DataDisplay

import io.continuum.bokeh.Tools.Pan
import io.continuum.bokeh.{Circle, Color, ColumnDataSource, DataRange1d, Document, GlyphRenderer, Legend, LinearAxis, Location, Plot, Tools}

import scala.collection.mutable.ListBuffer

/**
  * Display method
  */
abstract class DataDisplay extends ColumnDataSource with Tools{


  /**
    * Initialize and display a 1D graph from data in a vector
    *
    * @param myData : Vector with data
    */
  def displayVector1D(myData:Array[Double]): Unit={

    val plot = initializePlot()
    val x = column(myData)

    val xaxis = new LinearAxis().plot(plot).location(Location.Below)
    plot.below <<= (xaxis :: _)

    val glyph = new Circle().x(x).y(0.1).size(5).fill_color(Color.Red).line_color(Color.Black)
    val circle = new GlyphRenderer().data_source(this).glyph(glyph)

    plot.renderers := List(xaxis, circle)

    webDisplay(plot)
  }

  /**
    * Initialize and display a 2D graph from data in a matrix
    *
    * @param myData : Matrix with data (2 lines, N columns)
    */
  def displayMatrix2D(myData:Array[Array[Double]]): Unit={

    val plot = initializePlot()
   /* val x = column(myData(0)) //random data
    val y = column(myData(1)) //random germs*/

    val xaxis = new LinearAxis().plot(plot).location(Location.Below)
    val yaxis = new LinearAxis().plot(plot).location(Location.Left)
    plot.below <<= (xaxis :: _)
    plot.left <<= (yaxis :: _)

    val glyph = new Circle().x(7).y(2).size(5).fill_color(Color.Red).line_color(Color.Black)
    val glyph2 = new Circle().x(5).y(7).size(5).fill_color(Color.Red).line_color(Color.Black)

    val circle = new GlyphRenderer().data_source(this).glyph(glyph)
    val circle2 = new GlyphRenderer().data_source(this).glyph(glyph2)

    var listRenderers = List(xaxis, yaxis)
    plot.renderers :=  listRenderers

    plot.renderers.<<=(_ :+ circle)
    plot.renderers.<<=(_ :+ circle2)

    webDisplay(plot)
  }

  /**
    * Display a vector of vector [ [germ1, algo1, res1.1], [germ2, algo1, res1.2], ... [germN, algoN, resN.N] ]
    *
    * @param myData : The vector of vector to display
    */
  def displayVectorOfVector(myData:Vector[Vector[Any]]): Unit


  /**
    * Initialize a graph
    *
    * @return : The graph
    */
  def initializePlot(): Plot ={
    val xdr = new DataRange1d()
    val ydr = new DataRange1d()

    //Options of the graph
    val plot = new Plot().x_range(xdr).y_range(ydr).tools(Pan|WheelZoom|Resize|Reset|PreviewSave)

    return plot
  }

  /**
    * Display a graph in a browser
    *
    * @param plot : The graph to display
    */
  def webDisplay(plot: Plot): Unit ={
    val document = new Document(plot)
    val html = document.save("sample.html")
    println(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
    html.view()
  }

}
