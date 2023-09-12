package ModuleOrganigrammaViewer;

import ModuloCreationGui.CreationGuiController;
import ModuloCreationGui.MediatorModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class ChangingModel {
    private final AnchorPane anchorPane;
    private MediatorModel mediator;
    public ChangingModel() {
        // Create an AnchorPane
        anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(135.0);
        anchorPane.setPrefWidth(600.0);
        anchorPane.setStyle("-fx-background-color: #929090;");

        // Create a Text element
        Text text = new Text("Stai cambiando il modello del progetto, tutto quello che hai fatto in precedenza verra perso, vuoi continuare ?");
        text.setLayoutY(20.0);
        text.setStrokeWidth(0.0);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setWrappingWidth(600.0);
        Font font = new Font("Cambria", 21.0);
        text.setFont(font);

        // Create an HBox for buttons
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setLayoutX(180.0);
        hbox.setLayoutY(49.0);
        hbox.setPrefHeight(66.0);
        hbox.setPrefWidth(600.0);
        AnchorPane.setBottomAnchor(hbox, 0.0);
        AnchorPane.setLeftAnchor(hbox, 0.0);
        AnchorPane.setRightAnchor(hbox, 0.0);

        // Create "Si" button
        Button yesButton = new Button("Si");  yesButton.setPrefWidth(54); yesButton.setPrefHeight(30);
        yesButton.setMnemonicParsing(false);
        yesButton.getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());
        yesButton.getStyleClass().add("menu-button");
        HBox.setMargin(yesButton, new Insets(0, 50.0, 0, 50.0));
        yesButton.setOnMousePressed(this::yesPressed);

        // Create "No" button
        Button noButton = new Button("No"); noButton.setPrefWidth(54); noButton.setPrefHeight(30);
        noButton.setMnemonicParsing(false);
        noButton.getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());
        noButton.getStyleClass().add("menu-button");
        HBox.setMargin(noButton, new Insets(0, 50.0, 0, 50.0));
        noButton.setOnMousePressed(this::noPressed);


        // Add components to the AnchorPane
        anchorPane.getChildren().addAll(text, hbox);
        hbox.getChildren().addAll(yesButton, noButton);

        // Create a Scene and set it on the Stage
        Scene scene = new Scene(anchorPane); Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("Stai per chiudere il progetto");
        stage.show();
    }

    private void yesPressed(MouseEvent mouseEvent) {
        ((CreationGuiController)mediator).clickForVisualProject(mouseEvent);
        ((Stage)anchorPane.getScene().getWindow()).close();
    }

    public void setMediator(MediatorModel mediator) {
        this.mediator = mediator;
    }

    private void noPressed(MouseEvent mouseEvent) {
        ((Stage)anchorPane.getScene().getWindow()).close();
    }
}
