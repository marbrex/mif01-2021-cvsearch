package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.Comparator;

public class SortApplicantsByName implements Comparator<Applicant> {

    @Override
    public int compare(Applicant a1, Applicant a2) {
        return a1.getName().compareToIgnoreCase(a2.getName());
    }

    @Override
    public Comparator<Applicant> reversed() {
        return null;
    }

    @Override
    public String toString() {
        return "Name";
    }
}
