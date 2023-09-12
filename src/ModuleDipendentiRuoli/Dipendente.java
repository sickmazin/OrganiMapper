package ModuleDipendentiRuoli;

import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;

import java.io.Serializable;

public class Dipendente implements Serializable, MediatorSelection {
    //INFORMAZIONI DEL DIPENDENTE
    private int id;
    private String nomeCognome,unitaAssociate,ruoli;

    //CHECKBOX UTILIZZATO NELLA TABLE VIEW PER SELEZIONARE IL DIPENDENTE
    private transient CheckBox checkBox;

    public Dipendente(int id, String nomeCognome) {
        this.id = id;
        this.nomeCognome = nomeCognome;
    }

    public int getId() {
        return id;
    }

    public String getNomeCognome() {
        return nomeCognome;
    }

    public String getUnitaAssociate() {
        return unitaAssociate;
    }

    public void setUnitaAssociate(String unitaAssociata) {
        this.unitaAssociate = unitaAssociata;
    }

    public String getRuoli() {
        return ruoli;
    }

    public void setRuoli(String ruoli) {
        this.ruoli = ruoli;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    //MEDIATOR (ControllerSettingDipendentiRuoli)

    private transient MediatorDipendenti mediator;
    public void setMediator(MediatorDipendenti mediator) {
        this.mediator = mediator;
    }


    /*
    *  Metodo richiamato dal check qualora esso venga selezionato significa che l'utente ha selezionato il dipendente per assegnarli un'unita, ruolo o entrambi.
    */
    @Override
    public void getSelected() {
        mediator.dipendenteSelected(this);
    }

    @Override
    public String toString() {
        return nomeCognome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dipendente that)) return false;
        return id == that.id;
    }

    public void createCheckBox() {
        checkBox=new CheckBox();
        checkBox.setOnMousePressed(this::getSelected);
    }

    private void getSelected(MouseEvent mouseEvent) {getSelected();}

}
