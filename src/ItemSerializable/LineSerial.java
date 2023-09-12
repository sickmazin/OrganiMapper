package ItemSerializable;

import javafx.scene.shape.Line;

import java.io.Serial;
import java.io.Serializable;

public class LineSerial extends Line implements Serializable {
    private double v;
    private double v1;
    private double v2;
    private double v3;

    public LineSerial() {
        super();
    }

    public LineSerial(double v, double v1, double v2, double v3) {
        super(v, v1, v2, v3);
        this.v=v;
        this.v1=v1;
        this.v2 =v2;
        this.v3=v3;

    }
    @Serial
    private Object readResolve(){
        return new LineSerial(v,v1,v2,v3);
    }
}
