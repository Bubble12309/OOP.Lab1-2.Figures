package figures;

import java.awt.*;

public class MEllipse extends AreaFigure {

    public MEllipse(Color fill, Point topLeft, Point bottomRight) {
        super(fill);
        var points = this.getPoints();
        points.add(topLeft);
        points.add(bottomRight);
        setPointsRequired(2);
    }

    public void draw(Graphics g) {
        g.setColor(this.getFill());
        var points = this.getPoints();
        Point p1 = points.get(0);
        Point p2 = points.get(1);
        int width = p2.x - p1.x;
        int height = p2.y - p1.y;
        g.fillOval(p1.x, p1.y, width, height);
    }

    @Override
    public void recalculate(Point newPoint) {
        var points = this.getPoints();
        points.set(1, newPoint);
    }
}