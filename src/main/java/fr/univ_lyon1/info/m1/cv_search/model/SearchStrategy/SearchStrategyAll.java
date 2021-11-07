package fr.univ_lyon1.info.m1.cv_search.model.SearchStrategy;

import fr.univ_lyon1.info.m1.cv_search.controllers.CvSearchController;
import fr.univ_lyon1.info.m1.cv_search.model.Applicant.Applicant;
import fr.univ_lyon1.info.m1.cv_search.model.Applicant.ApplicantList;
import fr.univ_lyon1.info.m1.cv_search.model.Applicant.ApplicantListBuilder;
import javafx.scene.control.Label;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SearchStrategyAll implements SearchStrategy {

    @Override
    public List<Applicant> search() {
        System.out.println("SearchStrategyAll");

        // building applicants' list,
        // that is loading the data from the ".yaml" files into the ApplicantList class
        ApplicantList listApplicants = new ApplicantListBuilder(new File(".")).build();

        List<Applicant> res = new ArrayList<>();

        for (Applicant a : listApplicants) {
            boolean selected = false;
            int matchSkillsCount = 0;
            System.out.println("Applicant = " + a.getName());

            for (Label skill : CvSearchController.getSkillLabels()) {
                String skillName = skill.getText();

                if (CvSearchController.isGreaterSignSelected()) {
                    System.out.println("greater sign selected");

                    if (a.getSkill(skillName) >= CvSearchController.getSelectedValue()) {
                        matchSkillsCount++;
                        System.out.println("is greater or equal than Value");
                        selected = true;
                        break;
                    }
                } else if (CvSearchController.isLessSignSelected()) {
                    System.out.println("less sign selected");
                    if (a.getSkill(skillName) <= CvSearchController.getSelectedValue()) {
                        matchSkillsCount++;
                        System.out.println("is less or equal than Value");
                        selected = true;
                        break;
                    }
                }
            }

            if (matchSkillsCount == CvSearchController.getWantedSkillsCount() && selected) {
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
