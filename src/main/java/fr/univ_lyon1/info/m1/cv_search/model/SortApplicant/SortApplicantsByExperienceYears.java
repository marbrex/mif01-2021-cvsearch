package fr.univ_lyon1.info.m1.cv_search.model.SortApplicant;

import fr.univ_lyon1.info.m1.cv_search.model.Applicant.Applicant;

import java.util.Comparator;

public class SortApplicantsByExperienceYears implements Comparator<Applicant> {

    @Override
    public int compare(Applicant a1, Applicant a2) {
        return Integer.compare(a1.getExpYears(), a2.getExpYears());
    }

    @Override
    public String toString() {
        return "Experience: Years";
    }

}
