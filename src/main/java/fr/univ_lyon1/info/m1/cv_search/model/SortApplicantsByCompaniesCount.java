package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.Comparator;

public class SortApplicantsByCompaniesCount implements Comparator<Applicant> {

    @Override
    public int compare(Applicant a1, Applicant a2) {
        return Integer.compare(a1.getCompaniesCount(), a2.getCompaniesCount());
    }

    @Override
    public String toString() {
        return "Experience: Companies";
    }

}
