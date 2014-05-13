
package Electricity_Magnetism;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import Graph.Graph;
import Interface.EventActions;
import Interface.Interface;
import javafx.geometry.Insets;
import javafx.scene.control.Button;

public class DCcircuit extends EventActions implements Interface{

    private EventHandler <ActionEvent> buttonAction = new ButtonAction();
    private int circleIndex = INDEX_ONE;
    private double componentX = 0;
    private double componentY = 0;
    private double voltage;
    private double current;
    private double resistance;
    private double speed;
    private double lightIntensity;
    private int count = INDEX_ZERO;
    private Graph graph = new Graph();
    private BorderPane graphPane;
    private Scene circuitAnimationScene;
    private Timeline circuitAnimationTimeline = new Timeline();
    
    @Override
    public Scene getScene(){
        setScene();
        return circuitAnimationScene;
    }
    
    @Override
    public void setScene(){
        
        circuitAnimationScene = new Scene(root_circuit, sceneWidth, sceneHeight);
        setGraph();
        setButtonEvent();
        setTimelineEvent();        
        
            for(int i = 0; i < 3; i++){
                inputs[i] = new TextField();    
                inputs[i].setMaxWidth(100);           
                inputs[i].setOnAction(buttonAction);
            }
            for(int x = 0; x < 2; x++){
                iv[x] = new ImageView();
                iv[x].setFitHeight(50);
                iv[x].setFitWidth(50);
                root_circuit.getChildren().addAll(iv[x]);
            }
            
// Image positions            
            iv[INDEX_ZERO].setImage(lightbulb);
            iv[INDEX_ZERO].setTranslateX(275);
            iv[INDEX_ZERO].setTranslateY(170);
// Image positions            
            iv[1].setImage(battery);           
            iv[1].setTranslateX(285);
            iv[1].setTranslateY(275);
            
            inputs[INDEX_ZERO].setPromptText("Input Voltage");
            inputs[INDEX_ONE].setPromptText("Input current");
            inputs[INDEX_TWO].setPromptText("Input Resistance");
            
            for(int i = 0; i <3 ; i++){
                brightness[i] = new Line();
                brightness[i].setStroke(Color.YELLOW);
                brightness[i].setStrokeType(StrokeType.OUTSIDE);
                brightness[i].setOpacity(0);
                root_circuit.getChildren().addAll(brightness[i]);
            }    
                brightness[INDEX_ZERO].setStartX(275);
                brightness[INDEX_ZERO].setStartY(190);
                brightness[INDEX_ONE].setStartX(300);
                brightness[INDEX_ONE].setStartY(165);
                brightness[INDEX_TWO].setStartX(325);
                brightness[INDEX_TWO].setStartY(190);
                
                brightness[INDEX_ZERO].setEndX(240);
                brightness[INDEX_ZERO].setEndY(170);
                brightness[INDEX_ONE].setEndX(300);
                brightness[INDEX_ONE].setEndY(145);
                brightness[INDEX_TWO].setEndX(360);
                brightness[INDEX_TWO].setEndY(170);

            buttonGroup_circuit.getChildren().addAll(voltage_Label, inputs[INDEX_ZERO], current_Label, inputs[INDEX_ONE],
                    resistance_Label, inputs[INDEX_TWO],startButtonCircuit,stopButtonCircuit,resetButtonCircuit,
                    helpButtonCircuit,backButtonCircuit);
            buttonGroup_circuit.setSpacing(10);
            buttonGroup_circuit.setPadding(new Insets(10,10,10,10));

        for(int i = 0; i < electron.length; i++){
            electron[i] = new Circle(295, POINT1Y, 5, Color.RED);
            root_circuit.getChildren().addAll(electron[i]);
        }
        root_circuit.getChildren().addAll(electronPath());
        root_circuit.setTop(buttonGroup_circuit);
    }
    
    @Override
    public void setGraph(){
        graphPane = graph.initiateGraph("DC Circuit", "Circuit", "Voltage", "Current", 5, 5, 1);
        graph.setBounds(101, 70, 0, 0);
        root_circuit.setBottom(graphPane);
    }
    
