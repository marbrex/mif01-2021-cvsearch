package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Applicant {
    private Map<String, Integer> skills = new HashMap<>();
    private List<ApplicantExperience> expList = new ArrayList<>();
    private int expYears = 0;
    private  int companiesCount = 0;
    private int skillsAverage;
    private String name;

    public int getSkill(String string) {
        return skills.getOrDefault(string, 0);
    }

    public Map<String, Integer> getSkills() {
        return Collections.unmodifiableMap(skills);
    }

    public void setSkill(String string, int value) {
        skills.put(string, value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSkillsAverage() {
        return skillsAverage;
    }

    public void setSkillsAverage(int skillsAverage) {
        this.skillsAverage = skillsAverage;
    }

    public void addExp(ApplicantExperience exp) {
        expList.add(exp);
    }

    public List<ApplicantExperience> getExpList() {
        return expList;
    }

    public void setExpYears(int expYears) {
        this.expYears = expYears;
    }

    public int getExpYears() {
        return expYears;
    }

    public int getCompaniesCount() {
        return companiesCount;
    }

    public void setCompaniesCount(int companiesCount) {
        this.companiesCount = companiesCount;
    }
}
