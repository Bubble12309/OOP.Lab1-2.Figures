package entry;

import drawable.Drawable;
import drawable.DrawableList;
import javax.swing.*;
import java.awt.*;

import java.awt.image.BufferedImage;

public class GCanvas extends JPanel {
    //------------------------------------------------------------------------------------------------------------------
    //--------------------------------------Все поля, что касается отрисовки фигур--------------------------------------
    //------------------------------------------------------------------------------------------------------------------

    private int mode;

    public void setMode(int mode) {
        if (this.mode != mode) {
            sampleFigure = null;
        }
        this.mode = mode;
    }

    public int getMode() {
        return this.mode;
    }

    private Color currColor;

    public void setCurrColor(Color currColor) {
        this.currColor = currColor;
    }

    public Color getCurrColor() {
        return this.currColor;
    }

    private Drawable sampleFigure;

    public void setSample(Drawable sampleFigure) {
        this.sampleFigure = sampleFigure;
    }

    public Drawable getSample() {
        return this.sampleFigure;
    }

    private final DrawableList drawableList;

    public DrawableList getDrawableList() {
        return this.drawableList;
    }

    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------Для сохранения картинги в джипеге-----------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    public BufferedImage getBufferedImage() throws AWTException {
        BufferedImage bi = new Robot().createScreenCapture(this.getBounds());
        Graphics2D g2d = bi.createGraphics();
        this.paint(g2d);
        return bi;
    }

    GCanvas() {
        this.setMode(JFigureButton.NONE);
        drawableList = new DrawableList();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawableList.drawAll(g);
        if (sampleFigure != null) sampleFigure.draw(g);
    }
}