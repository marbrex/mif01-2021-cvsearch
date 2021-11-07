package fr.univ_lyon1.info.m1.cv_search.model.search;

import fr.univ_lyon1.info.m1.cv_search.model.applicant.Applicant;
import fr.univ_lyon1.info.m1.cv_search.model.applicant.ApplicantList;
import fr.univ_lyon1.info.m1.cv_search.model.applicant.ApplicantListBuilder;
import javafx.scene.control.Label;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SearchStrategyAverage implements SearchStrategy {

    @Override
    public List<Applicant> search() {
        System.out.println("SearchStrategyAverage");

        SearchState searchState = SearchState.getInstance();

        // building applicants' list,
        // that is loading the data from the ".yaml" files into the ApplicantList class
        ApplicantList listApplicants = new ApplicantListBuilder(new File(".")).build();

        List<Applicant> res = new ArrayList<>();

        for (Applicant a : listApplicants) {
            int skillLevelSum = 0;
            int averageSkill;

            for (Label skill : searchState.getSkillLabels()) {
                String skillName = skill.getText();
                skillLevelSum += a.getSkill(skillName);
            }
            if (searchState.getWantedSkillsCount() != 0) {
                averageSkill = skillLevelSum / searchState.getWantedSkillsCount();
            } else {
                averageSkill = 1;
            }

            if (searchState.isGreaterSignSelected()) {
                if (averageSkill >= searchState.getSelectedValue()) {
                    res.add(a);
                }
            } else if (searchState.isLessSignSelected()) {
                if (averageSkill <= searchState.getSelectedValue()) {
                    res.add(a);
                }
            }
        }

        return res;
    }

    @Override
    public String toString() {
        return "Average";
    }
}
