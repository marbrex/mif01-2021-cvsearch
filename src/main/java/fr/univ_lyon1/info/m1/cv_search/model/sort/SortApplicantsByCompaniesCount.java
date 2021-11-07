package fr.univ_lyon1.info.m1.cv_search.model.sort;

import fr.univ_lyon1.info.m1.cv_search.model.applicant.Applicant;

import java.util.Comparator;

public class SortApplicantsByCompaniesCount implements Comparator<Applicant> {

    @Override
    public int compare(Applicant a1, Applicant a2) {
        try {
            int result = Integer.compare(a1.getCompaniesCount(), a2.getCompaniesCount());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -666;
        }
    }

    @Override
    public String toString() {
        return "Experience: Companies";
    }

}
