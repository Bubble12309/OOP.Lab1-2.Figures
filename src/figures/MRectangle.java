package figures;

import java.awt.*;

public class MRectangle extends MPolygon {
    public MRectangle(Color fill, Point topLeft, Point bottomRight) {
        super(fill,
                topLeft,
                new Point(bottomRight.x, topLeft.y),
                bottomRight,
                new Point(topLeft.x, bottomRight.y));
        setPointsRequired(4);
    }

    @Override
    public void recalculate(Point newPoint) {
        var points = this.getPoints();
        Point topLeft = points.get(0);
        Point topRight = new Point(newPoint.x, topLeft.y);
        Point bottomLeft = new Point(topLeft.x, newPoint.y);
        points.set(1, topRight);
        points.set(2, newPoint);
        points.set(3, bottomLeft);
    }
}