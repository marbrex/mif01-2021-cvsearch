package fr.univ_lyon1.info.m1.cv_search.controllers;

import java.io.File;

import fr.univ_lyon1.info.m1.cv_search.model.*;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
  private ComboBox<String> scopeSelector;

  @FXML
  private ToggleButton signSelectorLess;

  @FXML
  private ToggleButton signSelectorGreater;

  @FXML
  private Spinner<Integer> valueSelector;

  @FXML
  private ComboBox<String> sortBySelector;

  @FXML
  private ComboBox<String> orderBySelector;

  @FXML
  private Label cvFoundCountLbl;

  private int cvFoundCount = 0;

  public CvSearchController() {
    System.out.println("IN JfxView CONSTRUCTOR");
  }

  private Label createSkillLabel(String text) {
    Label skillLabel = new Label(text);
    skillLabel.getStyleClass().add("skill-label");

    Label closeBtn = new Label("X");
    closeBtn.getStyleClass().add("skill-label-close");

    skillLabel.setGraphic(closeBtn);
    skillLabel.contentDisplayProperty().setValue(ContentDisplay.RIGHT);

    closeBtn.setOnMouseClicked(mouseEvent -> {
      skillLabelContainer.getChildren().remove(skillLabel);
    });

    return skillLabel;
  }

//  private Map<String, String> convertToMap(String parameter) {
//    final Map<String, String> res = new HashMap<>();
//    String[] splitList = parameter.split(" ");
//    res.put("scope", splitList[0]);
//    res.put("sign", splitList[1]);
//    res.put("value", splitList[2]);
//    return res;
//  }

  private void selectedScopeAll(ApplicantList listApplicants, int selectedValue, int wantedSkillsCount){

    for (Applicant a : listApplicants) {
      int matchSkillsCount = 0;

      for (Node skill : skillLabelContainer.getChildren()) {
        String skillName = ((Label) skill).getText();

        if (signSelectorGreater.isSelected()) {
          if (a.getSkill(skillName) >= selectedValue) {
            matchSkillsCount++;
          }
        }
        else if (signSelectorLess.isSelected()) {
          if (a.getSkill(skillName) <= selectedValue) {
            matchSkillsCount++;
          }
        }
      }

      if (matchSkillsCount == wantedSkillsCount && matchSkillsCount != 0) {
        applicantCardList.getChildren().add(createApplicantCard(a));
      }
    }
  }

  private void selectedScopeAverage(ApplicantList listApplicants, int selectedValue, int wantedSkillsCount){

    for (Applicant a : listApplicants) {
      int skillLevelSum = 0;
      int averageSkill = 0;

      for (Node skill : skillLabelContainer.getChildren()) {
        String skillName = ((Label) skill).getText();
        skillLevelSum += a.getSkill(skillName);
      }
      if(wantedSkillsCount != 0){
        averageSkill = skillLevelSum / wantedSkillsCount;
      }
      else {
        averageSkill = 1;
      }

      if (signSelectorGreater.isSelected()) {
        if (averageSkill >= selectedValue) {
          applicantCardList.getChildren().add(createApplicantCard(a));
        }
      }
      else if (signSelectorLess.isSelected()) {
        if (averageSkill <= selectedValue) {
          applicantCardList.getChildren().add(createApplicantCard(a));
        }
      }
    }
  }

  private void selectedScopeAtLeastOne(ApplicantList listApplicants, int selectedValue){

    for (Applicant a : listApplicants) {
      boolean selected = false;

      for (Node skill : skillLabelContainer.getChildren()) {
        String skillName = ((Label) skill).getText();

        if (signSelectorGreater.isSelected()) {
          if (a.getSkill(skillName) >= selectedValue) {
            selected = true;
            break;
          }
        } else if (signSelectorLess.isSelected()) {
          if (a.getSkill(skillName) <= selectedValue) {
            selected = true;
            break;
          }
        }
      }

      if (selected) {
        applicantCardList.getChildren().add(createApplicantCard(a));
      }
    }
  }

  private void searchApplicants() {

    // clearing already existing list of applicants
    applicantCardList.getChildren().clear();
    cvFoundCount = 0;

    // building applicants' list,
    // that is loading the data from the ".yaml" files into the ApplicantList class
    ApplicantList listApplicants = new ApplicantListBuilder(new File(".")).build();

    int wantedSkillsCount = skillLabelContainer.getChildren().size();
    String selectedScope = scopeSelector.getSelectionModel().getSelectedItem();
    int selectedValue = valueSelector.getValue();

    if (selectedScope.equals("All")) {
      selectedScopeAll(listApplicants, selectedValue, wantedSkillsCount);
    }
    else if (selectedScope.equals("Average")) {
      selectedScopeAverage(listApplicants, selectedValue, wantedSkillsCount);
    }
    else if (selectedScope.equals("At least one")) {
      selectedScopeAtLeastOne(listApplicants, selectedValue);
      }

    cvFoundCountLbl.setText(String.valueOf(cvFoundCount));
    if (cvFoundCount == 0) {
      Label errorMessage = new Label("No Applicants Found !");
      errorMessage.setOpacity(0.5);
      applicantCardList.getChildren().add(errorMessage);
    }
  }

  private VBox createApplicantCard(Applicant a) {
    cvFoundCount++;

    VBox card = new VBox();
    card.getStyleClass().add("cv");

    Label applicantNameLbl = new Label("Name:");
    applicantNameLbl.setFont(Font.font("Regular", FontWeight.BOLD, 12.0));
    card.getChildren().add(applicantNameLbl);

    Label applicantName = new Label(a.getName());
    card.getChildren().add(applicantName);

    Label applicantSkillsLbl = new Label("Skills:");
    applicantSkillsLbl.setFont(Font.font("Regular", FontWeight.BOLD, 12.0));
    card.getChildren().add(applicantSkillsLbl);

    for(Map.Entry<String, Integer> skill : a.getSkills().entrySet()) {
      Label applicantSkills = new Label(skill.getKey() + ": " + skill.getValue());
      card.getChildren().add(applicantSkills);
    }

    return card;
  }

  private void initStrategySelector() {

    SpinnerValueFactory<Integer> svf = new IntegerSpinnerValueFactory(0, 100, 50, 1);
    valueSelector.setValueFactory(svf);

    scopeSelector.getItems().add("All");
    scopeSelector.getItems().add("Average");
    scopeSelector.getItems().add("At least one");

    // Setting the first element as default, that is "All"
    scopeSelector.getSelectionModel().select(0);

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

    scopeSelector.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    initStrategySelector();

    sortBySelector.getItems().add("First Name");
    sortBySelector.getItems().add("Last Name");
    sortBySelector.getItems().add("Chosen Skills");

    // Setting the first element as default, that is "All"
    sortBySelector.getSelectionModel().select(0);

    orderBySelector.getItems().add("Low to High");
    orderBySelector.getItems().add("High to Low");

    // Setting the first element as default, that is "All"
    orderBySelector.getSelectionModel().select(0);

    sortBySelector.setOnAction(actionEvent -> {
      System.out.println("ON ACTION !");
    });

  }
}
