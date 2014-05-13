
package Interface;

import java.text.DecimalFormat;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.stage.Screen;

public interface Interface {
    
//Number values
    int INDEX_ZERO = 0;
    int INDEX_ONE = 1;
    int INDEX_TWO = 2;
    int INDEX_THREE = 3;
    int INDEX_FOUR = 4;
    int INDEX_FIVE = 5;
    int INDEX_SIX = 6;    
    double sceneWidth = Screen.getPrimary().getVisualBounds().getWidth();
    double sceneHeight = Screen.getPrimary().getVisualBounds().getHeight();    
    
//String values
    String start = "Start"; String stop = "Stop";
    String pause = "Pause"; String reset = "Reset";
    String back = "Return"; String help = "Help";
    String mechanics = "Mechanics"; String waves = "Waves & Modern Physics";
    String electricity = "Electricity & Magnetism"; String exit = "Exit";    
    String projectile = "Projectile Motion"; String pendulum = "Pendulum";
    String oscillation = "Simple Harmonic Motion"; String doppler = "Doppler Effect";
    String coulombs = "Coulombs Law"; String circuit = "DC Circuit";
    String thirdChoice = "Third Choice"; String error = "INPUT ERROR!";
    
//Common variables        
    DecimalFormat df_1 = new DecimalFormat("#.#");
    DecimalFormat df_2 = new DecimalFormat("#.##");
    
//Menu Layouts final values
    BorderPane[] mainPages = {new BorderPane(),new BorderPane(),new BorderPane(),new BorderPane()};
    Button[] mainPageButtonSet = {new Button(),new Button(),new Button(),new Button()};
    Button[] mechanicsButtonSet = {new Button(),new Button(),new Button(),new Button()};
    Button[] wavesButtonSet = {new Button(),new Button(),new Button(),new Button()};
    Button[] electricityButtonSet = {new Button(),new Button(),new Button(),new Button()};
    VBox[] buttonGroup = {new VBox(),new VBox(),new VBox(),new VBox()};
    String[] labels = {null,null,null,null};    
    
//Pendulum final values
    Button startButtonPendulum = new Button(start);
    Button pauseButtonPendulum = new Button(pause);
    Button resetButtonPendulum = new Button(reset);
    Button backButtonPendulum = new Button(back);    
    Circle circle_pendulum = new Circle(10);
    Line line_pendulum = new Line();
    TextField massTextField = new TextField();
    TextField lengthTextField = new TextField();
    TextField angleTextField_pendulum = new TextField();
    HBox buttonGroup_pendulum = new HBox();
    HBox graphGroup = new HBox();
    BorderPane root_pendulum = new BorderPane(); 
    BorderPane border = new BorderPane();
    
//Projectile Motion final values
    Button startButtonProjectile = new Button(start);
    Button pauseButtonProjectile = new Button(pause);
    Button resetButtonProjectile = new Button(reset);
    Button backButtonProjectile = new Button(back);
    double ACCELERATION = 9.8;
    Circle circleProjectile = new Circle(10);
    VBox buttonGroup_PM = new VBox();
    TextField inputAngleProjectile = new TextField();
    TextField inputVelocityProjectile = new TextField();
    TextField inputHeightProjectile = new TextField();
    BorderPane root_PM = new BorderPane();
    
//Simple Harmonic Motion final values
    Button startButtonWaves = new Button(start);
    Button pauseButtonWaves = new Button(pause);
    Button resetButtonWaves = new Button(reset);
    Button backButtonWaves = new Button(back);
    Button stopButtonWaves = new Button(stop);
    Button helpButtonWaves = new Button(help);
    int INIT_X = 65, INIT_Y = 350;
    int DEFAULT_AMPLITUDE = 70, DEFAULT_FREQUENCY = 2;
    double INIT_TIME = Math.PI/200;
    Circle[] waveString = new Circle[120];
    HBox buttonGroup_SHM = new HBox();
    TextField inputAmplitude_SHM = new TextField();
    TextField inputFrequency_SHM = new TextField();
    Label ampltiudeUnits = new Label("meters");
    Label frequencyUnits_SHM = new Label("Hz");
    BorderPane root_SHM = new BorderPane();
    
//Doppler Effect final values
    Button startButtonDoppler = new Button(start);
    Button pauseButtonDoppler = new Button(pause);
    Button resetButtonDoppler = new Button(reset);
    Button backButtonDoppler = new Button(back);    
    int INIT_X_Source = 400; int INIT_Y_Source = 400;
    int INIT_X_OBSERVER = 600; int INIT_Y_OBSERVER = 350;
    double fadeRate = 0.003;
    TextField inputVelocitySource = new TextField();
    TextField inputVelocityObserver = new TextField();
    TextField inputFrequency_doppler = new TextField();
    Label sourceVelocityUnits = new Label("x 10^2 m/s");
    Label observerVelocityUnits = new Label("x 10^2 m/s");
    Label frequencyUnits_doppler = new Label("kHz");
    BorderPane root_doppler = new BorderPane();
    HBox buttonGroup_doppler = new HBox();

//Coulomb's law final values
    Button backButtonCoulombs = new Button(back);
    Button setButtonCoulombs = new Button("Set");
    Button resetButtonCoulombs = new Button(reset);
    Button helpButtonCoulombs = new Button(help);    
    double PCINIT_X = 400;
    double PCcharge = 1;
    double INIT_X_coulombs = 500;
    double INIT_Y_Coulombs = 300;
    Circle particle = new Circle(10);
    Circle pointCharge = new Circle(5);
    Line  electroForce = new Line();
    Line electricForce = new Line();
    HBox buttonGroup_coulombs = new HBox();
    TextField inputCharge1 = new TextField();
    Label charge1 = new Label("Charge 1");
    Label charge2 = new Label("Charge 2");    
    BorderPane root_coulombs = new BorderPane();
    
//DC Circuit final values
    Button startButtonCircuit = new Button(start);
    Button resetButtonCircuit = new Button(reset);
    Button stopButtonCircuit = new Button(stop);
    Button backButtonCircuit = new Button(back);
    Button helpButtonCircuit = new Button(help);    
    double POINT1X = 205; double POINT1Y = 300; double POINT2Y = 200; 
    double POINT3X = 400;double POINT5X = 295;
    double DEFAULT_VOLTAGE = 1; double DEFAULT_CURRENT = 1;
    double DEFAULT_RESISTANCE = 1;
    Path path = new Path();
    Circle [] electron = new Circle[800];
    Line [] brightness = new Line[3];    
    TextField [] inputs = new TextField[3];
    Label voltage_Label = new Label("Voltage");
    Label resistance_Label = new Label("Resistance");
    Label current_Label = new Label("Current");
    HBox buttonGroup_circuit = new HBox();
    Image lightbulb = new Image("light.jpg");
    Image battery = new Image("battery.jpg");
    ImageView [] iv = new ImageView[2];
    BorderPane root_circuit = new BorderPane();
}