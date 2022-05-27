package entry;

import javax.swing.*;

public class JFigureButton extends JToggleButton {

    public static final int NONE = 0;
    public static final int CIRCLE = 1;
    public static final int ELLIPSE = 2;
    public static final int RECTANGLE = 3;
    public static final int SQUARE = 4;
    public static final int LINE = 5;
    public static final int POLYLINE = -1;
    public static final int POLYGON = -2;

    private int mode;
    public void setMode(int mode){this.mode = mode;}
    public int getMode(){return this.mode;}

    public JFigureButton(int mode){
        super();
        setMode(mode);
    }
    public JFigureButton(){
        super();
        setMode(JFigureButton.NONE);
    }
}