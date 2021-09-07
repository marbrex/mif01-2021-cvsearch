package fr.univ_lyon1.info.m1.cv_search;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

/**
 * Main class for the application (structure imposed by JavaFX).
 */
public class App extends Application {

  final private double windowWidth = 900;
  final private double windowHeight = 700;

  final private double windowMinWidth = 620;
  final private double windowMinHeight = 500;

  private static Scene scene;
  private static Stage stage;

  /**
   * With javafx, start() is called when the application is launched.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {

    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/fxml/cv-search-view.fxml"));
    Parent root = loader.load();

    Scene scene = new Scene(root, windowWidth, windowHeight);
    scene.getStylesheets()
        .add(getClass().getResource("/css/cv-search-styles.css").toExternalForm());

    // Image appIcon = new Image(getClass().getResourceAsStream("/img/app-icon.png"));

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

  public void setScene(Scene scene) {
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
