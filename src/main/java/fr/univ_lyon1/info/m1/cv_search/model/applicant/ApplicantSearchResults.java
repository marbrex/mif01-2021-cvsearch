package fr.univ_lyon1.info.m1.cv_search.model.applicant;

import fr.univ_lyon1.info.m1.cv_search.model.sort.SortApplicantsByName;

import java.util.Comparator;

public class ApplicantSearchResults extends ApplicantList {

    private Comparator<Applicant> comparator;

    public ApplicantSearchResults() {
        this.comparator = new SortApplicantsByName();
    }

    public ApplicantSearchResults(Comparator<Applicant> comparator) {
        this.comparator = comparator;
    }

    public void sort() {
        getList().sort(comparator);
    }

    public void reverseOrder() {
        comparator = comparator.reversed();
    }

    public void sort(Comparator<Applicant> comparator) {
        getList().sort(comparator);
    }

    public void setComparator(Comparator<Applicant> comparator) {
        this.comparator = comparator;
    }
}
