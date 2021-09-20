package fr.univ_lyon1.info.m1.cv_search.model;

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
        return res;
    }
}
