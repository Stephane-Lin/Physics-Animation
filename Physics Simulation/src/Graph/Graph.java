package Graph;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;

public class Graph {

    private XYChart.Series series;
    private double dataY, dataX;
    private NumberAxis xAxis, yAxis;
    private double xUpperBound, xLowerBoundInitial, yLowerBoundInitial, yUpperBound, xUpperBoundInitial, yUpperBoundInitial, xLowerBound, yLowerBound;
    private BorderPane pane = new BorderPane(); //--TEMPORARY
    private int switchInt;
    private LineChart<Number, Number> graph;
    
    public Graph(){}
    
    /* In order of what you values you have to bring into this class with the initiateGraph() method:
     * String graphTitle, seriesTitle, xAxisString, yAxisString
     * double xUpperBoundInitial, yUpperBoundInitial, xLowerBoundInitial, yLowerBoundInitial
     * double tickX, tickY  --> sets the tick, the little lines on the graph on the axis
     * swithcInt = can only be 1 or 2 --> 1 = lowerbound will change, 2 = lower bound will not change
     * 
     * */
    
    public BorderPane initiateGraph(String graphTitle, String seriesTitle, String xAxisString, String yAxisString, double xTick, double yTick, int switchInt){
        this.switchInt = switchInt;
        
        xAxis = new NumberAxis(xAxisString, xLowerBoundInitial, xUpperBoundInitial, xTick);
        xAxis.setAutoRanging(false);
        
        yAxis = new NumberAxis(yAxisString, yLowerBoundInitial, yUpperBoundInitial, yTick); 
        yAxis.setAutoRanging(false);
               
        //--CHART
        graph = new LineChart<Number, Number>(xAxis, yAxis) {};
        
        xAxis.setAnimated(false);
        yAxis.setAnimated(false);
        graph.setCreateSymbols(false);
        graph.setAnimated(true);
        //sc.setId("liveAreaChart");
        graph.setTitle(graphTitle);
        
        //--CHART SERIES
        series = new LineChart.Series<>();
        series.setName(seriesTitle);
        graph.getData().add(series);
        
        pane.setCenter(graph);
        
        return pane;
    }
    
    public void setBounds(double xUpperBoundInitial, double yUpperBoundInitial, double xLowerBoundInitial, double yLowerBoundInitial){
        this.xUpperBoundInitial = xUpperBoundInitial;
        this.yUpperBoundInitial = yUpperBoundInitial;
        this.xLowerBoundInitial = xLowerBoundInitial;
        this.yLowerBoundInitial = yLowerBoundInitial;
        xUpperBound = this.xUpperBoundInitial;
        yUpperBound = this.yUpperBoundInitial;
        xLowerBound = this.xLowerBoundInitial;
        yLowerBound = this.yLowerBoundInitial;        
    }

    public void addDataToSeries(double dataX, double dataY, double rate){
        
        this.dataY = dataY;
        this.dataX = dataX;
        
        series.getData().add(new LineChart.Data(this.dataX, this.dataY));
      
        if (dataX > (xUpperBound-rate*4) && Math.ceil(dataX) != xUpperBound) {
            switch(switchInt){
                case 1: xLowerBound+=rate;
                        xUpperBound+=rate;
                        xAxis.setLowerBound(xLowerBound);
                        xAxis.setUpperBound(xUpperBound); 
                        break;
                case 2: if(dataX > yUpperBound){  
                            xUpperBound += rate;
                            xAxis.setUpperBound(Math.floor(yUpperBound));
                        }       
                        break;
            }
        }
        
        if(dataY > (yUpperBound-rate*4) && Math.ceil(dataY) != yUpperBound || dataY < (yUpperBound) && Math.floor(dataY) != yLowerBound){
            switch(switchInt){
                case 1: if(Math.ceil(dataY) > yUpperBound){
                            yUpperBound = Math.ceil(dataY);
                        }
                        
                        yAxis.setUpperBound(yUpperBound);
                        yAxis.setLowerBound(yLowerBound);
                        break;
                    
                case 2: if(dataY > yUpperBound){  
                            yUpperBound += rate;
                            yAxis.setUpperBound(Math.floor(yUpperBound));
                        }       
                        break;
            }
        }
    }
    
    public void resetGraph(){
        xAxis.setLowerBound(xLowerBoundInitial);
        yAxis.setLowerBound(yLowerBoundInitial);
        xAxis.setUpperBound(xUpperBoundInitial);
        yAxis.setUpperBound(yUpperBoundInitial);
        xUpperBound = xUpperBoundInitial;
        xLowerBound = xLowerBoundInitial;
        yUpperBound = yUpperBoundInitial;
        yLowerBound = yLowerBoundInitial;
        series.getData().remove(0, series.getData().size());
    }
}