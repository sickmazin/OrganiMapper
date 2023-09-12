package ModuleDipendentiRuoli;

import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;

import java.io.Serializable;

public class Ruolo implements Serializable {
    private transient CheckBox checkBox;
    private String ruolo;
    private transient MediatorDipendenti mediator;
    public Ruolo(String ruolo) {
        this.ruolo = ruolo;
        checkBox=new CheckBox();
        checkBox.setOnMousePressed(this::getSelected);
    }

    private void getSelected(MouseEvent mouseEvent) {
        mediator.ruoloSelected(this);
    }

    public void setMediator(MediatorDipendenti mediator) {
        this.mediator = mediator;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public String getRuolo() {
        return ruolo;
    }
    public void createCheckBox() {
        checkBox=new CheckBox();
        checkBox.setOnMousePressed(this::getSelected);
    }

}
