package fr.univ_lyon1.info.m1.cv_search.model.SearchStrategy;

import fr.univ_lyon1.info.m1.cv_search.model.Applicant.Applicant;

import java.util.List;

public interface SearchStrategy {

    List<Applicant> search();
}
