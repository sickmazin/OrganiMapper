package ModuloCreationGui;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;

public class ModelPreviewController {
    public Label modelloLabel;
    public ImageView imgModel;
    private MediatorModel mediator;
    public void setModello(String modello){
        modelloLabel.setText("Modello di organigramma "+modello);
        imgModel.setImage(new Image(new File("src\\resources\\img\\modello_"+modello+".png").toURI().toString()));
    }

    public void clickForChoseModel(MouseEvent mouseEvent) {
        mediator.setChosedModel(modelloLabel.getText());
    }

    public void setMediator(MediatorModel mediator) {
        this.mediator=mediator;
    }
}
