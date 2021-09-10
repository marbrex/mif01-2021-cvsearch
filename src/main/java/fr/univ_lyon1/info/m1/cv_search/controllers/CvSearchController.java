package fr.univ_lyon1.info.m1.cv_search.controllers;

import java.io.File;

import fr.univ_lyon1.info.m1.cv_search.model.Applicant;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantList;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantListBuilder;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class CvSearchController {

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

  @FXML
  private ComboBox<String> stratWhiteList;

  public CvSearchController() {
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

  private Map<String, String> convertToMap(String parameter) {
    final Map<String, String> res = new HashMap<>();
    String[] splitList = parameter.split(" ");
    res.put("scope", splitList[0]);
    res.put("sign", splitList[1]);
    res.put("value", splitList[2]);
    return res;
  }

  private void searchApplicants() {

    applicantCardList.getChildren().clear();

    // build applicants' list
    ApplicantList listApplicants = new ApplicantListBuilder(new File(".")).build();

    String selectedStrat = stratWhiteList.getSelectionModel().getSelectedItem();
    Map selectedStratMap = convertToMap(selectedStrat);

    int wantedSkillsCount = skillLabelContainer.getChildren().size();

    if (selectedStratMap.get("scope").equals("All")) {

      for (Applicant a : listApplicants) {
        int matchSkillsCount = 0;

        for (Node skill : skillLabelContainer.getChildren()) {
          String skillName = ((Label) skill).getText();

          if (selectedStratMap.get("sign").equals(">=")) {
            if (a.getSkill(skillName) >= Integer.parseInt(selectedStratMap.get("value").toString())) {
              matchSkillsCount++;
            }
          }
          else if (selectedStratMap.get("sign").equals("<=")) {
            if (a.getSkill(skillName) <= Integer.parseInt(selectedStratMap.get("value").toString())) {
              matchSkillsCount++;
            }
          }
        }

        if (matchSkillsCount == wantedSkillsCount) {
          applicantCardList.getChildren().add(createApplicantCard(a.getName()));
        }
      }
    }
    else if (selectedStratMap.get("scope").equals("Average")) {

      for (Applicant a : listApplicants) {
        int skillLevelSum = 0;
        int averageSkill = 0;

        for (Node skill : skillLabelContainer.getChildren()) {
          String skillName = ((Label) skill).getText();
          skillLevelSum += a.getSkill(skillName);
        }

        averageSkill = skillLevelSum / wantedSkillsCount;

        if (selectedStratMap.get("sign").equals(">=")) {
          if (averageSkill >= Integer.parseInt(selectedStratMap.get("value").toString())) {
            applicantCardList.getChildren().add(createApplicantCard(a.getName()));
          }
        }
        else if (selectedStratMap.get("sign").equals("<=")) {
          if (averageSkill <= Integer.parseInt(selectedStratMap.get("value").toString())) {
            applicantCardList.getChildren().add(createApplicantCard(a.getName()));
          }
        }
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

    stratWhiteList.getItems().add("All >= 50");
    stratWhiteList.getItems().add("All >= 60");
    stratWhiteList.getItems().add("All <= 50");
    stratWhiteList.getItems().add("Average >= 50");
    stratWhiteList.getItems().add("Average >= 60");

    // Setting the first element as default, that is
    stratWhiteList.getSelectionModel().select(0);



  }
}
