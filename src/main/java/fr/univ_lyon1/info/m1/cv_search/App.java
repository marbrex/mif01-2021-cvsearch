package fr.univ_lyon1.info.m1.cv_search;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.util.Objects;

/**
 * Main class for the application (structure imposed by JavaFX).
 */
public class App extends Application {

    /**
     * Preferred Width of the window.
     */
    private final double windowWidth = 600;

    /**
     * Preferred Height of the window.
     */
    private final double windowHeight = 400;

    /**
     * Minimum Width of the window.
     */
    private final double windowMinWidth = 600;

    /**
     * Minimum Height of the window.
     */
    private final double windowMinHeight = 400;

    /**
     * Global Scene.
     */
    private static Scene scene;

    /**
     * Global Stage.
     */
    private static Stage stage;

    /**
     * With javafx, start() is called when the application is launched.
     */
    @Override
    public final void start(final Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/cv-search-view.fxml"));
        Parent root = loader.load();

        scene = new Scene(root, windowWidth, windowHeight);
        String styleSheet =
            Objects.requireNonNull(getClass().getResource("/css/cv-search-styles.css"))
                .toExternalForm();
        scene.getStylesheets().add(styleSheet);

//    Image appIcon = new Image(getClass()
//        .getResourceAsStream("/img/app-icon.png"));

        primaryStage.setMinWidth(windowMinWidth);
        primaryStage.setMinHeight(windowMinHeight);
        primaryStage.setTitle("CV Search App");
        // primaryStage.getIcons().add(appIcon);
        primaryStage.setScene(scene);
        // primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.show();

        setScene(scene);
        setStage(primaryStage);
    }

    /**
     * Set the global scene.
     *
     * @param myScene Scene to switch
     */
    public final void setScene(final Scene myScene) {
        App.scene = myScene;
    }

    /**
     * Get the global scene.
     *
     * @return Global Scene
     */
    public static Scene getScene() {
        return scene;
    }

    /**
     * Set the global stage.
     *
     * @param myStage Stage to switch
     */
    public final void setStage(final Stage myStage) {
        App.stage = myStage;
    }

    /**
     * Get the global stage.
     *
     * @return Global Stage
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * A main method in case the user launches
     * the application using App as the main class.
     *
     * @param args Arguments passed to the program on launch
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }
}
