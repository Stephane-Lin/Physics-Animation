package Mechanics;

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
import javafx.util.Duration;
import javax.swing.JOptionPane;

public class Pendulum extends EventActions implements Interface{
    
    private EventHandler <MouseEvent> mouseAction = new MouseAction();
    private EventHandler<ActionEvent> buttonAction = new ButtonAction();
    private double mass, time, currentLength, highestY,
            newLength, stringX, stringY, zeroPosition,
            mechanicalE, kinecticE, potentialE, pivotX, pivotY;
    double ratio;
    private int switchInt;
    private boolean valid = false;  
    private BorderPane kinecticPane = new BorderPane();
    private BorderPane potentialPane = new BorderPane();
    private Graph graphP = new Graph();
    private Graph graphK = new Graph();
    private Timeline pendulumAnimationTimeline = new Timeline();
    private Scene pendulumAnimationScene;

    public Pendulum(){
        time = 0;
        line_pendulum.setEndX(500);
        line_pendulum.setEndY(200);
        reset();
    }

    @Override
    public Scene getScene(){ 
        setScene();
        return pendulumAnimationScene;
    }
    
    @Override
    public void setScene(){

        pendulumAnimationScene = new Scene(root_pendulum, sceneWidth, sceneHeight);
        setGraph();
        setButtonEvent();
        setTimelineEvent();
        setDrag();
        
        massTextField.setPrefWidth(200);
        lengthTextField.setPrefWidth(200);
        massTextField.setPromptText("Mass in g (Between 0-100))");
        lengthTextField.setPromptText("Length in cm (Between 0-500)");
        
        buttonGroup_pendulum.getChildren().addAll(massTextField, lengthTextField,
                startButtonPendulum, pauseButtonPendulum, resetButtonPendulum, backButtonPendulum);        
        buttonGroup_pendulum.setPadding(new Insets(25,25,25,25));
        buttonGroup_pendulum.setSpacing(10);

        root_pendulum.setTop(buttonGroup_pendulum);
        graphGroup.getChildren().addAll(kinecticPane, potentialPane);
        root_pendulum.setTop(buttonGroup_pendulum);
        root_pendulum.setBottom(graphGroup);
        root_pendulum.getChildren().addAll(line_pendulum,circle_pendulum);   
    }
    
    @Override
    public void setGraph(){
        potentialPane = graphP.initiateGraph("Potential Energy", 
                "Potential Energy", "Time (s)", "Energy (J)", 1, 1, 1);
        graphP.setBounds(50,50,0,0);
        kinecticPane = graphK.initiateGraph("Kinetic Energy", 
                "Kinectic Energy", "Time (s)", "Energy (J)", 1, 1, 1);
        graphK.setBounds(50,50,0,0);
    }
    
    @Override
    public void setButtonEvent(){
        startButtonPendulum.setOnAction(buttonAction);
        pauseButtonPendulum.setOnAction(buttonAction);
        resetButtonPendulum.setOnAction(buttonAction);        
    }
    
    @Override
    public void setTimelineEvent(){
        KeyFrame kf = new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {                   
            @Override
            public void handle(ActionEvent actionEvent){
                time+=1;
                switch(switchInt){
                    case 1: if(Double.parseDouble(df_2.
                            format(line_pendulum.getStartX())) == 500){
                                switchInt = 2;
                                break;
                            }
                            line_pendulum.setStartY(line_pendulum.getStartY()+1);
                            line_pendulum.setStartX(pivotX + 
                                    Math.sqrt(Math.pow(currentLength,2) 
                                    - Math.pow(stringY,2)));
                            break;
                    case 2: if(line_pendulum.getStartY() == highestY){
                                switchInt = 3;
                                break;
                            }
                            line_pendulum.setStartY(line_pendulum.getStartY()-1);
                            line_pendulum.setStartX(pivotX 
                                    - Math.sqrt(Math.pow(currentLength,2)
                                    - Math.pow(stringY,2)));                             
                            break;
                    case 3: if(line_pendulum.getStartX() == 500){
                                switchInt = 4;
                                break;
                            }
                            line_pendulum.setStartY(line_pendulum.getStartY()+1);
                            line_pendulum.setStartX(pivotX 
                                    - Math.sqrt(Math.pow(currentLength,2)
                                    - Math.pow(stringY,2)));
                            break;
                    case 4: if(line_pendulum.getStartY() == highestY){
                                switchInt = 1;
                                break;
                            }
                            line_pendulum.setStartY(line_pendulum.getStartY()-1);
                            line_pendulum.setStartX(pivotX 
                                    + Math.sqrt(Math.pow(currentLength,2)
                                    - Math.pow(stringY,2)));
                            break;
                }

                stringY = line_pendulum.getStartY()-pivotY;
                circle_pendulum.setCenterX(line_pendulum.getStartX());
                circle_pendulum.setCenterY(line_pendulum.getStartY());

                    potentialE = (zeroPosition-line_pendulum.getStartY())
                                *(mass/1000)*ACCELERATION;
                    kinecticE = mechanicalE - potentialE;
                    graphK.addDataToSeries(time, kinecticE, 1);                  
                    graphP.addDataToSeries(time, potentialE, 1);  

            }
        });
        
