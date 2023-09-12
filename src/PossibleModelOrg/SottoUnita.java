package PossibleModelOrg;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

import java.io.Serial;

public class SottoUnita extends AbstractUnita {
    private double x,y;

    public SottoUnita(double x, double y) {
        super();
        this.x=x;
        this.y=y;
        setAlignment(Pos.TOP_RIGHT);
        setLayoutX(x);
        setLayoutY(y);
        setPrefHeight(60.0);
        setPrefWidth(150.0);
        getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());
        getStyleClass().add("box");
        setOnMousePressed(this::selectedBox);
        label = new Label("Sotto unità");
        label.setAlignment(Pos.CENTER);
        label.setPrefHeight(60.0);
        label.setPrefWidth(200.0);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setWrapText(true);
        getChildren().addAll(label);

    }
    @Serial
    private Object readResolve(){
        SottoUnita u=new SottoUnita(x,y);
        u.setLabelText(nomeUnita);
        u.setRuoliUnita(ruoliUnita);
        u.setMediator(mediator);
        return u;
    }
}
