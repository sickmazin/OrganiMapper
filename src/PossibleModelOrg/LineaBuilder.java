package PossibleModelOrg;

import ItemSerializable.LineSerial;
import javafx.scene.shape.Line;

public class LineaBuilder {
    private static final double LARGHEZZA_CELL=200, ALTEZZA_CELL=60 ;

    public static LineSerial getLineaOrizzontale(double startX, double endX, double altezzaY){
        return new LineSerial(startX,altezzaY,endX,altezzaY);
    }
    public static LineSerial getLineaVerticale(double posX, double startY, double endY){
        return new LineSerial(posX,startY,posX,endY);
    }

    public static LineSerial getLineaTraBox(double startX, double startY, double endX, double endY) {
        LineSerial line= new LineSerial();
        line.setStartX(startX+(LARGHEZZA_CELL/2));            line.setStartY(startY+ALTEZZA_CELL);
        line.setEndX(endX+(LARGHEZZA_CELL/2));            line.setEndY(endY);
        return line;
    }
}