        pendulumAnimationTimeline.setCycleCount(Timeline.INDEFINITE);
        pendulumAnimationTimeline.getKeyFrames().add(kf);        
    }  
    
    public void setDrag(){
        circle_pendulum.setOnMouseDragged(mouseAction); 
    }

    class ButtonAction implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e){
            if(e.getSource().equals(startButtonPendulum)){
                startEvent();
            }else if(e.getSource().equals(pauseButtonPendulum)){
                pauseEvent();
            }else if(e.getSource().equals(resetButtonPendulum)){
                stopEvent();
            }
        }
    }
    
    @Override
    public void startEvent(){
        if(valid == false){
            valid = true;
            graphP.resetGraph();
            graphK.resetGraph();

            try{
                newLength = convertToDF
                        (Double.parseDouble(lengthTextField.getText()));
                currentLength =  convertToDF
                        (Math.sqrt(Math.pow(line_pendulum.getStartX()
                        -line_pendulum.getEndX(),2)
                        +Math.pow(line_pendulum.getStartY()
                        -line_pendulum.getEndY(),2)));
                mass = convertToDF(Double.parseDouble
                        (massTextField.getText())); 

                if(newLength < 0 || newLength > 500 
                        || mass < 0 || mass > 100){
                    throw new Exception();
                }

                setValues();
                
                zeroPosition = currentLength+200;

                zeroPosition = currentLength+200;
                mechanicalE = (mass/1000)*ACCELERATION*currentLength;

                setAllButtons(true);
                playAnimation();
            }catch(Exception o){
                JOptionPane.showMessageDialog(null, 
                        "Please enter a correct value for all inputs");
            }
        }else{
            setAllButtons(true);
            playAnimation();
        }    
    }
    
    @Override
    public void pauseEvent(){
        pendulumAnimationTimeline.pause();
        startButtonPendulum.setDisable(false);    
    }
    
    @Override
    public void stopEvent(){
        valid = false;
        pendulumAnimationTimeline.stop();
        setAllButtons(false);
        reset();
        graphK.resetGraph();
        graphP.resetGraph();    
    }
    
    @Override
    public void reset(){
        valid = false;
        time = 0;
        line_pendulum.setStartX(200);
        line_pendulum.setStartY(200);
        mass = 0;
        circle_pendulum.setCenterX(line_pendulum.getStartX());
        circle_pendulum.setCenterY(line_pendulum.getStartY()); 
        massTextField.clear();
        lengthTextField.clear();
        circle_pendulum.setDisable(false);
    }
    
    public void playAnimation(){             
        circle_pendulum.setDisable(true);
        pendulumAnimationTimeline.play();
    }
    
    public void setAllButtons(boolean valid){
        massTextField.setDisable(valid);
        lengthTextField.setDisable(valid);
        startButtonPendulum.setDisable(valid);
    }

    public void setValues(){
        ratio = newLength/currentLength;
        currentLength = newLength;
        
        pivotX = line_pendulum.getEndX();
        pivotY = line_pendulum.getEndY();

        if(line_pendulum.getStartX() < pivotX){
            switchInt = 3;
        }else{
            switchInt = 1;
        }

        stringX = Math.abs(line_pendulum.getStartX()-pivotX);
        stringY = Math.abs(line_pendulum.getStartY()-pivotY);

        stringX = ratio*stringX;
        stringY = ratio*stringY;
        
        line_pendulum.setStartX(pivotX+stringX);
        line_pendulum.setStartY(pivotY+stringY);  
        highestY = line_pendulum.getStartY();
        circle_pendulum.setCenterX(line_pendulum.getStartX());
        circle_pendulum.setCenterY(line_pendulum.getStartY());
    }

    public double convertToDF(double value){
        double valueDF = Double.parseDouble(df_2.format(value));
        return valueDF;
    }

    class MouseAction implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent me){
            if(me.getSceneY() >= 200 && me.getSceneX() > 0 
                    && me.getSceneX() < 1000 && me.getSceneY() < 1000){
                line_pendulum.setStartX(me.getSceneX());
                line_pendulum.setStartY(me.getSceneY());
                circle_pendulum.setCenterX(line_pendulum.getStartX());
                circle_pendulum.setCenterY(line_pendulum.getStartY());
                lengthTextField.setText(df_2.
                        format(Math.sqrt(Math.pow(line_pendulum.getStartX()
                        -line_pendulum.getEndX(),2)+Math.pow(line_pendulum.getStartY()
                        -line_pendulum.getEndY(),2))));              
            }           
        }
     }
     
    @Override
     public Button getReturnButton(){
        return backButtonPendulum;
     }
}