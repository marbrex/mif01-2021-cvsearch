package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ApplicantExperience {

    private String name = "";
    private int start;
    private int end;
    private Set<String> keywords = new HashSet<>();
    private int years = 0;

    ApplicantExperience(String name, int start, int end, Collection<String> keywords, int years) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.keywords.addAll(keywords);
        this.years = years;
    }

    public String getName() {
        return name;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public int getYears() {
        return years;
    }
}
