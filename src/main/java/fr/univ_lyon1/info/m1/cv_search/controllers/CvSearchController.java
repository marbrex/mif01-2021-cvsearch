package fr.univ_lyon1.info.m1.cv_search.controllers;

import fr.univ_lyon1.info.m1.cv_search.model.Applicant;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.univ_lyon1.info.m1.cv_search.model.SearchStrategy;
import fr.univ_lyon1.info.m1.cv_search.model.SearchStrategyAtLeastOne;
import fr.univ_lyon1.info.m1.cv_search.model.SearchStrategyAverage;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CvSearchController {

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
    private ComboBox<SearchStrategy> scopeSelector;

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

    private static int wantedSkillsCount = 0;
    private static int selectedValue = 0;
    private static List<Label> skillLabels = new ArrayList<>();
    private static boolean isGreaterSignSelected = true;
    private static boolean isLessSignSelected = false;

    public static int getWantedSkillsCount() {
        return wantedSkillsCount;
    }

    public static int getSelectedValue() {
        return selectedValue;
    }

    public static List<Label> getSkillLabels() {
        return skillLabels;
    }

    public static boolean isGreaterSignSelected() {
        return isGreaterSignSelected;
    }

    public static boolean isLessSignSelected() {
        return isLessSignSelected;
    }

    public CvSearchController() {
        System.out.println("IN CvSearchController CONSTRUCTOR");
    }

    /**
     * Creates and returns a label with the specified text.
     *
     * @param text Skill to add
     * @return Skill Label
     */
    private Label createSkillLabel(final String text) {
        Label skillLabel = new Label(text.toLowerCase());
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

    private void selectedScopeAll(final ApplicantList listApplicants, final int selectedValue,
                                  final int wantedSkillsCount) {

        for (Applicant a : listApplicants) {
            int matchSkillsCount = 0;

            for (Node skill : skillLabelContainer.getChildren()) {
                String skillName = ((Label) skill).getText();

                if (signSelectorGreater.isSelected()) {
                    if (a.getSkill(skillName) >= selectedValue) {
                        matchSkillsCount++;
                    }
                } else if (signSelectorLess.isSelected()) {
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

    /**
     * Searches for the CVs, and displays them if they're matching
     * user specified parameters.
     */
    private void searchApplicants() {

        // clearing already existing list of applicants
        applicantCardList.getChildren().clear();
        skillLabels.clear();

        wantedSkillsCount = skillLabelContainer.getChildren().size();
        selectedValue = valueSelector.getValue();
        skillLabelContainer.getChildren().forEach(node -> {
            skillLabels.add((Label) node);
        });

        List<Applicant> applicants = scopeSelector.getSelectionModel().getSelectedItem().search();

        cvFoundCountLbl.setText(String.valueOf(applicants.size()));
        if (applicants.isEmpty()) {
            // No applicant found
            Label errorMessage = new Label("No Applicants Found !");
            errorMessage.setOpacity(0.5);
            applicantCardList.getChildren().add(errorMessage);
        } else {
            // Create CV cards
            for (Applicant applicant : applicants) {
                applicantCardList.getChildren().add(createApplicantCard(applicant));
            }
        }
    }

    /**
     * Creates and returns a CV card of the specified applicant.
     *
     * @param a Applicant
     * @return A rectangular node, representing the CV
     */
    private VBox createApplicantCard(final Applicant a) {

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

        for (Map.Entry<String, Integer> skill : a.getSkills().entrySet()) {
            Label applicantSkills = new Label(skill.getKey() + ": " + skill.getValue());
            card.getChildren().add(applicantSkills);
        }

        return card;
    }

    /**
     * Initializing search controls.
     */
    private void initStrategySelector() {

        // setting an event handler
        addSkillBtn.setOnMouseClicked(mouseEvent -> {
            String skillEntered = addSkillField.getCharacters().toString();

            if (skillEntered.equals("")) {
                return;
            }

            // before creating a label, let's see
            // whether there is already a label with that skill
            boolean lblExists = skillLabelContainer.getChildren().stream().map(n -> (Label) n)
                .anyMatch(skillLbl -> skillLbl.getText().equalsIgnoreCase(skillEntered));

            // and if there is no a label with that skill
            // we'll create a new one
            if (!lblExists) {
                Label skillLabel = createSkillLabel(skillEntered);
                skillLabelContainer.getChildren().add(skillLabel);
            }

            // resetting the skill input field
            addSkillField.setText("");
        });

        // setting an event handler
        searchBtn.setOnMouseClicked(mouseEvent -> searchApplicants());

        // just for styling, needed to resize controls on window resizing
        HBox.setHgrow(searchBtn, Priority.ALWAYS);
        searchBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(addSkillField, Priority.ALWAYS);
        addSkillField.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        scopeSelector.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // init strategy value selector
        SpinnerValueFactory<Integer> svf = new IntegerSpinnerValueFactory(0, 100, 50, 1);
        valueSelector.setValueFactory(svf);

        // init strategy scope selector
        scopeSelector.getItems().add(new SearchStrategyAverage());
        scopeSelector.getItems().add(new SearchStrategyAtLeastOne());

        // Setting the first element as default, that is "All"
        scopeSelector.getSelectionModel().select(0);

        signSelectorGreater.setOnAction(actionEvent -> {
            System.out
                .println("signSelectorGreater pressed ! VALUE=" + signSelectorGreater.isSelected());
            isGreaterSignSelected = signSelectorGreater.isSelected();
        });

        signSelectorLess.setOnAction(actionEvent -> {
            System.out.println("signSelectorLess pressed ! VALUE=" + signSelectorLess.isSelected());
            isLessSignSelected = signSelectorLess.isSelected();
        });

    }

    /**
     * Initializing sorting controls.
     */
    private void initSortSelector() {

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
            // TODO
            System.out.println("ON ACTION !");
        });

    }

    /**
     * The FXML loader will call the initialize() method after the loading of the FXML document is
     * complete.
     */
    @FXML
    private void initialize() {
        System.out.println("IN INIT");

        initStrategySelector();
        initSortSelector();

    }
}
