
package Interface;

import javafx.scene.Scene;
import javafx.scene.control.Button;

public abstract class EventActions {
    protected void setScene(){};
    protected void setGraph(){};
    protected void setButtonEvent(){};
    protected void setTimelineEvent(){};
    protected void startEvent(){};
    protected void pauseEvent(){};
    protected void stopEvent(){};
    protected void helpEvent(){};
    protected void reset(){};
    protected double convertToDF(double value){return 0;}; 
    protected Scene getScene(){return null;};
    protected Button getReturnButton(){return null;};
}
