package figures;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MPolyline extends LinearFigure {
    public MPolyline(Color lineColor, int penWidth, Point p1, Point p2, Point... varPoints) {
        super(lineColor, penWidth);
        ArrayList<Point> points = this.getPoints();
        points.add(p1);
        points.add(p2);
        points.addAll(Arrays.asList(varPoints));
        setPointsRequired(Figure.POINTS_MANY);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(this.getLineColor());
        g2d.setStroke(new BasicStroke((float) this.getPenWidth()));
        ArrayList<Point> points = this.getPoints();
        int size = points.size();
        int[] x = new int[size];
        int[] y = new int[size];
        for (int i = 0; i < size; i++) {
            x[i] = points.get(i).x;
            y[i] = points.get(i).y;
        }
        g2d.drawPolyline(x, y, size);
    }

    @Override
    public void recalculate(Point newPoint) {
        var points = this.getPoints();
        points.set(points.size() - 1, newPoint);
    }
}