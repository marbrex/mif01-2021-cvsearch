package fr.univ_lyon1.info.m1.cv_search.model.SearchStrategy;

import fr.univ_lyon1.info.m1.cv_search.controllers.CvSearchController;
import fr.univ_lyon1.info.m1.cv_search.model.Applicant.Applicant;
import fr.univ_lyon1.info.m1.cv_search.model.Applicant.ApplicantList;
import fr.univ_lyon1.info.m1.cv_search.model.Applicant.ApplicantListBuilder;
import javafx.scene.control.Label;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SearchStrategyAverage implements SearchStrategy {

    @Override
    public List<Applicant> search() {
        System.out.println("SearchStrategyAverage");

        // building applicants' list,
        // that is loading the data from the ".yaml" files into the ApplicantList class
        ApplicantList listApplicants = new ApplicantListBuilder(new File(".")).build();

        List<Applicant> res = new ArrayList<>();

        for (Applicant a : listApplicants) {
            int skillLevelSum = 0;
            int averageSkill;

            for (Label skill : CvSearchController.getSkillLabels()) {
                String skillName = skill.getText();
                skillLevelSum += a.getSkill(skillName);
            }
            if (CvSearchController.getWantedSkillsCount() != 0) {
                averageSkill = skillLevelSum / CvSearchController.getWantedSkillsCount();
            } else {
                averageSkill = 1;
            }

            if (CvSearchController.isGreaterSignSelected()) {
                if (averageSkill >= CvSearchController.getSelectedValue()) {
                    res.add(a);
                }
            } else if (CvSearchController.isLessSignSelected()) {
                if (averageSkill <= CvSearchController.getSelectedValue()) {
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
