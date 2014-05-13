
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javax.swing.JOptionPane;

public class DopplerEffect extends EventActions implements Interface{
    
    private EventHandler<ActionEvent> buttonAction = new ButtonAction();
    private Circle source = new Circle(INIT_X_Source,INIT_Y_Source,10);
    private Circle observer = new Circle(INIT_X_OBSERVER,INIT_Y_OBSERVER,15,Color.PINK);
    private Circle[] soundWaves = new Circle[100];
    private Timeline dopplerAnimationTimeline = new Timeline();
    private Scene dopplerAnimationScene;
    
    private int waveIndex = 0;
    private int frequency;
    private double observedFrequency;
    private double time = INIT_TIME;    
    private BorderPane graphPane;
    private Graph graph = new Graph();
    
    private double sourceTranslateIndex = 0;
    private double observerTranslateIndex = 0;
    private double refreshRate;
    private double sourceVelocity;
    private double observerVelocity;
    private double fade;
    
    @Override
    public Scene getScene(){
        setScene();
        return dopplerAnimationScene;
    }
    
    @Override
    public void setScene(){

        dopplerAnimationScene = new Scene(root_doppler, sceneWidth, sceneHeight);
        setGraph();
        setButtonEvent();
        setTimelineEvent();
        setDragEvent();
        
        buttonGroup_doppler.getChildren().addAll(inputVelocitySource,sourceVelocityUnits,inputVelocityObserver,observerVelocityUnits,inputFrequency_doppler,frequencyUnits_doppler,startButtonDoppler,pauseButtonDoppler,resetButtonDoppler,backButtonDoppler);
        buttonGroup_doppler.setSpacing(10);
        buttonGroup_doppler.setPadding(new Insets(10,10,10,10));
        
        inputVelocitySource.setPromptText("Enter velocity of the source");
        inputVelocityObserver.setPromptText("Enter velocity of the observer");
        inputFrequency_doppler.setPromptText("Enter Frequency");
        
        root_doppler.getChildren().addAll(source,observer);
        root_doppler.setTop(buttonGroup_doppler);
    }
    
    @Override
    public void setGraph(){
        graphPane = graph.initiateGraph("Frequency VS Time: Doppler Effect","Frequency","Time(s)","Frequency(m)",1,10,1);
        graph.setBounds(1,1,0,-50);
        root_doppler.setBottom(graphPane);        
    }
    
    @Override
    public void setButtonEvent(){
        startButtonDoppler.setOnAction(buttonAction);
        pauseButtonDoppler.setOnAction(buttonAction);
        resetButtonDoppler.setOnAction(buttonAction);        
    }
    
