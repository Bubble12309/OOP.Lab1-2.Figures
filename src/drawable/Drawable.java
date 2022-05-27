package drawable;

import java.awt.*;

public interface Drawable {
    void draw(Graphics g);

    void recalculate(Point lastPoint);

    default void addPoint(Point point) {
    }

    boolean ready();
}