package PossibleModelOrg;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;

import java.io.File;
import java.io.Serial;

public class Unita extends AbstractUnita {
    private double x,y;

    public Unita(double x, double y) {
        super();
        this.x=x; this.y=y;
        setAlignment(Pos.TOP_RIGHT);
        setLayoutX(x);
        setLayoutY(y);
        setPrefHeight(60.0);
        setPrefWidth(200.0);
        getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());
        getStyleClass().add("box");

        setOnMousePressed(this::selectedBox);

        label = new Label("Unita");
        label.setAlignment(Pos.CENTER);
        label.setPrefHeight(70.0);
        label.setPrefWidth(180.0);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setWrapText(true);

        ImageView imageView = new ImageView();
        imageView.setFitHeight(21.0);
        imageView.setFitWidth(23.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setOnMousePressed(this::openRuoliDellUnita);

        Image image = new Image(new File("src\\resources\\img\\quote.png").toURI().toString());
        imageView.setImage(image);
        setMargin(imageView, new Insets(4, 4, 4, 6));
        getChildren().addAll(label, imageView);
    }
    @Serial
    private Object readResolve(){
        Unita u=new Unita(x,y);
        u.setLabelText(nomeUnita);
        u.setRuoliUnita(ruoliUnita);
        u.setMediator(mediator);
        return u;
    }
}
