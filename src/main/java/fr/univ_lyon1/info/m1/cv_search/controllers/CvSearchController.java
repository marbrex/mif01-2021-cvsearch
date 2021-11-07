package fr.univ_lyon1.info.m1.cv_search.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.univ_lyon1.info.m1.cv_search.model.applicant.Applicant;
import fr.univ_lyon1.info.m1.cv_search.model.applicant.ApplicantList;
import fr.univ_lyon1.info.m1.cv_search.model.applicant.ApplicantListBuilder;
import fr.univ_lyon1.info.m1.cv_search.model.applicant.ApplicantSearchResults;
import fr.univ_lyon1.info.m1.cv_search.model.search.SearchState;
import fr.univ_lyon1.info.m1.cv_search.model.search.SearchStrategy;
import fr.univ_lyon1.info.m1.cv_search.model.search.SearchStrategyAll;
import fr.univ_lyon1.info.m1.cv_search.model.search.SearchStrategyAtLeastOne;
import fr.univ_lyon1.info.m1.cv_search.model.search.SearchStrategyAverage;
import fr.univ_lyon1.info.m1.cv_search.model.sort.SortApplicantsByCompaniesCount;
import fr.univ_lyon1.info.m1.cv_search.model.sort.SortApplicantsByExperienceYears;
import fr.univ_lyon1.info.m1.cv_search.model.sort.SortApplicantsByName;
import fr.univ_lyon1.info.m1.cv_search.model.sort.SortApplicantsBySkillsAmount;
import fr.univ_lyon1.info.m1.cv_search.model.sort.SortApplicantsBySkillsAverage;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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
    private ComboBox<Comparator<Applicant>> sortBySelector;

    @FXML
    private ToggleButton orderByAscend;

    @FXML
    private ToggleButton orderByDescend;

    @FXML
    private Label cvFoundCountLbl;

    @FXML
    private ContextMenu searchSuggestions;

    private final ApplicantSearchResults results = new ApplicantSearchResults();

    private final ApplicantList listApplicants = new ApplicantListBuilder(new File(".")).build();

    private final SearchState searchState = SearchState.getInstance();

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

    /**
     * Searches for the CVs, and displays them if they're matching
     * user specified parameters.
     */
    private void searchApplicants() {

        // clearing already previous search results
        searchState.getSkillLabels().clear();

        // setting static variables, so they can be accessed in model classes
        searchState.setWantedSkillsCount(skillLabelContainer.getChildren().size());
        searchState.setSelectedValue(valueSelector.getValue());
        skillLabelContainer.getChildren().forEach(node -> {
            searchState.getSkillLabels().add((Label) node);
        });

        if (!results.getList().isEmpty()) {
            results.getList().clear();
        }
        results.getList().addAll(scopeSelector.getSelectionModel().getSelectedItem().search());
        drawApplicantCards();
    }

    private void drawApplicantCards(boolean reverse) {

        // clearing already existing list of applicants on the view
        applicantCardList.getChildren().clear();

        // sorting the results
        if (reverse) {
            results.sort(sortBySelector.getSelectionModel().getSelectedItem().reversed());
        } else {
            results.sort();
        }

        cvFoundCountLbl.setText(String.valueOf(results.getList().size()));
        if (results.getList().isEmpty()) {
            // No applicant found
            Label errorMessage = new Label("No Applicants Found !");
            errorMessage.setOpacity(0.5);
            applicantCardList.getChildren().add(errorMessage);
        } else {
            // Create CV cards
            for (Applicant applicant : results.getList()) {
                applicantCardList.getChildren().add(createApplicantCard(applicant));
            }
        }
    }

    private void drawApplicantCards() {
        drawApplicantCards(orderByDescend.isSelected());
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

        int skillsMatchCount = 0;
        int skillsSum = 0;
        int skillsAverage = 0;
        List<Label> applicantSkillLabels = new ArrayList<>();
        for (Map.Entry<String, Integer> skill : a.getSkills().entrySet()) {
            applicantSkillLabels.add(new Label(skill.getKey() + ": " + skill.getValue()));

            for (Label label : searchState.getSkillLabels()) {
                if (label.getText().equals(skill.getKey())) {
                    skillsSum += skill.getValue();
                    ++skillsMatchCount;
                }
            }
        }
        if (skillsMatchCount != 0) {
            skillsAverage = skillsSum / skillsMatchCount;
        }
        a.setSkillsAverage(skillsAverage);

        Label applicantSkillsLbl = new Label("Skills: | AVG " + skillsAverage);
        applicantSkillsLbl.setFont(Font.font("Regular", FontWeight.BOLD, 12.0));
        card.getChildren().add(applicantSkillsLbl);

        card.getChildren().addAll(applicantSkillLabels);

        Label expLbl = new Label("Experience: | " + a.getExpYears() + " years");
        expLbl.setFont(Font.font("Regular", FontWeight.BOLD, 12.0));
        card.getChildren().add(expLbl);

        a.getExpList().forEach(exp -> {
            Label companyNameLbl =
                    new Label(exp.getName() + ", " + exp.getStart() + "-" + exp.getEnd()
                    + " (" + exp.getYears() + ")");
            companyNameLbl.setFont(Font.font("Regular", FontWeight.BOLD, 12.0));
            card.getChildren().add(companyNameLbl);

            exp.getKeywords().forEach(kw -> {
                Label kwLbl = new Label(kw);
                card.getChildren().add(kwLbl);
            });
        });

        return card;
    }

    private Set<String> findMatchingSkills(String text) {
        Set<String> skillsFound = new HashSet<>();

        listApplicants.getList().forEach(applicant -> {
            applicant.getSkills().keySet().forEach(skill -> {
                if (skill.contains(text) && !skill.equals(text)) {
                    skillsFound.add(skill);
                }
            });
        });

        return skillsFound;
    }

    private void addSearchSuggestion(String text) {
        MenuItem item = new MenuItem(text);
        item.setOnAction(event -> {
            addSkillField.setText(item.getText());
            addSkillField.positionCaret(item.getText().length());
        });
        searchSuggestions.getItems().add(item);
    }

    /**
     * Initializing search controls.
     */
    private void initStrategySelector() {

        addSkillField.setOnKeyTyped(event -> {
            if (!addSkillField.getText().isEmpty()) {
                if (!searchSuggestions.isShowing() && !searchSuggestions.getItems().isEmpty()) {
                    searchSuggestions.show(addSkillField, Side.BOTTOM, 0, 0);
                }
            } else {
                searchSuggestions.hide();
            }
            Set<String> res = findMatchingSkills(addSkillField.getText());
            searchSuggestions.getItems().clear();
            res.forEach(this::addSearchSuggestion);
        });

        // setting an event handler
        addSkillBtn.setOnAction(mouseEvent -> {
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
        searchBtn.setOnAction(mouseEvent -> searchApplicants());

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
        scopeSelector.getItems().add(new SearchStrategyAll());
        scopeSelector.getItems().add(new SearchStrategyAverage());
        scopeSelector.getItems().add(new SearchStrategyAtLeastOne());

        // Setting the first element as default, that is "All"
        scopeSelector.getSelectionModel().select(0);

        signSelectorGreater.setMouseTransparent(true);
        signSelectorGreater.setOnMouseClicked(mouseEvent -> {
            // normally the value here should always be TRUE
            System.out
                .println("signSelectorGreater pressed ! VALUE=" + signSelectorGreater.isSelected());
            searchState.setGreaterSignSelected(signSelectorGreater.isSelected());

            if (signSelectorGreater.isSelected()) {
                // disabling interaction with this button
                signSelectorGreater.setMouseTransparent(true);
                // enabling interaction with another button
                signSelectorLess.setMouseTransparent(false);
                // needed for a proper working of the selector
                // this variable is being accessed outside of the class
                searchState.setLessSignSelected(false);
            }
        });

        signSelectorLess.setOnMouseClicked(mouseEvent -> {
            System.out.println("signSelectorLess pressed ! VALUE=" + signSelectorLess.isSelected());
            searchState.setLessSignSelected(signSelectorLess.isSelected());

            if (signSelectorLess.isSelected()) {
                signSelectorLess.setMouseTransparent(true);
                signSelectorGreater.setMouseTransparent(false);
                searchState.setGreaterSignSelected(false);
            }
        });

    }

    /**
     * Initializing sorting controls.
     */
    private void initSortSelector() {

        sortBySelector.getItems().add(new SortApplicantsByName());
        sortBySelector.getItems().add(new SortApplicantsBySkillsAmount());
        sortBySelector.getItems().add(new SortApplicantsBySkillsAverage());
        sortBySelector.getItems().add(new SortApplicantsByExperienceYears());
        sortBySelector.getItems().add(new SortApplicantsByCompaniesCount());

        // Setting the first element as default, that is "All"
        sortBySelector.getSelectionModel().select(0);

        orderByAscend.setOnMouseClicked(mouseEvent -> {
            System.out.println("OrderByAscending pressed ! VALUE=" + orderByAscend.isSelected());
//            results.reverseOrder();
            if (!searchState.getSkillLabels().isEmpty()) {
                drawApplicantCards();
            }

            if (orderByAscend.isSelected()) {
                orderByAscend.setMouseTransparent(true);
                orderByDescend.setMouseTransparent(false);
            }
        });

        orderByDescend.setOnMouseClicked(mouseEvent -> {
            System.out.println("orderByDescending pressed ! VALUE=" + orderByDescend.isSelected());
//            results.reverseOrder();
            if (!searchState.getSkillLabels().isEmpty()) {
                drawApplicantCards(true);
            }

            if (orderByDescend.isSelected()) {
                orderByDescend.setMouseTransparent(true);
                orderByAscend.setMouseTransparent(false);
            }
        });

        sortBySelector.setOnAction(actionEvent -> {
            System.out.println("Selected sort: "
                + sortBySelector.getSelectionModel().getSelectedItem());
            results.setComparator(sortBySelector.getSelectionModel().getSelectedItem());

            if (!searchState.getSkillLabels().isEmpty()) {
                // Sorting only if search has been made
                // otherwise ignore
                drawApplicantCards();
            }
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
