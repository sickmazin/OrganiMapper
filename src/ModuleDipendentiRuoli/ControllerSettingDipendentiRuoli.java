package ModuleDipendentiRuoli;

import Model.AbstractOrganigramma;
import PossibleModelOrg.AbstractUnita;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSettingDipendentiRuoli implements Initializable,MediatorDipendenti {

    //LISTE ASSOCIATE ALLE TABLE VIEW
    private ObservableList<Ruolo> ruoliObservableList=FXCollections.observableArrayList();
    private ObservableList<Dipendente> dipendenteObservableList=FXCollections.observableArrayList();
    private ObservableList<AbstractUnita> unitaObservableList=FXCollections.observableArrayList();

    //TABELLA E COLONNE DELLE UNITA
    public TableView unitaTable;
    public TableColumn nomeUnitaColumn;
    public TableColumn selectedUnitaColumn;

    //LABEL PER VISUALIZZARE CIò CHE SI HA SELEZIONATO
    public Label nameDipendenteLabel;
    public Label ruoloSelectedLabel;
    public Label selectedUnitaLabel;

    //TABELLA E COLONNE DEI RUOLI DELL'UNITA SELEZIONATA
    public TableView ruoliUnitaTable;
    public TableColumn ruoliUnitaColumn;
    public TableColumn selectRuoloColumn;

    //TABELLA E COLONNE DEI DIPENDENTI
    public TableView dependentTableView;
    public TableColumn selectedColumn;
    public TableColumn ruoliTableColumn;
    public TableColumn unitaTableColumn;
    public TableColumn idTableColumn;
    public TableColumn nomeDependentColumn;
    private Dipendente dipendenteSelected;
    private AbstractOrganigramma organigramma;
    private AbstractUnita unitaSelected;

    public void associaInfoAlDipendente(MouseEvent mouseEvent) {
        if(dipendenteSelected!=null && unitaSelected!=null){
            if (dipendenteSelected.getUnitaAssociate() != null)
                dipendenteSelected.setUnitaAssociate(dipendenteSelected.getUnitaAssociate()+"\n"+selectedUnitaLabel.getText());
            else dipendenteSelected.setUnitaAssociate(selectedUnitaLabel.getText());
            if(dipendenteSelected.getRuoli() != null)
                dipendenteSelected.setRuoli(dipendenteSelected.getRuoli()+"\n"+ ruoloSelectedLabel.getText());
            else  dipendenteSelected.setRuoli(ruoloSelectedLabel.getText());
            dependentTableView.refresh();
            nameDipendenteLabel.setText("");
            selectedUnitaLabel.setText("");
            ruoloSelectedLabel.setText("");
            for (AbstractUnita u: unitaObservableList) {
                u.getCheckBox().setSelected(false);
            }
            ruoliUnitaTable.setItems(FXCollections.observableArrayList());
            ruoliUnitaTable.refresh();
            for (Dipendente d: dipendenteObservableList) {
                d.getCheckBox().setSelected(false);
            }
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //RUOLI TABLE
        ruoliUnitaColumn.setCellValueFactory(new PropertyValueFactory<>("ruolo"));
        selectRuoloColumn.setCellValueFactory(new PropertyValueFactory<>("checkBox"));


        //UNITA TABLE
        nomeUnitaColumn.setCellValueFactory(new PropertyValueFactory<>("nomeUnita"));
        selectedUnitaColumn.setCellValueFactory(new PropertyValueFactory<>("checkBox"));

        //DIPENDENTI TABLE
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeDependentColumn.setCellValueFactory(new PropertyValueFactory<>("nomeCognome"));
        unitaTableColumn.setCellValueFactory(new PropertyValueFactory<>("unitaAssociate"));
        ruoliTableColumn.setCellValueFactory(new PropertyValueFactory<>("ruoli"));
        selectedColumn.setCellValueFactory(new PropertyValueFactory<>("checkBox"));

        //ASSOCIAMO ALLE TABLE VIEW LE LISTE CORRISPONDENTI DA DOVER VISUALIZZARE
        dependentTableView.setItems(dipendenteObservableList);
        unitaTable.setItems(unitaObservableList);
        ruoliUnitaTable.setItems(ruoliObservableList);
    }

    /**
     * METODO CHE VIENE INVOCATO DAL CHECKBOX DEL DIPENDENTE QUANDO VIENE CLICCATO
     */
    @Override
    public void dipendenteSelected(Dipendente dipendente) {
        if(dipendenteSelected!=null && dipendenteSelected.equals(dipendente)) {
            dipendenteSelected = null;
            nameDipendenteLabel.setText("");

        }else {
            dipendenteSelected = dipendente;
            nameDipendenteLabel.setText(dipendente.getNomeCognome());
        }
        for (Dipendente d: organigramma.getDipendenti()){
            if(!d.equals(dipendente)) d.getCheckBox().setSelected(false);
        }
    }

    @Override
    public void unitaSelected(AbstractUnita unita) {
        if(dipendenteSelected!=null){
            if(unitaSelected!=null && unitaSelected.equals(unita)) {
                unitaSelected = null;
                selectedUnitaLabel.setText("");
            }else {
                unitaSelected = unita;
                selectedUnitaLabel.setText(unita.getNomeUnita());
                ruoliObservableList = FXCollections.observableArrayList();
                if(unita.getRuoliUnita()!=null){
                    for (Ruolo r : unita.getRuoliUnita()) {
                        ruoliObservableList.add(r);
                        r.createCheckBox();
                        r.setMediator(this);
                    }
                }
                ruoliUnitaTable.setItems(ruoliObservableList);
                ruoliUnitaTable.refresh();
            }
            for (AbstractUnita b : organigramma.getAllUnita()) {
                if (!b.equals(unita)) b.getCheckBox().setSelected(false);
            }
        }
    }

    @Override
    public void ruoloSelected(Ruolo ruolo) {
        if(!ruoliInConflitto(dipendenteSelected.getRuoli(),ruolo)) {
            ruoloSelectedLabel.setText(ruolo.getRuolo());
            for (Ruolo r : unitaSelected.getRuoliUnita()) {
                if (!r.equals(ruolo)) r.getCheckBox().setSelected(false);
            }
        }
    }

    private boolean ruoliInConflitto(String ruoli, Ruolo ruolo) { return false; //TODO
    }

    public void setOrganigramma(AbstractOrganigramma organigramma) {
        this.organigramma=organigramma;
        for (Dipendente dipendente: organigramma.getDipendenti()){
            dipendente.setMediator(this);
            dipendente.createCheckBox();
            dipendenteObservableList.add(dipendente);
        }
        for (AbstractUnita un:organigramma.getAllUnita()) {
            unitaObservableList.add( un);
            un.createCheckBox();
            un.setMediatorDipendenti(this);
        }
    }
}
