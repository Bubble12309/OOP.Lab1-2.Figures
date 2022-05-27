package figures;

import drawable.Drawable;

import java.awt.*;
import java.util.ArrayList;

public abstract class Figure implements Drawable {

    protected static final int POINTS_MANY = 0;

    private int pointsRequired;

    protected int getPointsRequired() {
        return this.pointsRequired;
    }

    protected void setPointsRequired(int n) {
        this.pointsRequired = n;
    }

    private final ArrayList<Point> points;

    public ArrayList<Point> getPoints() {
        return this.points;
    }

    public void addPoint(Point point) {
        if ((pointsRequired == POINTS_MANY) || (this.points.size() < pointsRequired)) {
            points.add(point);
        }
    }

    protected Figure() {
        this.points = new ArrayList<Point>();
    }

    @Override
    public abstract void draw(Graphics g);

    @Override
    public abstract void recalculate(Point newPoint);

    @Override
    public boolean ready() {
        return this.getPointsRequired() == Figure.POINTS_MANY || this.getPoints().size() == this.getPointsRequired();
    }
}