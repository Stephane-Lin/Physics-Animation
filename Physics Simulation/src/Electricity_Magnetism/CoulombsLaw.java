
package Electricity_Magnetism;

import Graph.Graph;
import Interface.EventActions;
import Interface.Interface;
import static Interface.Interface.helpButtonCoulombs;
import static Interface.Interface.resetButtonCoulombs;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javax.swing.JOptionPane;

public class CoulombsLaw extends EventActions implements Interface {
    
    private EventHandler <MouseEvent> mouseAction = new MouseAction();
    private EventHandler <ActionEvent> buttonAction = new ButtonAction();
    private Graph graph = new Graph();
    
    double particleCharge;  
    private double particleDraggableX;
    private double particleDraggableY;
    private double pointChargeDraggableX;
    private double pointChargeDraggableY;
    private double pointChargePosX;
    private double pointChargePosY;
    private double distance;
    private double particleXpos;
    private double particleYpos;
    private double distanceX;
    private double distanceY;
    private double slopeLine;    
    private double force;
    private Scene coulombsAnimationScene;
    private BorderPane graphPane = new BorderPane();
    
    @Override
    public Scene getScene(){
        setScene();
        return coulombsAnimationScene;
    }
    @Override    
    public void setScene(){
        coulombsAnimationScene = new Scene(root_coulombs, sceneWidth, sceneHeight);
        setGraph();
        setButtonEvent();
        setTimelineEvent();

        pointCharge.setCenterX(PCINIT_X);
        pointCharge.setCenterY(INIT_Y_Coulombs);
        pointCharge.setFill(Color.GREEN);

        particle.setCenterX(INIT_X_coulombs);
        particle.setCenterY(INIT_Y_Coulombs);

        electroForce.setStartX(pointCharge.getCenterX());
        electroForce.setStartY(pointCharge.getCenterY());
        electroForce.setEndX(particle.getCenterX());
        electroForce.setEndY(particle.getCenterY()); 
        electroForce.setStroke(Color.GREENYELLOW);

        inputCharge1.setPromptText("Enter charge 1");
        buttonGroup_coulombs.setSpacing(10);
        buttonGroup_coulombs.getChildren().addAll(charge1,inputCharge1,
                setButtonCoulombs,resetButtonCoulombs,helpButtonCoulombs,backButtonCoulombs);
        root_coulombs.setTop(buttonGroup_coulombs);
        root_coulombs.getChildren().addAll(pointCharge,particle,electroForce);

        for(int i = 0; i < 2; i++){
            particle.setOnMouseDragged(mouseAction);      
        }
        pointCharge.setOnMouseDragged(mouseAction); 
}
    @Override
    public void setGraph(){
        graphPane = graph.initiateGraph("Coulomb's Law", "Coulomb's", "Force", "Distance", 5, 5, 1);
        graph.setBounds(101,70,0,0);
        root_coulombs.setBottom(graphPane);
    }
    @Override
    public void setButtonEvent(){
        setButtonCoulombs.setOnAction(buttonAction);
        resetButtonCoulombs.setOnAction(buttonAction);
        helpButtonCoulombs.setOnAction(buttonAction);
        backButtonCoulombs.setOnAction(buttonAction);        
    }       

