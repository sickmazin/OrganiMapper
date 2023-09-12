package PossibleModelOrg;

import ItemSerializable.LineSerial;
import ItemSerializable.PaneSerial;
import ModuleDipendentiRuoli.Ruolo;
import ModuleOrganigrammaViewer.BoxRuoli;
import javafx.collections.ObservableList;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class ModelDisplayStrategy implements MediatorUnita,Observer, Serializable {
    protected double LARGHEZZA_CELL=200, ALTEZZA_CELL=60 ;
    protected PaneSerial pane;
    protected AbstractUnita unitaSelected;
    protected ComponentOrg lastComponentAdded;
    protected List<AbstractUnita> allBoxInOrganigramma= new ArrayList<>();


    private List<AbstractUnita> saveUnitaList;
    private List<LineSerial>  saveLineList;

    /**
     * Ogni modello ha una sua strategia di eseguire e implementare unita e sottounita.
     * */
    public abstract AnchorPane getModelDisplayStrategy();
    public abstract void aggiungiSottoUnita(AbstractUnita unita, String text) ;

    /**
     * Metodo che permette di andare a definire quale unita o sotto unita è stata selezionata dall'utente
     * */
    public void setSelected(AbstractUnita boxUnita) {
        unitaSelected=boxUnita;
        for (AbstractUnita b:allBoxInOrganigramma) {
            if(b.isSelectedOnDisplay() && !b.equals(boxUnita)) b.setUnSelectedDisplay();
        }
    }


    /**
     * Permette di aprire una semplice finestra in cui tramite una txt area l'utente può inserire i ruoli all'intero della specifica unita
     * @param unita permette al mediator di associarla direttamente ad essa.
     */
    @Override
    public void openRuoliDellUnita(AbstractUnita unita) {
        unitaSelected=unita;
        BoxRuoli boxRuoli=new BoxRuoli(unita.getLayoutX(),unita.getLayoutY());  boxRuoli.setMediator(this);
        pane.getChildren().add(boxRuoli);
    }

    /**
     * L'utente ha cliccato inserisci nella mini gui associata all'inserimento dei ruoli.
     * @param ruoli questa lista viene generata e gestita dal BoxRuoli aperto per l'unita selezionata.
     */
    @Override
    public void setRuoliDellUnita(List<Ruolo> ruoli) {
        if(unitaSelected!=null)unitaSelected.setRuoliUnita(ruoli);
    }

    //GETTERS
    public AbstractUnita getUnitaSelected() {
        return unitaSelected;
    }
    public List<AbstractUnita> getAllBoxInOrganigramma() {
        return allBoxInOrganigramma;
    }

    /**
     * Permette di ottenere un oggetto memento dall'ultimo componente che ho inserito.
     * */
    public Memento storeInMemento(){
        return new Memento(lastComponentAdded);
    }

    /**
     * Ottengo dal Memento l'ultimo Component aggiunto nell'organigramma, che sia un'unità, sotto unita o divisione.
     * */
    public ComponentOrg restorComponentFromMemento(Memento memento){
        lastComponentAdded=memento.getLastComponentAdded();
        removedComponent(lastComponentAdded);
        return lastComponentAdded;
    }

    /**
     * Metodo che permette di eliminare dall'organigramma cio che è stato rimosso con l'undo
     * */
    protected abstract void removedComponent(ComponentOrg lastComponentAdded) ;

    /**
     * Permette di capire qual è l'ultimo componente che è stato inserito.
     * */
    public void setLastComponentAdded(ComponentOrg componentAdded){
        lastComponentAdded=componentAdded;
    }

    protected void setPane() {
        pane=new PaneSerial();
        pane.setId("pane");
        pane.setMaxHeight(Double.NEGATIVE_INFINITY);
        pane.setMaxWidth(Double.NEGATIVE_INFINITY);
        pane.setMinHeight(Double.NEGATIVE_INFINITY);
        pane.setMinWidth(Double.NEGATIVE_INFINITY);
        pane.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        pane.setPrefHeight(2000.0);pane.setPrefWidth(5000.0);
    }


    public PaneSerial getPane() {
        setPane();
        for (AbstractUnita unita:saveUnitaList) {
            pane.getChildren().add(unita);
        }
        for (LineSerial l:saveLineList) {
            pane.getChildren().add(l);
        }
        return pane;
    }

    public void saveItem(ObservableList<Node> children) {
        saveUnitaList=new ArrayList<>();
        saveLineList=new ArrayList<>();
        for (Node n:children) {
            if (n instanceof AbstractUnita) {
                saveUnitaList.add((AbstractUnita) n);
            }
            else if (n instanceof Line) saveLineList.add((LineSerial)n);
        }
    }
}
