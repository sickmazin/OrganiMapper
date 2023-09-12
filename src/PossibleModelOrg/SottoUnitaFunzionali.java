package PossibleModelOrg;

import ItemSerializable.LineSerial;
import javafx.scene.Node;
import javafx.scene.shape.Line;

import java.util.LinkedList;
import java.util.List;

public class SottoUnitaFunzionali implements ComponentOrg{
    private Unita unitaAssociata;

    public void setUnitaAssociata(Unita unitaAssociata) {
        this.unitaAssociata = unitaAssociata;
    }

    private final SottoUnita sottounita;
    private  List<LineSerial> lines;

    public SottoUnitaFunzionali(SottoUnita sottoUnita, List<LineSerial> lineList) {
        this.sottounita=sottoUnita;
        lines=lineList;
    }

    public SottoUnita getSottoUnita() {
        return sottounita;
    }

    @Override
    public void setOff() {
        sottounita.setOff();
        for (LineSerial l:
             lines) {
            l.setVisible(false);
        }
    }

    public Unita getUnitaAssociata() {
        return unitaAssociata;
    }
}
