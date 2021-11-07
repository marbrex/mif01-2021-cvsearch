package fr.univ_lyon1.info.m1.cv_search.model.SortApplicant;

import fr.univ_lyon1.info.m1.cv_search.model.Applicant.Applicant;

import java.util.Comparator;

public class SortApplicantsByName implements Comparator<Applicant> {

    @Override
    public int compare(Applicant a1, Applicant a2) {
        return a1.getName().compareToIgnoreCase(a2.getName());
    }

    @Override
    public String toString() {
        return "Name";
    }
}
