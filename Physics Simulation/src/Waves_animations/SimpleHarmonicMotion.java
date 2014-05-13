package Waves_animations;

import Graph.Graph;
import Interface.EventActions;
import Interface.Interface;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javax.swing.JOptionPane;

public class SimpleHarmonicMotion extends EventActions implements Interface{

    private EventHandler<ActionEvent> buttonAction = new ButtonAction();
    private int circleIndex = INDEX_ONE;
    private double amplitude = DEFAULT_AMPLITUDE;
    private double frequency = DEFAULT_FREQUENCY;
    private double time = INIT_TIME;
    private Graph graph = new Graph();
    private BorderPane graphPane;
    private Timeline waveAnimationTimeline = new Timeline();
    private Scene waveAnimationScene;
   
    @Override
    public Scene getScene(){
        setScene();
        return waveAnimationScene;
    }
    
    @Override
    public void setScene(){
        waveAnimationScene = new Scene(root_SHM,sceneWidth,sceneHeight);
        setGraph();
        setButtonEvent();
        setTimelineEvent();

//Creates the waveString string composed of multiple circles        
        for (int i = 0; i < waveString.length; i++) {
            waveString[i] = new Circle(INIT_X + (i * 15), INIT_Y, 5, Color.LIGHTGRAY);
            root_SHM.getChildren().add(waveString[i]);
            if (i%10 == 0) {
                waveString[i].setFill(Color.BLACK);
            }
        }
        inputAmplitude_SHM.setPromptText("Input Amplitude");
        inputFrequency_SHM.setPromptText("Input Frequency");
        buttonGroup_SHM.getChildren().addAll(inputAmplitude_SHM, ampltiudeUnits, inputFrequency_SHM, frequencyUnits_SHM, startButtonWaves, pauseButtonWaves, stopButtonWaves, backButtonWaves);
        buttonGroup_SHM.setSpacing(10);
        buttonGroup_SHM.setPadding(new Insets(10,10,10,10));        
        root_SHM.setTop(buttonGroup_SHM);
    }
    
    @Override
    public void setGraph(){
        graphPane = graph.initiateGraph("Position VS Time: Simple Harmonic Motion","SHM","Time(s)","Position(m)",1,10,1);
        graph.setBounds(1,DEFAULT_AMPLITUDE,0,-1*DEFAULT_AMPLITUDE);
        root_SHM.setBottom(graphPane);        
    }
    
    @Override
    public void setButtonEvent(){
        startButtonWaves.setOnAction(buttonAction);
        pauseButtonWaves.setOnAction(buttonAction);
        stopButtonWaves.setOnAction(buttonAction);        
    }
    
    @Override
    public void setTimelineEvent(){
        KeyFrame kf = new KeyFrame(Duration.millis(40), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                graph.addDataToSeries(time, INIT_Y - waveString[INDEX_ZERO].getCenterY(), 0.02);
  //Control each individual circleProjectile's simple harmonic motion              
                for (int i = 1; i <= circleIndex; i++) {
                    waveString[i-1].setCenterY(INIT_Y - amplitude * Math.sin(Math.PI * 2 * frequency * (time - INIT_TIME * (i - 1))));
                }
                time += INIT_TIME;
//Makes sure each ball start its motion one at a time, one after the other
                if (circleIndex < waveString.length) {
                    circleIndex++;
                }
            }
        });
        
        waveAnimationTimeline.setCycleCount(Timeline.INDEFINITE);
        waveAnimationTimeline.getKeyFrames().add(kf);        
    }
    
    class ButtonAction implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e){
            if(e.getSource().equals(startButtonWaves)){
                startEvent();
            }else if(e.getSource().equals(pauseButtonWaves)){
                pauseEvent();
            }else if(e.getSource().equals(stopButtonWaves)){
                stopEvent();
            }
        }
    }
    
    @Override
    public void startEvent(){
        try{
            if(inputAmplitude_SHM.getText().isEmpty()){
                amplitude = DEFAULT_AMPLITUDE;
                inputAmplitude_SHM.setText(Double.toString(amplitude));
            }else{
                amplitude = Double.parseDouble(inputAmplitude_SHM.getText());
                if(amplitude>DEFAULT_AMPLITUDE)
                    graph.setBounds(1, amplitude, 0, -1*amplitude);
            }
            if(inputFrequency_SHM.getText().isEmpty()){
                frequency = DEFAULT_FREQUENCY;
                inputFrequency_SHM.setText(Double.toString(frequency));
            }else{
                frequency = Double.parseDouble(inputFrequency_SHM.getText());
            }   
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, error);
        }   
        waveAnimationTimeline.play();
    }
    
    public void pauseEvent(){
        waveAnimationTimeline.pause();
    }
    
    @Override
    public void stopEvent(){
        waveAnimationTimeline.stop();
        reset();
    }

    @Override
    public void reset() {
        graph.resetGraph();
        time = INIT_TIME;
        circleIndex = 1;
//Reset wave string to its original position
        for (int i = 0; i < waveString.length; i++) {
            waveString[i].setCenterY(INIT_Y);
        }
    }
    
    @Override
    public Button getReturnButton(){
        return backButtonWaves;
    }
}