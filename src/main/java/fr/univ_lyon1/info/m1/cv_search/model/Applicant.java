package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Applicant {
    Map<String, Integer> skills = new HashMap<>();
    String name;

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

    public float getMeanSkills(){
        float meanSkills = 0;
        for (int i = 0; i < this.skills.size(); ++i){
            meanSkills += skills.get(i);
        }
        meanSkills /= this.skills.size();
        return meanSkills;
    }
}
