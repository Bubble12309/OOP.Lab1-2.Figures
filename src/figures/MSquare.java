package figures;

import java.awt.*;

public class MSquare extends MRectangle {

    public MSquare(Color fill, Point topleft, int width) {
        super(fill, topleft, new Point(topleft.x + width, topleft.y + width));
    }

    @Override
    public void recalculate(Point newPoint) {
        var points = this.getPoints();
        Point topLeft = points.get(0);
        int dx = newPoint.x - topLeft.x;
        int dy = newPoint.y - topLeft.y;
        int width = Math.max(dx, dy);
        Point topRight = new Point(topLeft.x + width, topLeft.y);
        Point bottomLeft = new Point(topLeft.x, topLeft.y + width);
        Point bottomRight = new Point(topRight.x, bottomLeft.y);
        points.set(1, topRight);
        points.set(2, bottomRight);
        points.set(3, bottomLeft);
    }
}