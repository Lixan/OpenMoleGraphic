package DataDisplay

import io.continuum.bokeh.Tools.Pan
import io.continuum.bokeh.{Circle, Color, ColumnDataSource, DataRange1d, Document, GlyphRenderer, Legend, LinearAxis, Location, Plot, Tools}

import scala.collection.mutable.ListBuffer

object DataDisplay extends ColumnDataSource with Tools{


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
    val circle = new GlyphRenderer().data_source(DataDisplay).glyph(glyph)

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
    val x = column(myData(0)) //random data
    val y = column(myData(1)) //random germs

    val xaxis = new LinearAxis().plot(plot).location(Location.Below)
    val yaxis = new LinearAxis().plot(plot).location(Location.Left)
    plot.below <<= (xaxis :: _)
    plot.left <<= (yaxis :: _)

    val glyph = new Circle().x(x).y(y).size(5).fill_color(Color.Red).line_color(Color.Black)

    val circle = new GlyphRenderer().data_source(DataDisplay).glyph(glyph)

    plot.renderers := List(xaxis, yaxis, circle)

    webDisplay(plot)
  }

  /**
    * Display a vector of vector [ [germ1, algo1, res1.1], [germ2, algo1, res1.2], ... [germN, algoN, resN.N] ]
    *
    * @param myData : The vector of vector to display
    */
  def displayVectorOfVector(myData:Vector[Vector[Any]]): Unit={

    /** Initialization of the graph **/
    val plot = initializePlot()
    val xaxis = new LinearAxis().plot(plot).location(Location.Below).axis_label("Résultats aléatoires")
    val yaxis = new LinearAxis().plot(plot).location(Location.Left).axis_label("Germes aléatoires")

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
        circle = new GlyphRenderer().data_source(DataDisplay).glyph(glyph)

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
