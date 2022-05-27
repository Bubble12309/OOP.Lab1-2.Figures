package figures;
import java.awt.Color;

public abstract class AreaFigure extends Figure {
    private Color fill;

    public Color getFill() {
        return this.fill;
    }

    public void setFill(Color fill) {
        this.fill = fill;
    }

    protected AreaFigure(Color fill) {
        this.setFill(fill);
    }
}
