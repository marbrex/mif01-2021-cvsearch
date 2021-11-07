package fr.univ_lyon1.info.m1.cv_search.model.search;

import fr.univ_lyon1.info.m1.cv_search.model.applicant.Applicant;

import java.util.List;

public interface SearchStrategy {

    List<Applicant> search();
}
