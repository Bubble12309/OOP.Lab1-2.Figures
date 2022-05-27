package figures;
import java.awt.*;

public class MLine extends MPolyline {

    public MLine(Color lineColor, int penWidth, Point p1, Point p2) {
        super(lineColor, penWidth, p1, p2);
        setPointsRequired(2);
    }
}