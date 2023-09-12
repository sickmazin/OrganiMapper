package PossibleModelOrg;

import ItemSerializable.LineSerial;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class DivisioneProdotto implements ComponentOrg{

    private Unita unita;
    private List<SottoUnita> sottoUnitaList=new ArrayList<>();
    private List<LineSerial> lines=new ArrayList<>();

    public DivisioneProdotto(Unita unita, List<SottoUnita> sottoUnitaList, List<LineSerial> lines) {
        this.unita = unita;
        this.sottoUnitaList = sottoUnitaList;
        this.lines = lines;
    }

    public AbstractUnita getUnita() {
        return unita;
    }

    public List<SottoUnita> getSottoUnitaList() {
        return sottoUnitaList;
    }
    public void addSottoUnita(SottoUnita sottoUnita){
        sottoUnitaList.add(sottoUnita);
    }

    @Override
    public void setOff() {
        unita.setOff();
        for (SottoUnita sottoUnita: sottoUnitaList) {
            sottoUnita.setOff();
        }
        for (LineSerial l:
             lines) {
            l.setVisible(false);
        }
    }

    public void setLineList(List<LineSerial> lineList) {
        lines=lineList;
    }
}
