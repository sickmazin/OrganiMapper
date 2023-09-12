package ModuloCreationGui;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChoseModelOrgController implements Initializable, MediatorModel {
    public GridPane gridPane;
    private String[] MODELLI={"funzionale","divisionale","matrice"};
    private MediatorModel mediator;

    public void setMediator(MediatorModel mediator) {
        this.mediator = mediator;
    }

    @Override
    public void setChosedModel(String text) {
        mediator.setChosedModel(text);
        ((Stage)gridPane.getScene().getWindow()).close();
    }
    private void refreshGrid(){
        int colonna =0, riga=1;
        try {
            for (int i = 0; i < MODELLI.length; i++){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/modelPreview.fxml"));
                AnchorPane anchorPane= loader.load();

                ModelPreviewController modelPreviewController = loader.getController();
                modelPreviewController.setModello(MODELLI[i]);
                modelPreviewController.setMediator(this);


                gridPane.add(anchorPane,colonna,riga);++colonna;
                GridPane.setMargin(anchorPane,new Insets(10));
            }} catch (IOException e) {
            throw new RuntimeException(e);
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

        refreshGrid();
    }
}
