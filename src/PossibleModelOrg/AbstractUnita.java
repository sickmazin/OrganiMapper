package PossibleModelOrg;

import ModuleDipendentiRuoli.MediatorDipendenti;
import ModuleDipendentiRuoli.MediatorSelection;
import ModuleDipendentiRuoli.Ruolo;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AbstractUnita extends HBox implements ComponentOrg, MediatorSelection, Serializable {

    //INFO PER LA DIPENDENTI GUI
    protected String nomeUnita;
    protected List<Ruolo> ruoliUnita;
    protected transient CheckBox checkBox;
    protected transient Label label;

    //MEDIATOR
    protected MediatorUnita mediator;
    protected transient MediatorDipendenti mediatorDipendenti;

    public List<Ruolo> getRuoliUnita() {
        return ruoliUnita;
    }

    public void setRuoliUnita(List<Ruolo> ruoli) {
        if(ruoli!=null){
            if (ruoliUnita == null) {
                ruoliUnita = new ArrayList<>();
                this.ruoliUnita.addAll(ruoli);
            } else {
                for (Ruolo r : ruoli) {
                    if (!ruoliUnita.contains(r)) ruoliUnita.add(r);
                }
            }
        }
    }

    // INFO E METODI PER CAPIRE SE L'UNITA VIENE SELEZIONATA O MENO DALL'UTENTE QUANDO è VISUALIZZATO L'ORGANIGRAMMA
    protected boolean selected=false;
    public void setUnSelectedDisplay(){
        selected=false;
        getStyleClass().remove("selected-Box");
    }
    public boolean isSelectedOnDisplay(){return selected;}
    protected void selectedBox(MouseEvent mouseEvent) {
        selected=!selected;
        if(selected) {
            mediator.setSelected(this);
            getStyleClass().add("selected-Box");
        }
        else { //RICLICCO SULLA STESSA BOX PER DESELEZIONARLA
            mediator.setSelected(null);
            getStyleClass().remove("selected-Box");
        }

    }

    //SETTERS MEDIATOR
    public void setMediatorDipendenti(MediatorDipendenti dipendentiController) {
        mediatorDipendenti=dipendentiController;
    }

    public void setMediator(MediatorUnita mediator) {
        this.mediator = mediator;
    }
    public CheckBox getCheckBox() {
        return checkBox;
    }
    public void setLabelText(String text){
        nomeUnita=text;
        label.setText(text);
    }
    public String getNomeUnita() {
        return nomeUnita;
    }
    public void setOff() {
        setVisible(false);
    }

    @Override
    public void getSelected() {
        mediatorDipendenti.unitaSelected(this);
    }
    protected void getSelected(MouseEvent mouseEvent) { getSelected();}

    public void openRuoliDellUnita(MouseEvent mouseEvent) {
        mediator.openRuoliDellUnita(this);
    }

    public void createCheckBox() {
        checkBox=new CheckBox();
        checkBox.setOnMousePressed(this::getSelected);
    }
}
