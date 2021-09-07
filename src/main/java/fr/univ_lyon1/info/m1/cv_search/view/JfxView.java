package fr.univ_lyon1.info.m1.cv_search.view;

import java.io.File;

import fr.univ_lyon1.info.m1.cv_search.model.Applicant;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantList;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantListBuilder;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class JfxView {

  @FXML
  private BorderPane root;

  @FXML
  private Button addSkillBtn;

  @FXML
  private TextField addSkillField;

  @FXML
  private FlowPane skillLabelContainer;

  @FXML
  private Button searchBtn;

  @FXML
  private TilePane applicantCardList;

  public JfxView() {
    System.out.println("IN JfxView CONSTRUCTOR");
  }

  private Label createSkillLabel(String text) {
    Label skillLabel = new Label(text);
    skillLabel.getStyleClass().add("skill-label");

    skillLabel.setOnMouseClicked(mouseEvent -> {
      skillLabelContainer.getChildren().remove(skillLabel);
    });

    return skillLabel;
  }

  private void searchApplicants() {

    applicantCardList.getChildren().clear();

    // build applicants' list
    ApplicantList listApplicants = new ApplicantListBuilder(new File(".")).build();

    for (Applicant a : listApplicants) {
      boolean selected = false;
      for (Node skill : skillLabelContainer.getChildren()) {
        String skillName = ((Label) skill).getText();
        if (a.getSkill(skillName) > 50) {
          selected = true;
          break;
        }
      }
      if (selected) {
        applicantCardList.getChildren().add(createApplicantCard(a.getName()));
      }
    }

  }

  private VBox createApplicantCard(String name) {
    VBox card = new VBox();
    card.getStyleClass().add("cv");

    Label applicantName = new Label(name);
    card.getChildren().add(applicantName);

    return card;
  }

  /**
   * The FXML loader will call the initialize() method after the loading of the FXML document is
   * complete.
   */
  @FXML
  private void initialize() {
    System.out.println("IN INIT");

    addSkillBtn.setOnMouseClicked(mouseEvent -> {
      String skillEntered = addSkillField.getCharacters().toString();

        if (skillEntered.equals("")) {
            return;
        }

      Label skillLabel = createSkillLabel(skillEntered);
      skillLabelContainer.getChildren().add(skillLabel);

      addSkillField.setText("");
    });

    searchBtn.setOnMouseClicked(mouseEvent -> searchApplicants());

    HBox.setHgrow(searchBtn, Priority.ALWAYS);
    searchBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    HBox.setHgrow(addSkillField, Priority.ALWAYS);
    addSkillField.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

  }
}
