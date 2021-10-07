package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Applicant {
    private Map<String, Integer> skills = new HashMap<>();
    private String name;

    public int getSkill(String string) {
        if ((string.getClass()).equals(java.lang.String.class))
            return skills.getOrDefault(string, 0);
        else
            return -1;
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
}