    class ButtonAction implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e){
            if(e.getSource().equals(setButtonCoulombs)){          
                startEvent();      
            }
            if(e.getSource().equals(resetButtonCoulombs)){
                stopEvent();
            }
            if(e.getSource().equals(helpButtonCoulombs)){
                helpEvent();
            }
        }
    }
   
    class MouseAction implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent me){
            
            directionForce(1);
            
            pointChargePosX = pointCharge.getTranslateX()+pointCharge.getCenterX();
            pointChargePosY = pointCharge.getTranslateY()+pointCharge.getCenterY();        
                                   
            pointChargeDraggableX = me.getSceneX()- pointCharge.getLayoutX() - pointCharge.getCenterX();
            pointChargeDraggableY = me.getSceneY()- pointCharge.getLayoutY() - pointCharge.getCenterY();
                      
            electroForce.setStartX(pointChargePosX);
            electroForce.setStartY(pointChargePosY);

            particleXpos = Math.abs(particle.getTranslateX() + particle.getCenterX());
            particleYpos = Math.abs(particle.getTranslateY() + particle.getCenterY());

            particleDraggableX = me.getSceneX()- particle.getLayoutX() - particle.getCenterX();
            particleDraggableY = me.getSceneY()- particle.getLayoutY() - particle.getCenterY();
                
            if(me.getSource().equals(particle)){
                particle.setTranslateX(particleDraggableX);
                particle.setTranslateY(particleDraggableY);
                }
            
            if(me.getSource().equals(pointCharge)){
                pointCharge.setTranslateX(pointChargeDraggableX);
                pointCharge.setTranslateY(pointChargeDraggableY);
                
                distance = Math.sqrt(Math.pow(Math.abs(pointChargePosX - (particle.getCenterX() + particle.getTranslateX())),2) 
                    + Math.pow(Math.abs(pointChargePosY - (particle.getCenterY() - particle.getTranslateY())),2));
                
                    distanceX = Math.abs(particleXpos - pointChargePosX);
                    distanceY = Math.abs(particleYpos - pointChargePosY);
                slopeLine = (Math.abs((particleYpos)- me.getSceneY()) 
                    / Math.abs((particleXpos) - me.getSceneX()));     
            }
        }
    }
    
    @Override
    public void startEvent(){
        particleCharge = Double.parseDouble(inputCharge1.getText());
        if(particleCharge < 0){
            particle.setFill(Color.RED);
        }else{
            particle.setFill(Color.BLUE);
        }                
    }    
    
    @Override
    public void stopEvent(){
        try{    
        for(int i = 0; i < 2; i++){
        particle.setTranslateX(INIT_X_coulombs + i*INIT_X_coulombs);
        particle.setTranslateY(INIT_Y_Coulombs); 
        electroForce.setEndX(pointCharge.getCenterX());
        electroForce.setEndY(pointCharge.getCenterY());
        electroForce.setEndX(particle.getCenterX());
        electroForce.setEndY(particle.getCenterY());
        }
        pointCharge.setCenterX(pointChargeDraggableX + pointCharge.getTranslateX());
        pointCharge.setCenterY(pointChargeDraggableY + pointCharge.getTranslateY());
    
        }catch(Exception a){
            JOptionPane.showMessageDialog(null,
                "Invalid input", "Error",
                JOptionPane.ERROR_MESSAGE);  
        }
    }
    @Override
    public void helpEvent(){
        JOptionPane.showMessageDialog(null,
            "Input the charge of the particle and move the test charge around(green) around the particle"
            + "red = negative, blue = positive.","Help",
            JOptionPane.INFORMATION_MESSAGE);     
    }        
    
    public int particleCharge(){    
        int charge;
        if(particleCharge < 0){ 
            charge = -1;          
        }else{
            charge = 1;
        } 
        return charge;
    }
   
    public void directionForce(final int x){
        if(pointChargePosX >= particleXpos && pointChargePosY < particleYpos){
            electroForce.setEndX((particleYpos+(particleCharge()*230))/slopeLine);
            electroForce.setEndY(((slopeLine*particleXpos)-(particleCharge()*230)));       
        }
        if(pointChargePosX < particleXpos && pointChargePosY <= particleYpos){
            electroForce.setEndX(((particleYpos-(particleCharge()*230))/slopeLine));
            electroForce.setEndY(((slopeLine*particleXpos) - (particleCharge()*230)));
        }
        if(pointChargePosX <= particleXpos && pointChargePosY > particleYpos){
            electroForce.setEndX(((particleYpos-(particleCharge()*230))/slopeLine));
            electroForce.setEndY(((slopeLine*particleXpos) +(particleCharge()*230)));
        }
        electroForce.setEndX(((particleYpos+(particleCharge()*230))/slopeLine));
        electroForce.setEndY(((slopeLine*particleXpos) +(particleCharge()*230)));
    }

    public double resultantForce(){
        force = (9*10)*((particleCharge*PCcharge)/Math.pow((distance-distance),2));
        return force;
    }
        
    @Override
    public Button getReturnButton(){
        return backButtonCoulombs;
    }
}