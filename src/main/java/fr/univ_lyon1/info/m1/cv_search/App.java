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
   *  Window width.
   */
  private final double windowWidth = 600;
  /**
   *  Window height.
   */
  private final double windowHeight = 400;

  private final double windowMinWidth = 600;
  private final double windowMinHeight = 400;

  private static Scene scene;
  private static Stage stage;

  /**
   * With javafx, start() is called when the application is launched.
   */
  public final void start(final Stage primaryStage) throws Exception {

    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/fxml/cv-search-view.fxml"));
    Parent root = loader.load();

    scene = new Scene(root, windowWidth, windowHeight);
    String styleSheet = Objects.requireNonNull(getClass().getResource("/css/cv-search-styles.css")).toExternalForm();
    scene.getStylesheets().add(styleSheet);

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

  public void setScene(final Scene scene) {
    App.scene = scene;
  }

  public static Scene getScene() {
    return scene;
  }

  public void setStage(Stage stage) {
    App.stage = stage;
  }

  public static Stage getStage() {
    return stage;
  }

  /**
   * A main method in case the user launches the application using App as the main class.
   */
  public static void main(String[] args) {
    Application.launch(args);
  }
}
