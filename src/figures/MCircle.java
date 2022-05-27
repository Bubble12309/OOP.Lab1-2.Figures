package figures;

import drawable.Drawable;

import java.awt.*;

public class MCircle extends MEllipse implements Drawable {
    public MCircle(Color fill, Point topLeft, int width) {
        super(fill, topLeft, new Point(topLeft.x + width, topLeft.y + width));
    }

    @Override
    public void recalculate(Point newPoint) {
        var points = this.getPoints();
        Point topLeft = points.get(0);
        int dx = newPoint.x - topLeft.x;
        int dy = newPoint.y - topLeft.y;
        int width = Math.max(dx, dy);
        Point bottomRight = new Point(topLeft.x + width, topLeft.y + width);
        points.set(1, bottomRight);
    }
}