    @Override
    public void setButtonEvent(){
        startButtonCircuit.setOnAction(buttonAction);
        stopButtonCircuit.setOnAction(buttonAction);
        resetButtonCircuit.setOnAction(buttonAction);
        helpButtonCircuit.setOnAction(buttonAction);
        backButtonCircuit.setOnAction(buttonAction);        
    }
    
    @Override
    public void setTimelineEvent(){
        KeyFrame kf = new KeyFrame(Duration.millis(40), 
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                
                if(electron[0].getCenterX() == POINT3X-100 && 
                       electron[INDEX_ZERO].getCenterY() == POINT2Y){
                    for(int j = 0; j < 3; j++){
                        brightness[j].setOpacity(1);
                    }                         
                        brightness[INDEX_ZERO].setEndX(brightness[INDEX_ZERO].getEndX() 
                            - lightBrightness());
                        brightness[INDEX_ZERO].setEndY(brightness[INDEX_ZERO].getEndY()
                            - lightBrightness());
                        brightness[INDEX_ONE].setEndX(brightness[INDEX_ONE].getEndX());
                        brightness[INDEX_ONE].setEndY(brightness[INDEX_ONE].getEndY() 
                            - lightBrightness());
                        brightness[INDEX_TWO].setEndX(brightness[INDEX_TWO].getEndX() 
                            + lightBrightness());
                        brightness[INDEX_TWO].setEndY(brightness[INDEX_TWO].getEndY() 
                            - lightBrightness());
                }        
                
                for(int i = 1; i <= circleIndex; i++){
                    if(electron[i-1].getCenterX() > POINT1X && 
                        electron[i-1].getCenterY() == POINT1Y){
                        movePoint1(i);
                    }else if(electron[i-1].getCenterX() == POINT1X && 
                        electron[i-1].getCenterY() > POINT2Y){
                        movePoint2(i);
                    }else if(electron[i-1].getCenterX() < POINT3X && 
                        electron[i-1].getCenterY() == POINT2Y){            
                        movePoint3(i);
                    }else if(electron[i-1].getCenterX() == POINT3X && 
                        electron[i-1].getCenterY() < POINT1Y){
                        movePoint4(i);
                    }else if(electron[i-1].getCenterX() < 315 && 
                        electron[i-1].getCenterY() == POINT1Y){                      
                    }
                    if(electron[i-1].getCenterX() == 300 && 
                        electron[i-1].getCenterY() == 350){
                        movePoint5(i);
                        root_circuit.getChildren().remove(electron[i]);
                    }
                } 
                if(circleIndex < electron.length){
                    if(count%5 == 0){
                        circleIndex++;
                        count++;
                    }else{
                        count++;
                    }
                }
                if(componentX <= voltage - (voltage-current)){
                    componentX++;
                }
                if(componentY <= current){
                    componentY++;
                }                 
                graph.addDataToSeries(componentX,componentY,1);
            }});
        
        circuitAnimationTimeline.setCycleCount(Timeline.INDEFINITE);
        circuitAnimationTimeline.getKeyFrames().add(kf);
    }

    public Path electronPath(){
        path.getElements().add(new MoveTo(POINT5X,POINT1Y));
        path.getElements().add(new LineTo(POINT1X,POINT1Y));
        for(int i = 0; i < 7; i++){
        path.getElements().add(new LineTo(200 + Math.pow(-1,i)*5, 250 - i*5));
        }
        path.getElements().add(new LineTo(POINT1X,POINT2Y));    
        path.getElements().add(new LineTo(POINT3X,POINT2Y));
        path.getElements().add(new LineTo(POINT3X,POINT1Y));
        path.getElements().add(new LineTo(310,POINT1Y));
        
        return path;
    }

    public void movePoint1(int i){
        electron[i-1].setCenterX(convertToDF(electron[i-1].
                getCenterX()-electronSpeed()));
    }
    public void movePoint2(int i){
        electron[i-1].setCenterY(convertToDF(electron[i-1].          
                getCenterY()-electronSpeed()));
    }
    public void movePoint3(int i){
        electron[i-1].setCenterX(convertToDF(electron[i-1].
                getCenterX()+electronSpeed()));
    }
    public void movePoint4(int i){
        electron[i-1].setCenterY(convertToDF(electron[i-1].
                getCenterY()+electronSpeed()));
    }
    public void movePoint5(int i){
        electron[i-1].setCenterX(convertToDF(electron[i-1].
                getCenterY()-1));
    }

    class ButtonAction implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e){
            if(e.getSource().equals(startButtonCircuit)){ 
                startEvent();
            }
            if(e.getSource().equals(stopButtonCircuit)){
                pauseEvent();
            }
            if(e.getSource().equals(resetButtonCircuit)){
                stopEvent();
            }
            if(e.getSource().equals(helpButtonCircuit)){
                helpEvent();
            }
        }
    }
    
    @Override
    public void startEvent(){
        try{
            for(int i = 0 ; i < 5; i++){         
                if(inputs[0].getText().isEmpty() && 
                    inputs[1].getText().isEmpty() 
                    && inputs[2].getText().isEmpty()){

                    voltage = DEFAULT_VOLTAGE;
                    current += DEFAULT_CURRENT;
                    resistance = DEFAULT_RESISTANCE;
                    inputs[INDEX_ZERO].setText(Double.toString(voltage));
                    inputs[INDEX_ONE].setText(Double.toString(current));
                    inputs[INDEX_TWO].setText(Double.toString(resistance));
              }

                if(inputs[INDEX_ZERO].getText().isEmpty()){
                   voltage = Double.parseDouble(inputs[INDEX_ONE].getText()) * 
                           Double.parseDouble(inputs[INDEX_TWO].getText());
                   inputs[INDEX_ZERO].setText(Double.toString(convertToDF(voltage)));
                }else{
                    voltage = Double.parseDouble(inputs[INDEX_ZERO].getText());
                }
                if(inputs[INDEX_ONE].getText().isEmpty()){
                    current = Double.parseDouble(inputs[INDEX_ZERO].getText()) /
                        Double.parseDouble(inputs[INDEX_TWO].getText());
                    inputs[INDEX_ONE].setText(Double.toString(convertToDF(current)));
                }else{
                    current = Double.parseDouble(inputs[INDEX_ONE].getText());
                }
                if(inputs[INDEX_TWO].getText().isEmpty()){
                    resistance = Double.parseDouble(inputs[INDEX_ZERO].getText()) / 
                            Double.parseDouble(inputs[INDEX_ONE].getText());
                    inputs[INDEX_TWO].setText(Double.toString(convertToDF(resistance)));
              }else{
                   resistance = Double.parseDouble(inputs[INDEX_TWO].getText());
                }      
                }       
            }catch(Exception a){
                JOptionPane.showMessageDialog(null,
                    "Invalid Input", "Error",
                    JOptionPane.ERROR_MESSAGE);          
            }                    
            if(Math.abs(voltage-(current*resistance)) >= 0.5){
                    JOptionPane.showMessageDialog(null,
                        "Inputs do not respect Ohm's Law", "Error",
                        JOptionPane.ERROR_MESSAGE);
                    circuitAnimationTimeline.stop();
                    }else{
                circuitAnimationTimeline.play();
                }                
                    
    }
    
    @Override
    public void pauseEvent(){
        circuitAnimationTimeline.pause();
        //resetSpeed();
    }
    
    @Override
    public void stopEvent(){
        circuitAnimationTimeline.stop();
        resetSpeed();
        graph.resetGraph();
        for(int x = 0; x < 3; x++){
            brightness[x].setOpacity(0);
            inputs[x].clear();
        }
            for(int y = 0; y < electron.length; y++){
                electron[y].setCenterX(295);
                electron[y].setCenterY(POINT1Y);
            }        
    }
    
    @Override
    public void helpEvent(){
        JOptionPane.showMessageDialog(null,
                "Input the voltage, current and resistance by"
                + " respecting Ohm's law, if one input is missing "
                + "the program will automatically fill it.","Help",
                JOptionPane.INFORMATION_MESSAGE);     
    }
    
    public void resetSpeed(){
        speed = 0;
        current = 0;
        //timeline_circuit.getKeyFrames().clear();
    }

    public double electronSpeed(){
        speed = convertToDF(current/10);
        return speed;
    }
    
    public double lightBrightness(){
        lightIntensity = convertToDF(current/500);
        return lightIntensity;
    }
    
    @Override
    public double convertToDF(double value){
       double valueDF = Double.parseDouble(df_1.format(value));
       return valueDF;
    }
    
    @Override
    public Button getReturnButton(){
        return backButtonCircuit;
    }
}