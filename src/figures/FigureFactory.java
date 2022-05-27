package figures;

import entry.JFigureButton;
import java.awt.*;

public final class FigureFactory {

    public static Figure getInitInstance(int mode, Color color, int penWidth, Point p1, Point p2) {
        return switch (mode) {
            case JFigureButton.CIRCLE -> {
                int dx = p2.x - p1.x;
                int dy = p2.y - p1.y;
                yield new MCircle(color, p1, Math.max(dx, dy));
            }
            case JFigureButton.ELLIPSE -> new MEllipse(color, p1, p2);
            case JFigureButton.SQUARE -> {
                int dx = p2.x - p1.x;
                int dy = p2.y - p1.y;
                yield new MSquare(color, p1, Math.max(dx, dy));
            }
            case JFigureButton.RECTANGLE -> {
                if ((p2.x <= p1.x) || (p2.y <= p1.y))
                    p2 = p1;
                yield new MRectangle(color, p1, p2);
            }
            case JFigureButton.LINE -> new MLine(color, penWidth, p1, p2);
            case JFigureButton.POLYLINE -> new MPolyline(color, penWidth, p1, p2);
            case JFigureButton.POLYGON -> new MPolygon(color, p1, p2);
            default -> null;
        };
    }

    public static Figure getInitInstance(int mode, Color color, Point p1, Point p2) {
        return getInitInstance(mode, color, 1, p1, p2);
    }
}