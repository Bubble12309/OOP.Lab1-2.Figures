package drawable;

import java.awt.*;
import java.util.ArrayList;

public class DrawableList {
    private final ArrayList<Drawable> drawables;

    public void add(Drawable figure) {
        this.drawables.add(figure);
    }

    public void removeLast() {
        this.drawables.remove(this.drawables.size() - 1);
    }

    public boolean isEmpty() {
        return this.drawables.isEmpty();
    }

    public void drawAll(Graphics g) {
        for (Drawable figure : this.drawables) {
            figure.draw(g);
        }
    }

    public void clear() {
        this.drawables.clear();
    }

    public DrawableList() {
        this.drawables = new ArrayList<>();
    }
}
