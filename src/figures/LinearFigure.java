package figures;
import java.awt.Color;

public abstract class LinearFigure extends Figure {
    private Color lineColor;
    private int penWidth;

    public Color getLineColor() {
        return this.lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public int getPenWidth() {
        return this.penWidth;
    }

    public void setPenWidth(int penWidth) {
        this.penWidth = penWidth;
    }

    protected LinearFigure(Color lineColor, int penWidth) {
        this.setLineColor(lineColor);
        this.setPenWidth(penWidth);
    }
}