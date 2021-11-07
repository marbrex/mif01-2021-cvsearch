package fr.univ_lyon1.info.m1.cv_search.model.sort;

import fr.univ_lyon1.info.m1.cv_search.model.applicant.Applicant;

import java.util.Comparator;

public class SortApplicantsBySkillsAmount implements Comparator<Applicant> {

    @Override
    public int compare(Applicant a1, Applicant a2) {
        return Integer.compare(a1.getSkills().size(), a2.getSkills().size());
    }

    @Override
    public String toString() {
        return "Skills: Amount";
    }
}
