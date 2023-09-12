package ModuloCreationGui;

import Model.AbstractOrganigramma;
import Model.OrganigrammaSimple;
import ModuleDipendentiRuoli.Dipendente;
import ModuleOrganigrammaViewer.ChangingModel;
import ModuleOrganigrammaViewer.OrganigrammaViewerApplication;
import ModuleOrganigrammaViewer.OrganigrammaViewerController;
import PossibleModelOrg.ModelDisplayStrategy;
import PossibleModelOrg.ModelDivisionaleStrategy;
import PossibleModelOrg.ModelFunzionaleStrategy;
import PossibleModelOrg.ModelMatriceStrategy;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class CreationGuiController implements MediatorModel {


    //TEXT FIELD E AREA DOVE INSERIRE LE INFO
    public TextField nomeTxtField;
    public TextField annoTxtField;
    public TextArea dipendentiTxtArea;

    //LABEL DOVE APPARIRANNO LE INFO INSERITE E L'IMMAGINE SCELTA
    public Label aziendaNameLabel;
    public Label annoLabel;
    public ImageView imgScelta;
    public Label modelloLabel;
    public Button createButton;
    private AbstractOrganigramma organigramma;
    private String pathImage="src\\resources\\img\\noImageGrigia.png";

    /**
     * Metodo mediator utilizzato per cambiare il testo del modello selezionato dall'utente per il progetto.
     * */
    @Override
    public void setChosedModel(String text) {
        modelloLabel.setText(text);
    }

    /**
     * Modifica automatica del nome inserito nel text field all'interno del box preview.
     * */
    public void refreshNamePreview(KeyEvent keyEvent) { aziendaNameLabel.setText(nomeTxtField.getText());
    }

    /**
     * Modifica automatica dell'anno inserito nel text field all'interno del box preview.
     * All'interno del metodo si ha anche una gestione del testo inserito dall'utente infatti si accettano dati in input del tipo:
     * <li>aaaa</li>
     * <li>aaaa/aa</li>
     * <li>aaaa-aa</li>
     *
     * */
    public void refreshAnnoPreview(KeyEvent keyEvent) {
        String text=annoTxtField.getText();
        if(text.matches("\\d{4}(?:/\\d{2}|-\\d{2})?") || text.isBlank() )
            annoLabel.setText(text);
    }


    /**
     *  Permette l'apertura di un fileChooser dove l'utente sceglierà il logo da assegnare all'organigramma. Nel caso in cui questo non trova l'immagine o decide di non inserirla ne associamo una di default.
     */
    public void openFileChoser(MouseEvent mouseEvent) {
        FileChooser fileChooser= new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.jpg", "*.jpeg","*.png"));
        File file=fileChooser.showOpenDialog(new Stage());
        if(file!=null) {
            pathImage = file.getAbsolutePath();
        }
        imgScelta.setImage(new Image(new File(pathImage).toURI().toString()));
    }

    /**
     *  Apertura del metodo di selezione dei modelli disponibili per l'app. Una volta selezionato si chiuderà la finestra di scelta. 
     */
    public void openModelChoser(MouseEvent mouseEvent) {
        Parent root= null;
        Stage stage=new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            AnchorPane p = fxmlLoader.load(getClass().getResource("../fxml/choseModelOrg.fxml").openStream());
            ChoseModelOrgController controller=(ChoseModelOrgController)fxmlLoader.getController();
            controller.setMediator(this);
            stage.setTitle("Clicca sul modello di organigramma che vuoi utilizzare");
            stage.setScene(new Scene(p));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     *  Crea il file associato all'organigramma nella directory scelta dall'utente, il nome del file deriva dal nome dell'organigramma.
     */
    private void createFileForOrganigramma() throws IOException {
        DirectoryChooser directoryChooser= new DirectoryChooser();
        File f=directoryChooser.showDialog(new Stage());
        if(f!=null){
            String pathDirectory = f.getPath();
            File file = new File(pathDirectory + "\\" + organigramma.getNomeAzienda());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            organigramma.setFilePath(file.getAbsolutePath());
            objectOutputStream.writeObject(organigramma);
        }else         createFileForOrganigramma();
    }
    
    public void clickForVisualProject(MouseEvent mouseEvent) {
        if(!aziendaNameLabel.getText().isBlank() && !annoLabel.getText().isBlank() && !modelloLabel.getText().equals("Scegli modello...")) {
            List<Dipendente>dipendenteList=DipendentiTxtArea.ottieniDipendenti(dipendentiTxtArea.getText(),1);

            String nome=aziendaNameLabel.getText(),
                    anno= annoLabel.getText(),
                    modello= modelloLabel.getText();

            organigramma=new OrganigrammaSimple(nome,anno,modello,pathImage);
            organigramma.setDipendenti(dipendenteList);
            setOrganigrammaDisplayStrategyFromData();

            try {
                createFileForOrganigramma();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            openOrganigrammaViewer();
            Stage thisStage=(Stage) aziendaNameLabel.getScene().getWindow();
            thisStage.close();
        }
    }
    private ModelDisplayStrategy setOrganigrammaDisplayStrategyFromData() {
        String modello=organigramma.getModello();
        switch (modello) {
            case "Modello di organigramma funzionale" -> {
                organigramma.setModelDisplayStrategy(new ModelFunzionaleStrategy());
            }
            case "Modello di organigramma divisionale" -> {
                organigramma.setModelDisplayStrategy(new ModelDivisionaleStrategy());
            }
            case "Modello di organigramma matrice" -> {
                organigramma.setModelDisplayStrategy(new ModelMatriceStrategy());
            }
        }
        return null;
    }


    private void openOrganigrammaViewer() {
        try {
            OrganigrammaViewerApplication application=new OrganigrammaViewerApplication();
            application.start(new Stage());
            OrganigrammaViewerController controller=application.getController();
            controller.setOrganigramma(organigramma,true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Si effettua una modifica al button create perchè il metodo viene invocato nel caso in cui l'utente vuole
     * andare a modificare le info in corso d'opera.
     *
     *
     */
    public void openedForModification(){
        createButton.setText("Salva modifiche");
        createButton.setOnMousePressed(mouseEvent -> {
            String nome=aziendaNameLabel.getText(),
                    anno= annoLabel.getText(),
                    modello= modelloLabel.getText();
            List<Dipendente> dipendenteList=DipendentiTxtArea.ottieniDipendenti(dipendentiTxtArea.getText(),organigramma.getDipendenti().size());
            for (Dipendente d: dipendenteList) {
                if(!organigramma.getDipendenti().contains(d)) organigramma.getDipendenti().add(d);
            }
            organigramma.setImgPath(pathImage);
            organigramma.setAnno(anno);
            organigramma.setNomeAzienda(nome);
            if(!modello.equals(organigramma.getModello())) {
                ChangingModel changingModel=new ChangingModel();
                changingModel.setMediator(this);
            }
            ((Stage)createButton.getScene().getWindow()).close();
        });

    }


    /**
     * Metodo che permette di settare da subito le info di un organigramma, viene utilizzato quando l'utente vuole modificare le info in corso d'opera 
     *
     * */
    public void setPreview(AbstractOrganigramma organigramma) {
        this.organigramma=organigramma;
        aziendaNameLabel.setText(organigramma.getNomeAzienda());
        annoLabel.setText(organigramma.getAnno());
        modelloLabel.setText(organigramma.getModello());
        File f=new File(organigramma.getImgPath());
        if(f!=null) {
            pathImage=f.getAbsolutePath();
            imgScelta.setImage(new Image(f.toURI().toString()));
        }
        dipendentiTxtArea.setText(String.valueOf(organigramma.getDipendenti()));
    }
}
