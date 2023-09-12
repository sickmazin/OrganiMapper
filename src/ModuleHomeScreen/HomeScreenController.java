package ModuleHomeScreen;

import Model.AbstractOrganigramma;
import ModuleOrganigrammaViewer.OrganigrammaViewerApplication;
import ModuleOrganigrammaViewer.OrganigrammaViewerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeScreenController implements Initializable,MediatorHomeScreen {

    //LABEL E IMAGE PER LA PREVIEW DELL'ORGANIGRAMMA SELEZIONATO
    public Label aziendaNameLabel;
    public Label annoAzSceltaLabel;
    public Label modelloOrgScelto;
    public ImageView organSceltoImg;

    //GRID CHE CONTERRà GLI ORGANIGRAMMI PRESENTI SU FILE
    public GridPane gridPane;
    private AbstractOrganigramma organigrammaSelected;
    private ObservableList<AbstractOrganigramma> organigrammi= FXCollections.observableArrayList();

    //INDICI UTILI PER MANTENERE LA GRID CORRETTA
    private int indexLastAdded=0,colonna =1, riga=1;

    /**
     * METODO INVOCATO DAL BUTTON VISUALIZZA ORGANGIRAMMA -> APERTURA DEL FOGLIO DI VISUALIZZAZIONE
     */
    public void clickForVisualProject(MouseEvent mouseEvent) {
        if(organigrammaSelected!=null){
            openOrganigrammaViewer();
            ((Stage)gridPane.getScene().getWindow()).close();
        }
    }
    private void openOrganigrammaViewer() {
        OrganigrammaViewerController controller;
        try {
            OrganigrammaViewerApplication application=new OrganigrammaViewerApplication();
            application.start(new Stage());
            controller=application.getController();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        controller.setOrganigramma(organigrammaSelected,false);
    }

    /**
     * METODO INVOCATO DALLA PRESSIONE DEL TASTO APRI DA, PERMETTE ALL'UTENTE DI SCEGLIERE LA DIRECTORY DA DOVE LEGGERE I FILE DA INSERIRE NELLA GRID
     */
    public void apriDaSelected(MouseEvent mouseEvent) {
        DirectoryChooser directoryChooser=new DirectoryChooser();
        File f=directoryChooser.showDialog(new Stage());
        String directPath=null;
        if(f!=null) {
            directPath=f.getAbsolutePath();
            try {
                getDataFromFile(directPath);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            refreshGrid();
        }
    }

    /**REFRESHA LA GRID QUANDO VIENE SELEZIONATA UNA NUOVA DIRECTORY DA DOVE LEGGERE I FILE*/
    private void refreshGrid() {
        try {
            for (int i = indexLastAdded; i < organigrammi.size(); i++){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/previewOrganigramma.fxml"));
                AnchorPane anchorPane= loader.load();

                PreviewOrgController previewOrgController = loader.getController();
                previewOrgController.setData(organigrammi.get(i));
                previewOrgController.setMediator(this);

                if (colonna==4){
                    colonna=0;
                    riga++;
                }

                gridPane.add(anchorPane,colonna,riga);++colonna;
                GridPane.setMargin(anchorPane,new Insets(10));
            }
            indexLastAdded=organigrammi.size();
        } catch (IOException e) {throw new RuntimeException(e);
        }
    }

    /**LEGGE DAI FILE.SER DELLA DIRECTORY SCELTA E CREA GLI ORGANIGRAMMI E LI INSERISCE NELLA LISTA*/
    private void getDataFromFile(String directPath) throws IOException, ClassNotFoundException {
        if (directPath!=null){
            File sourceFolder = new File(directPath);
            String fileExt = "", fileName;
            for (File file : sourceFolder.listFiles()) {
                fileName= file.getName();
                fileExt=fileName.substring(fileName.indexOf(".")+1);
                //LEGGE DA FILE SOLO .SER CIOè CREATI DALLA SERIALIZZAZIONE DI UN OGGETTO
                //if(fileExt.equalsIgnoreCase("ser")) { //TODO aggiustare che non funziona
                    FileInputStream fileOutputStream = new FileInputStream(file);
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileOutputStream);
                    AbstractOrganigramma organigramma = (AbstractOrganigramma) objectInputStream.readObject();

                    if(!organigrammi.contains(organigramma)) {
                        organigrammi.add(organigramma);
                    }
                    fileOutputStream.close();
                    objectInputStream.close();
                //}
            }
        }
    }

    /**INSERISCE NELLA POSIZIONE [0][1] DELLA GRID IL BUTTON PER APRIRE LA GUI DI CREAZIONE DI UN NUOVO ORGANIGRAMMA*/
    private void inserNewButton() {
        try {
            FXMLLoader newOrgButton = new FXMLLoader();
            newOrgButton.setLocation(getClass().getResource("/fxml/newOrganigrammaButton.fxml"));
            AnchorPane newChartanchorPane= newOrgButton.load();
            gridPane.add(newChartanchorPane,0,1);
            GridPane.setMargin(newChartanchorPane,new Insets(10));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo invocato dagli elementi della grid, permette di inserire le info dell'organigramma selezionato nella preview del progetto.
     * */
    @Override
    public void setPreview(AbstractOrganigramma organigramma) {
        organigrammaSelected=organigramma;
        annoAzSceltaLabel.setText(organigramma.getAnno());
        aziendaNameLabel.setText(organigramma.getNomeAzienda());
        modelloOrgScelto.setText(organigramma.getModello());
        try {
            File image = new File(organigramma.getImgPath());
            organSceltoImg.setImage(new Image(image.toURI().toString()));
        }catch (NullPointerException e){
            organSceltoImg.setImage(new Image(new File("src\\resources\\img\\noImageGrigia.png").toURI().toString()));
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set grid width
        gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
        gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
        gridPane.setMaxWidth(Region.USE_PREF_SIZE);
        //set grid width
        gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
        gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
        gridPane.setMaxHeight(Region.USE_PREF_SIZE);

        inserNewButton();
    }

}
