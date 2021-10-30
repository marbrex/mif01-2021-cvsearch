package fr.univ_lyon1.info.m1.cv_search.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public abstract class JfxView {

    private Stage stage;
    private URL fxml = getClass().getResource("/fxml/cv-search-view.fxml");
    private URL css = getClass().getResource("/css/cv-search-styles.css");

    private double width = 600;
    private double height = 400;
    private double minWidth = 600;
    private double minHeight = 400;
    private String title = "CV Search App";

    public JfxView() {
        stage = new Stage();
        loadFxml();
    }

    public JfxView(Stage stage) {
        this.stage = stage;
        loadFxml();
    }

    public JfxView(double width, double height, double minWidth, double minHeight, String title) {
        stage = new Stage();

        this.width = width;
        this.height = height;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.title = title;

        loadFxml();
    }

    protected void loadFxml() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(fxml);
            Parent root = loader.load();

            Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
            String styleSheet = Objects.requireNonNull(css).toExternalForm();
            scene.getStylesheets().add(styleSheet);

            stage.setWidth(width);
            stage.setHeight(height);
            stage.setMinWidth(minWidth);
            stage.setMinHeight(minHeight);
            stage.setTitle(title);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Stage getStage() {
        return stage;
    }

    protected void setStage(Stage stage) {
        this.stage = stage;
    }

    protected URL getFxml() {
        return fxml;
    }

    protected void setFxml(String fxml) {
        this.fxml = getClass().getResource(fxml);
    }

    protected URL getCss() {
        return css;
    }

    protected void setCss(String css) {
        this.css = getClass().getResource(css);
    }
}
