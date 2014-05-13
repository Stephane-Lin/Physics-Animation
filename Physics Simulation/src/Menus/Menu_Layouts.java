
package Menus;

import Interface.Interface;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Menu_Layouts implements Interface{
    
    private Button[] temporaryButtonSet;
    private Text[] title = new Text[4];

    public void setLayout(int pos){
        switch(pos){
            case 0: 
                labels[INDEX_ZERO] = projectile; labels[INDEX_ONE] = pendulum;
                labels[INDEX_TWO] = thirdChoice; labels[INDEX_THREE] = back;
                temporaryButtonSet = mechanicsButtonSet; break;
            case 1: 
                labels[INDEX_ZERO] = oscillation; labels[INDEX_ONE] = doppler; 
                labels[INDEX_TWO] = thirdChoice; labels[INDEX_THREE] = back;
                temporaryButtonSet = wavesButtonSet; break;
            case 2: 
                labels[INDEX_ZERO] = coulombs; labels[INDEX_ONE] = circuit; 
                labels[INDEX_TWO] = thirdChoice; labels[INDEX_THREE] = back;
                temporaryButtonSet = electricityButtonSet; break;
            default:
                labels[INDEX_ZERO] = mechanics; labels[INDEX_ONE] = waves;
                labels[INDEX_TWO] = electricity; labels[INDEX_THREE] = exit; 
                temporaryButtonSet = mainPageButtonSet; break;  
        }
        
        for(int i=0; i<temporaryButtonSet.length; i++){
            temporaryButtonSet[i].setPrefSize(300, 50);
            temporaryButtonSet[i].setText(labels[i]);
        }
        
        buttonGroup[pos].getChildren().addAll(temporaryButtonSet);
        buttonGroup[pos].setAlignment(Pos.CENTER);
        buttonGroup[pos].setSpacing(30);
        buttonGroup[pos].setPadding(new Insets(275,0,0,0));
        
        title[0] = new Text("Mechanics");
        title[1] = new Text("Waves and Modern Physics");
        title[2] = new Text("Electricity & magnetism");
        title[3] = new Text("Physics Animations");
        
        for(int i = 0; i < 4; i++){
            title[i].setFont(Font.loadFont("file:resources/fonts/title.ttf", 60));     
            mainPages[i].setPadding(new Insets(50,0,50,0));
            mainPages[i].setAlignment(title[i], Pos.TOP_CENTER);
            mainPages[i].setTop(title[i]);
        }
        mainPages[pos].setCenter(buttonGroup[pos]);
    }
    
    public BorderPane getLayout(int pos){
        return mainPages[pos];
    }
    
    public Button[] getButtons(int pos){
        if(pos == INDEX_ZERO){
            return mechanicsButtonSet;
        }else if(pos == INDEX_ONE){
            return wavesButtonSet;
        }else if(pos == INDEX_TWO){
            return electricityButtonSet;
        }else{
            return mainPageButtonSet;
        }
    }
}