    @Override
    public void setTimelineEvent(){
        KeyFrame kf = new KeyFrame(Duration.millis(17), new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                if(soundWaves[INDEX_ZERO].getRadius() >= observer.getCenterX()-source.getCenterX()){
                    if(observer.getCenterX() > source.getCenterX()+source.getTranslateX()){
                        observedFrequency = ((340+observerVelocity*200)/(340-sourceVelocity*200))*(1/(Double.parseDouble(inputFrequency_doppler.getText())/100));
                        graph.addDataToSeries(time, 40 * Math.sin(Math.PI * 2 * observedFrequency * time), 0.02);
                        time+=INIT_TIME;
                    }else if(observer.getCenterX() < source.getCenterX()+source.getTranslateX()){
                        observedFrequency = ((340-observerVelocity*200)/(340+sourceVelocity*200))*(1/(Double.parseDouble(inputFrequency_doppler.getText())/100));
                        graph.addDataToSeries(time, 40 * Math.sin(Math.PI * 2 * observedFrequency * time), 0.02);
                        time+=INIT_TIME;
                    }
                }else{
                    graph.addDataToSeries(time, 0, 0.02);
                    time+=INIT_TIME;
                }     
                if(sourceTranslateIndex+source.getCenterX() < (int)(1021-sourceVelocity)){
                    source.setTranslateX(sourceTranslateIndex);
                    sourceTranslateIndex = Double.parseDouble(df_1.format(sourceVelocity+sourceTranslateIndex));
                    if(observerTranslateIndex+observer.getCenterX() > (int)(31+observerVelocity)){
                        observer.setTranslateX(observerTranslateIndex);
                        observerTranslateIndex = Double.parseDouble(df_1.format(observerTranslateIndex-observerVelocity));
                    }
                }else{
                    fade+=fadeRate;
                }
                for(int i=0; i<waveIndex; i++){
                    if(soundWaves[i].getStrokeWidth() < 0.15){
                        if(root_doppler.getChildren().contains(soundWaves[i]))
                            root_doppler.getChildren().remove(soundWaves[i]);
                    }
                    soundWaves[i].setRadius(soundWaves[i].getRadius()+1.7);
                    soundWaves[i].setStrokeWidth(1.5-(fade*(sourceTranslateIndex-(i*refreshRate))));
                }
                if(sourceTranslateIndex%refreshRate == 0){
                    produceSoundWave((int)(sourceTranslateIndex/refreshRate));
                }
            }
        });
        
        dopplerAnimationTimeline.setCycleCount(Timeline.INDEFINITE);
        dopplerAnimationTimeline.getKeyFrames().add(kf);        
    }
    
    public void setDragEvent(){
        observer.setOnMouseDragged(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                observer.setCenterX(me.getSceneX()-observer.getLayoutX());
                observer.setCenterY(me.getSceneY()-observer.getLayoutY());
            }
        });        
    }
    
    class ButtonAction implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e){
            if(e.getSource().equals(startButtonDoppler)){
                startEvent();
            }else if(e.getSource().equals(pauseButtonDoppler)){
                pauseEvent();
            }else if(e.getSource().equals(resetButtonDoppler)){
                stopEvent();
            }
        }
    };
    
    @Override
    public void startEvent(){
        try{
            sourceVelocity = Double.parseDouble(df_1.format(Double.parseDouble(inputVelocitySource.getText())/2));
            observerVelocity = Double.parseDouble(df_1.format(Double.parseDouble(inputVelocityObserver.getText())/2));
            frequency = (int)((1/(Double.parseDouble(inputFrequency_doppler.getText())))*100);
            refreshRate = (frequency*sourceVelocity);
            if(sourceVelocity<1 && sourceVelocity>=0.5){
                fade = fadeRate*25;
            }else if(sourceVelocity<0.5){
                fade = fadeRate*80;
            }else{
                fade = sourceVelocity/(400-((1.5-sourceVelocity)*410));
            }
            produceSoundWave(0);
            dopplerAnimationTimeline.play();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, error);
        }        
    }
    
    @Override
    public void pauseEvent(){
        dopplerAnimationTimeline.pause();
    }
    
    @Override
    public void stopEvent(){
        dopplerAnimationTimeline.stop();
        reset();        
    }
    
    @Override
    public void reset(){
        graph.resetGraph();
        source.setTranslateX(0);
        waveIndex = 0;
        sourceTranslateIndex = 0;
        observer.setTranslateX(0);
        time = INIT_TIME;
        for(int i=0; i<soundWaves.length; i++){
            if(root_doppler.getChildren().contains(soundWaves[i])){
                root_doppler.getChildren().remove(soundWaves[i]);
            }
        }
    }
    
    public void produceSoundWave(int pos){
        soundWaves[pos] = new Circle(source.getTranslateX()+source.getCenterX(),source.getCenterY(),10, Color.TRANSPARENT);
        soundWaves[pos].setStroke(Color.BLACK);
        soundWaves[pos].setStrokeWidth(1.5);
        root_doppler.getChildren().add(soundWaves[pos]);
        waveIndex++;
    }
    
    @Override
    public Button getReturnButton(){
        return backButtonDoppler;
    }
}