package fr.univ_lyon1.info.m1.cv_search.model;

import fr.univ_lyon1.info.m1.cv_search.controllers.CvSearchController;
import javafx.scene.control.Label;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SearchStrategyAtLeastOne implements SearchStrategy {

    @Override
    public List<Applicant> search() {
        System.out.println("SearchStrategyAtLeastOne");

        // building applicants' list,
        // that is loading the data from the ".yaml" files into the ApplicantList class
        ApplicantList listApplicants = new ApplicantListBuilder(new File(".")).build();

        List<Applicant> res = new ArrayList<>();

        System.out.println("Value = " + CvSearchController.getSelectedValue());

        for (Applicant a : listApplicants) {
            boolean selected = false;
            System.out.println("Applicant = " + a.getName());

            for (Label skill : CvSearchController.getSkillLabels()) {
                String skillName = skill.getText();
                System.out.println("Skill = " + skillName);

                if (CvSearchController.isGreaterSignSelected()) {
                    System.out.println("greater sign selected");
                    if (a.getSkill(skillName) >= CvSearchController.getSelectedValue()) {
                        System.out.println("is greater or equal than Value");
                        selected = true;
                        break;
                    }
                } else if (CvSearchController.isLessSignSelected()) {
                    System.out.println("less sign selected");
                    if (a.getSkill(skillName) <= CvSearchController.getSelectedValue()) {
                        System.out.println("is less or equal than Value");
                        selected = true;
                        break;
                    }
                }
            }

            if (selected) {
                System.out.println("Adding applicant");
                res.add(a);
            }
        }

        return res;
    }

    @Override
    public String toString() {
        return "At Least One";
    }
}
