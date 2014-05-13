package Menus;

import Electricity_Magnetism.CoulombsLaw;
import Electricity_Magnetism.DCcircuit;
import Interface.Interface;
import Mechanics.Pendulum;
import Waves_animations.DopplerEffect;
import Waves_animations.SimpleHarmonicMotion;
import Mechanics.ProjectileMotion;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class Main_Page extends Application implements Interface{

//Create Main Page stage and Main Menu scenes
    private Stage mainPage;
    private Scene[] mainPageScene = new Scene[INDEX_FOUR];
    private Scene[] animationPageScenes = new Scene[INDEX_SIX];
    
//Create class objects
    private Menu_Layouts menuLayouts = new Menu_Layouts();
    private ProjectileMotion projectileAnimation = new ProjectileMotion();
    private Pendulum pendulumAnimation = new Pendulum();
    private SimpleHarmonicMotion wavesAnimation = new SimpleHarmonicMotion();
    private DopplerEffect dopplerAnimation = new DopplerEffect();
    private CoulombsLaw coulombsAnimation = new CoulombsLaw();
    private DCcircuit circuitAnimation = new DCcircuit();
    
//Create Event Handlers    
    private EventHandler<ActionEvent> mainPageButtonAction = new mainPageAction();
    private EventHandler<ActionEvent> staticAction = new staticAction();
    private EventHandler<ActionEvent> animationPageAction = new animationPageAction();
    
//Create Button arrays for each page
    private Button[] mainPageChoices;
    private Button[] mechanicsChoices;
    private Button[] wavesChoices;
    private Button[] electricityChoices;
    
    @Override
    public void start(Stage stage) {
        mainPage = stage;
        setScenes();
        setButtonActions();
        mainPage.setScene(mainPageScene[INDEX_THREE]);
        mainPage.show();
    }

    public void setScenes(){
//Get layouts for the different scenes and set each one in the appropriate array
        for(int i=0; i<mainPageScene.length; i++){
            menuLayouts.setLayout(i);
            mainPageScene[i] = new Scene(menuLayouts.getLayout(i),sceneWidth,sceneHeight);
            mainPageScene[i].getStylesheets().add(Main_Page.class.getResource("Menu_Design.css").toExternalForm());
        }
//Set animation scenes in the array
        animationPageScenes[INDEX_ZERO] = projectileAnimation.getScene();
        animationPageScenes[INDEX_ONE] = pendulumAnimation.getScene();
        animationPageScenes[INDEX_TWO] = wavesAnimation.getScene();
        animationPageScenes[INDEX_THREE] = dopplerAnimation.getScene();
        animationPageScenes[INDEX_FOUR] = coulombsAnimation.getScene();
        animationPageScenes[INDEX_FIVE] = circuitAnimation.getScene();
        for(int j=0; j<6; j++){
            animationPageScenes[j].getStylesheets().add(Main_Page.class.getResource("Menu_Design.css").toExternalForm());
        }
    }
    
    public void setButtonActions(){
//Get buttons from the Menu_Layouts class and set them in the appropriate array        
        mainPageChoices = menuLayouts.getButtons(INDEX_THREE);
        mechanicsChoices = menuLayouts.getButtons(INDEX_ZERO);
        wavesChoices = menuLayouts.getButtons(INDEX_ONE);
        electricityChoices = menuLayouts.getButtons(INDEX_TWO);
        
//Set the Event Handler for each button in the main page
        for(int i=0; i<mainPageChoices.length; i++){
            mainPageChoices[i].setOnAction(mainPageButtonAction);
        }

//Set the Event Handler for each button in the menu pages
        for(int i=0; i<4; i++){
            //Buttons that display an animation page
            if(i<2){
                mechanicsChoices[i].setOnAction(animationPageAction);
                wavesChoices[i].setOnAction(animationPageAction);
                electricityChoices[i].setOnAction(animationPageAction);
            //Buttons that returns to the main page or display "Under Construction"
            }else{
                mechanicsChoices[i].setOnAction(staticAction);
                wavesChoices[i].setOnAction(staticAction);
                electricityChoices[i].setOnAction(staticAction);                
            }
        }
    }
    
    class mainPageAction implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e){
            if(e.getSource().equals(mainPageChoices[INDEX_ZERO]) || e.getSource().equals(projectileAnimation.getReturnButton())
                    || e.getSource().equals(pendulumAnimation.getReturnButton())){
                mainPage.setScene(mainPageScene[INDEX_ZERO]);
            }
            if(e.getSource().equals(mainPageChoices[INDEX_ONE]) || e.getSource().equals(wavesAnimation.getReturnButton())
                    || e.getSource().equals(dopplerAnimation.getReturnButton())){
                mainPage.setScene(mainPageScene[INDEX_ONE]);
            }
            if(e.getSource().equals(mainPageChoices[INDEX_TWO]) || e.getSource().equals(circuitAnimation.getReturnButton())
                    || e.getSource().equals(coulombsAnimation.getReturnButton())){
                mainPage.setScene(mainPageScene[INDEX_TWO]);
            }
            if(e.getSource().equals(mainPageChoices[INDEX_THREE])){
                System.exit(0);
            }
        }
    }
    
    class staticAction implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e){
            if(e.getSource() == mechanicsChoices[INDEX_TWO] || 
                    e.getSource() == wavesChoices[INDEX_TWO] || 
                    e.getSource() == electricityChoices[INDEX_TWO]){
                JOptionPane.showMessageDialog(null, "UNDER CONSTRUCTION");
            }            
            if(e.getSource() == mechanicsChoices[INDEX_THREE] || 
                    e.getSource() == wavesChoices[INDEX_THREE] || 
                    e.getSource() == electricityChoices[INDEX_THREE]){
                mainPage.setScene(mainPageScene[INDEX_THREE]);
            }
        }
    }
    
    class animationPageAction implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e){
            if(e.getSource().equals(mechanicsChoices[INDEX_ZERO])){
                mainPage.setScene(animationPageScenes[INDEX_ZERO]);
                projectileAnimation.getReturnButton().setOnAction(mainPageButtonAction);
            }
            if(e.getSource().equals(mechanicsChoices[INDEX_ONE])){
                mainPage.setScene(animationPageScenes[INDEX_ONE]);
                pendulumAnimation.getReturnButton().setOnAction(mainPageButtonAction);
            }
            if(e.getSource().equals(wavesChoices[INDEX_ZERO])){
                mainPage.setScene(animationPageScenes[INDEX_TWO]);
                wavesAnimation.getReturnButton().setOnAction(mainPageButtonAction);
            }
            if(e.getSource().equals(wavesChoices[INDEX_ONE])){
                mainPage.setScene(animationPageScenes[INDEX_THREE]);
                dopplerAnimation.getReturnButton().setOnAction(mainPageButtonAction);
            }
            if(e.getSource().equals(electricityChoices[INDEX_ZERO])){
                mainPage.setScene(animationPageScenes[INDEX_FOUR]);
                coulombsAnimation.getReturnButton().setOnAction(mainPageButtonAction);
            }
            if(e.getSource().equals(electricityChoices[INDEX_ONE])){
                mainPage.setScene(animationPageScenes[INDEX_FIVE]);
                circuitAnimation.getReturnButton().setOnAction(mainPageButtonAction);
            }
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}