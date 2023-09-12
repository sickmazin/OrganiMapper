package PossibleModelOrg;

import ItemSerializable.LineSerial;
import javafx.scene.Node;
import javafx.scene.shape.Line;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class DirezioneProgetto implements ComponentOrg{
    private SottoUnita sottoUnita;
    List<LineSerial> lines;
    public DirezioneProgetto(SottoUnita sottoUnita, List<LineSerial> lineList) {
        this.sottoUnita=sottoUnita;
        lines=lineList;
    }

    @Override
    public void setOff() {
        for (LineSerial l:lines) l.setVisible(false);
        sottoUnita.setOff();
    }

    public SottoUnita getSottoUnita() {
        return sottoUnita;
    }

}
