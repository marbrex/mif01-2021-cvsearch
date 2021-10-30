package fr.univ_lyon1.info.m1.cv_search;

import fr.univ_lyon1.info.m1.cv_search.views.CvSearchView;
import fr.univ_lyon1.info.m1.cv_search.views.JfxView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class for the application (structure imposed by JavaFX).
 */
public class App extends Application {

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

        JfxView cvSearchView = new CvSearchView(primaryStage);

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
