package fr.univ_lyon1.info.m1.cv_search.model.search;

import fr.univ_lyon1.info.m1.cv_search.model.applicant.Applicant;
import fr.univ_lyon1.info.m1.cv_search.model.applicant.ApplicantList;
import fr.univ_lyon1.info.m1.cv_search.model.applicant.ApplicantListBuilder;
import javafx.scene.control.Label;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SearchStrategyAll implements SearchStrategy {

    @Override
    public List<Applicant> search() {
        System.out.println("SearchStrategyAll");

        SearchState searchState = SearchState.getInstance();

        // building applicants' list,
        // that is loading the data from the ".yaml" files into the ApplicantList class
        ApplicantList listApplicants = new ApplicantListBuilder(new File(".")).build();

        List<Applicant> res = new ArrayList<>();

        for (Applicant a : listApplicants) {
            boolean selected = false;
            int matchSkillsCount = 0;
            System.out.println("Applicant = " + a.getName());

            for (Label skill : searchState.getSkillLabels()) {
                String skillName = skill.getText();

                if (searchState.isGreaterSignSelected()) {
                    System.out.println("greater sign selected");

                    if (a.getSkill(skillName) >= searchState.getSelectedValue()) {
                        matchSkillsCount++;
                        System.out.println("is greater or equal than Value");
                        selected = true;
                        break;
                    }
                } else if (searchState.isLessSignSelected()) {
                    System.out.println("less sign selected");
                    if (a.getSkill(skillName) <= searchState.getSelectedValue()) {
                        matchSkillsCount++;
                        System.out.println("is less or equal than Value");
                        selected = true;
                        break;
                    }
                }
            }

            if (matchSkillsCount == searchState.getWantedSkillsCount() && selected) {
                System.out.println("Adding applicant");
                res.add(a);
            }
        }
        return res;
    }
    @Override
    public String toString() {
        return "All";
    }
}
