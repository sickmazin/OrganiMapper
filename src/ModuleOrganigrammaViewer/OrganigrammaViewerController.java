package ModuleOrganigrammaViewer;

import ItemSerializable.PaneSerial;
import Model.AbstractOrganigramma;
import ModuleDipendentiRuoli.ControllerSettingDipendentiRuoli;
import ModuleDipendentiRuoli.SettingsDipendentiRuoliApplication;
import ModuloCreationGui.CreationGuiApplication;
import ModuloCreationGui.CreationGuiController;
import PossibleModelOrg.*;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class OrganigrammaViewerController implements Initializable,MediatorViewer {
    public Label titoloOrganLabel;
    public AnchorPane paneCanvas;
    public AnchorPane pane1;
    public VBox menuPane2;

    //LISTA E METODO UTILIZZATI PER SALVARE I MEMENTO COSI DA PERMETTERE L'UNDO
    private LinkedList<Memento> savedNewComponentAdded= new LinkedList<>();
    public void addMemento(Memento m){savedNewComponentAdded.add(m);}

    private AbstractOrganigramma organigramma;
    private GestoreUnita gestoreSottoUnita=new GestoreUnita();
    private ModelDisplayStrategy organigrammaGraphic;

    public void openMenuClicked(MouseEvent mouseEvent) {
        pane1.setVisible(true);
        FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),pane1);
        fadeTransition1.setFromValue(0); fadeTransition1.setToValue(0.3); fadeTransition1.play();

        TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),menuPane2);
        translateTransition1.setByX(+140);
        translateTransition1.play();
    }

    /**
     * L'utente vuole aggiungere una nuova unità, allora si notificano gli osservatori ed essi si aggiorneranno.
     * A seconda del modello si ha un diverso metodo di inserire le unità.
     * Ogni volta che si aggiunge una nuova unita o sotto unita si salva un memento, nel caso si vuole tornare indietro usando undo.
     */
    public void aggiungiUnitaPressed(MouseEvent mouseEvent) {
        if(!(organigrammaGraphic instanceof ModelFunzionaleStrategy )|| organigrammaGraphic.getUnitaSelected()!=null){
            gestoreSottoUnita.notifyObservers();
            addMemento(organigrammaGraphic.storeInMemento());
        }
    }

    /**
     * L'utente dopo aver selezionato l'unita o sotto unita, vuole modificare il suo nome.
     * Fa apparire una textField sulla box così da far inserire il nuovo nome.
     * */
    public void rinominaBoxSelected(MouseEvent mouseEvent) {
        AbstractUnita selected=organigrammaGraphic.getUnitaSelected();
        if(selected!=null){
            TextField renameUnitaTextField = new TextField(); renameUnitaTextField.setPrefHeight(30);
            renameUnitaTextField.setLayoutX(selected.getLayoutX());
            renameUnitaTextField.setLayoutY(selected.getLayoutY());
            renameUnitaTextField.setOnKeyPressed(keyEvent -> {
                String text=renameUnitaTextField.getText();
                if(!text.isBlank()||!text.isEmpty()||!text.matches("\\s*"))selected.setLabelText(text);
                if(keyEvent.getCode().equals(KeyCode.ENTER)||keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                    renameUnitaTextField.setVisible(false);
                    paneCanvas.getChildren().remove(renameUnitaTextField);
                }
            });
            paneCanvas.getChildren().add(renameUnitaTextField);
        }
    }

    /**
     * Apertura della creationGUI in modalità modification, permette all'utente di modificare le info dell'organigramma.
     * */
    public void clickedModificaInfoProject(MouseEvent mouseEvent) {
        try {
            CreationGuiApplication application=new CreationGuiApplication();
            application.start(new Stage());
            CreationGuiController controller=application.getController();
            controller.setPreview(organigramma);
            controller.openedForModification();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Permette all'utente di tornare indietro rispetto all'operazione di aggiunta di una nuova unita.
     * Ottiene il memento e dal component che sia leaf o meno, li rende invisibili.
     * */
    public void undoOperation(MouseEvent mouseEvent) {
        if (savedNewComponentAdded.size()>0) {
            Memento m=savedNewComponentAdded.removeLast();
            ComponentOrg component=organigrammaGraphic.restorComponentFromMemento(m);
            component.setOff();
        }
    }


    /**
     * Apertura della schermata in cui l'utente può assegnare ruoli ed unita ai dipendenti dell'organigramma.
     */
    public void openDipendentiRuoliWindow(MouseEvent mouseEvent) {

        try {
            SettingsDipendentiRuoliApplication application=new SettingsDipendentiRuoliApplication();
            application.start(new Stage());
            ControllerSettingDipendentiRuoli controller=application.getController();
            organigramma.setAllUnita(organigrammaGraphic.getAllBoxInOrganigramma());
            controller.setOrganigramma(organigramma);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo invocato dal button SAVE del menu.
     * */
    public void saveButtonClicked(ActionEvent actionEvent) {
        saveProject(false);
    }

    /**
     * L'utente ha la possibilità di creare un nuovo progetto, mentre il corrente viene mantenuto in funzione
     * */
    public void newProjectButtonClicked(ActionEvent actionEvent) {
        CreationGuiApplication creationGui=new CreationGuiApplication();
        try {
            creationGui.start(new Stage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * L'utente ha la possibilità di aprire un nuovo progetto da file, mentre il corrente viene mantenuto in funzione
     *      */
    public void openProjectButtonClicked(ActionEvent actionEvent) {
        FileChooser fileChooser=new FileChooser();
        AbstractOrganigramma newOrganigramma;
        File file=fileChooser.showOpenDialog(new Stage());
        FileInputStream fileOutputStream = null;
        if(file!=null){
            try {
                fileOutputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileOutputStream);
                newOrganigramma = (AbstractOrganigramma) objectInputStream.readObject();
                fileOutputStream.close();
                objectInputStream.close();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                OrganigrammaViewerApplication application = new OrganigrammaViewerApplication();
                application.start(new Stage());
                OrganigrammaViewerController controller = application.getController();
                controller.setOrganigramma(newOrganigramma,false);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void helpButtonClicked(ActionEvent actionEvent) {
        //TODO INSERISCI INFO SUL PROGETTO
    }

    /**
     * L'utente vuole chiudere il progetto ed ha la possibilità di salvare o no il progetto.
     * */
    public void quitButtonClicked(ActionEvent actionEvent) {
        QuitGui quitGui=new QuitGui();        quitGui.setMediator(this);
    }


    public void setOrganigramma(AbstractOrganigramma organigramma,boolean isNew) {
        this.organigramma = organigramma;
        organigrammaGraphic =organigramma.getModelDisplayStrategy();
        setTitoloOrg();
        gestoreSottoUnita.register((Observer) organigrammaGraphic);
        if(isNew)paneCanvas.getChildren().add(organigrammaGraphic.getModelDisplayStrategy());
        else {
            try {
                paneCanvas.getChildren().addAll(organigrammaGraphic.getPane());
            }catch (NullPointerException e){
                paneCanvas.getChildren().add(organigrammaGraphic.getModelDisplayStrategy());
            }
        }

    }

    /**
     * Modifica il titolo dell'organigramma a secondo del nome dell'azienda
     * */
    private void setTitoloOrg() {
        titoloOrganLabel.setText(organigramma.getNomeAzienda());
    }



    @Override
    public void saveProject(boolean isQuit) {
        organigrammaGraphic.saveItem(((PaneSerial)paneCanvas.getChildren().get(0)).getChildren());
        try{
            PrintWriter pw = new PrintWriter(organigramma.getFilePath());
            pw.close();
            File file=new File(organigramma.getFilePath());
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(organigramma);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(isQuit) ((Stage)paneCanvas.getScene().getWindow()).close();

    }


    @Override
    public void closeProject() {
        ((Stage)pane1.getScene().getWindow()).close();
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //MENU VBOX-SIDEBAR SETTINGS
        pane1.setVisible(false);
        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(0.5),pane1);
        fadeTransition.setFromValue(1); fadeTransition.setToValue(0); fadeTransition.play();
        TranslateTransition translateTransition=new TranslateTransition(Duration.seconds(0.5),menuPane2);
        translateTransition.setByX(-140);
        translateTransition.play();

        pane1.setOnMouseClicked(mouseEvent -> {
            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),pane1);
            fadeTransition1.setFromValue(0.15); fadeTransition1.setToValue(0); fadeTransition1.play();
            fadeTransition1.setOnFinished(actionEvent -> {
                pane1.setVisible(false);
            });
            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),menuPane2);
            translateTransition1.setByX(-140);
            translateTransition1.play();
        });
    }
}
