package fr.univ_lyon1.info.m1.cv_search.model.search;

import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public final class SearchState {

    private static SearchState instance = null;

    private int wantedSkillsCount;
    private int selectedValue;
    private List<Label> skillLabels;
    private boolean isGreaterSignSelected;
    private boolean isLessSignSelected;

    private SearchState() {
        setWantedSkillsCount(0);
        setSelectedValue(0);
        setSkillLabels(new ArrayList<>());
        setGreaterSignSelected(true);
        setLessSignSelected(false);
    }

    public static SearchState getInstance() {
        if (instance == null) {
            instance = new SearchState();
        }
        return instance;
    }

    public int getSelectedValue() {
        return selectedValue;
    }

    public int getWantedSkillsCount() {
        return wantedSkillsCount;
    }

    public List<Label> getSkillLabels() {
        return skillLabels;
    }

    public boolean isGreaterSignSelected() {
        return isGreaterSignSelected;
    }

    public boolean isLessSignSelected() {
        return isLessSignSelected;
    }

    public void setWantedSkillsCount(int wantedSkillsCount) {
        this.wantedSkillsCount = wantedSkillsCount;
    }

    public void setSkillLabels(List<Label> skillLabels) {
        this.skillLabels = skillLabels;
    }

    public void setSelectedValue(int selectedValue) {
        this.selectedValue = selectedValue;
    }

    public void setLessSignSelected(boolean lessSignSelected) {
        isLessSignSelected = lessSignSelected;
    }

    public void setGreaterSignSelected(boolean greaterSignSelected) {
        isGreaterSignSelected = greaterSignSelected;
    }
}
