package ModuleHomeScreen;

import Model.AbstractOrganigramma;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;

public class PreviewOrgController {
    public Label aziendaNameLabel;
    public Label annoAzLabel;
    public ImageView imgOrg;
    public Label modelloOrg;


    private AbstractOrganigramma organigramma;
    private MediatorHomeScreen mediator;

    /**
     * Inizializza le informazioni dell'organigramma nelle varie label e inserendo il logo.
     * Se nella creazione l'immagine non viene inserita ne imposta una di default.
     * */
    public void setData(AbstractOrganigramma organigramma) {
        this.organigramma=organigramma;
        aziendaNameLabel.setText(organigramma.getNomeAzienda());
        annoAzLabel.setText(organigramma.getAnno());
        try {
            File image = new File(organigramma.getImgPath());
            imgOrg.setImage(new Image(image.toURI().toString()));
        }catch (NullPointerException e){
            imgOrg.setImage(new Image(new File("src\\resources\\img\\noImageGrigia.png").toURI().toString()));
        }
        modelloOrg.setText(organigramma.getModello());
    }

    public void setMediator(MediatorHomeScreen homeScreenController) {
        mediator=homeScreenController;
    }

    public void clickForPreview(MouseEvent mouseEvent) {
        mediator.setPreview(organigramma);
    }
}
