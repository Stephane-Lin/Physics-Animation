
package Mechanics;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import Graph.Graph;
import Interface.EventActions;
import Interface.Interface;
import javafx.scene.Scene;

public class ProjectileMotion extends EventActions implements Interface{   

    private double initialVX, initialVY, height, displacementY, displacementX, time, timeToTop, maxDisplacementY, initialVelocity, initialAngle, heightTime;
    
    private BorderPane graphPane = new BorderPane();
    private Graph graph = new Graph();
    private Timeline projectileAnimationTimeline = new Timeline();
    private EventHandler<ActionEvent> buttonAction = new ButtonAction();
    private Scene projectileAnmiationScene;
    
    @Override
    public Scene getScene(){
        setScene();
        return projectileAnmiationScene;
    }
    
    @Override
    public void setScene(){
        
        projectileAnmiationScene = new Scene(root_PM,sceneWidth,sceneHeight);
        setGraph();
        setButtonEvent();
        setTimelineEvent();        
        resetCircle();
         
        inputAngleProjectile.setPromptText("Input Angle in degrees (Between 0-90)");
        inputVelocityProjectile.setPromptText("Input Initial Velocity in m/s (Between 0-50)");
        inputHeightProjectile.setPromptText("Input Height in m (Optional, Between 0-100)");

        buttonGroup_PM.setPadding(new Insets(25,25,25,25));
        buttonGroup_PM.setSpacing(10);
        buttonGroup_PM.getChildren().addAll(startButtonProjectile,pauseButtonProjectile,resetButtonProjectile,backButtonProjectile,inputAngleProjectile,inputVelocityProjectile,inputHeightProjectile);
        root_PM.setTop(buttonGroup_PM);
        root_PM.setRight(graphPane);
        root_PM.getChildren().add(circleProjectile);
    }
    
    @Override
    public void setGraph(){
        graphPane = graph.initiateGraph("Projectile Motion", "Displacement","X Displacement", "Y Displacement", 5, 5, 1);
        graph.setBounds(100, 40, 0, 0);        
    }
    
    @Override
    public void setButtonEvent(){
        startButtonProjectile.setOnAction(buttonAction); 
        pauseButtonProjectile.setOnAction(buttonAction);
        resetButtonProjectile.setOnAction(buttonAction);        
    }
    
    @Override
    public void setTimelineEvent(){
        KeyFrame kf = new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {                   
            @Override
            public void handle(ActionEvent actionEvent){                     
                if(time != Double.parseDouble(df_2.format(2*timeToTop+heightTime))){
                    displacementX = Double.parseDouble(df_2.format(initialVX*time));
                    displacementY = height+Double.parseDouble(df_2.format((initialVY*time)-((0.5)*ACCELERATION*Math.pow(time, 2))));
                    circleProjectile.setCenterX(10+4*displacementX);
                    circleProjectile.setCenterY(730-4*displacementY);
                }else{
                    projectileAnimationTimeline.stop();
                    startButtonProjectile.setDisable(false);
                }
                time = +Double.parseDouble(df_2.format(time + 0.01));
                graph.addDataToSeries(displacementX, displacementY, 1);    
            }
        });
        projectileAnimationTimeline.setCycleCount(Animation.INDEFINITE);
        projectileAnimationTimeline.getKeyFrames().add(kf);
    }
    
    public void resetCircle(){
        circleProjectile.setCenterX(10);
        circleProjectile.setCenterY(730);
    }

    class ButtonAction implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e){
            if(e.getSource().equals(startButtonProjectile)){
                startEvent();
            }else if(e.getSource().equals(pauseButtonProjectile)){
                pauseEvent();
            }else if(e.getSource().equals(resetButtonProjectile)){
                stopEvent();
            }                
        }
    }; 
    
    @Override
    public void startEvent(){
        graph.resetGraph();
        try{
            //--INITIALIZING
            height = 0; time = 0; 
            displacementX = 0;   displacementY = 0;

            initialVelocity = Double.parseDouble(inputVelocityProjectile.getText());
            initialAngle = Double.parseDouble(inputAngleProjectile.getText());

            if(!inputHeightProjectile.getText().isEmpty()){
                height = Double.parseDouble(inputHeightProjectile.getText());
                if(height < 0 || height > 100){
                    throw new ArithmeticException();
                }
            }

            if( initialAngle > 90 ||  initialAngle < 0 || initialVelocity <= 0 || initialVelocity > 50){ 
                throw new ArithmeticException();
            }

            initialVX = initialVelocity*Math.cos(Math.toRadians(initialAngle));  
            initialVY = initialVelocity*Math.sin(Math.toRadians(initialAngle));
            timeToTop = Double.parseDouble(df_2.format(initialVY/ACCELERATION));

            maxDisplacementY = height+ initialVY*timeToTop - (0.5)*ACCELERATION*Math.pow(timeToTop,2);
            heightTime =  Double.parseDouble(df_2.format(Math.sqrt(maxDisplacementY/(0.5*ACCELERATION)) - timeToTop));
            startButtonProjectile.setDisable(true);
            projectileAnimationTimeline.play();
        }catch(ArithmeticException a){
            JOptionPane.showMessageDialog(null, "Please enter a correct value for each box.");
        }catch(Exception o){
            JOptionPane.showMessageDialog(null, "Please enter a value for the initial velocity and the angle");
        }        
    }
    
    @Override
    public void pauseEvent(){
        projectileAnimationTimeline.pause();
        startButtonProjectile.setDisable(false);        
    }
    
    @Override
    public void stopEvent(){
        projectileAnimationTimeline.stop();
        reset();
    }
    
    @Override
    public void reset(){
        resetCircle();
        graph.resetGraph();
        inputAngleProjectile.clear();
        inputVelocityProjectile.clear();
        inputHeightProjectile.clear();        
    }
    
    @Override
    public Button getReturnButton(){
        reset();
        return backButtonProjectile;
    }
}