package fr.univ_lyon1.info.m1.cv_search.model.Applicant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class ApplicantBuilder {

    private File file;

    public ApplicantBuilder(File f) {
        this.file = f;
    }

    public ApplicantBuilder(String filename) {
        this.file = new File(filename);
    }

    /**
     * Build the applicant from the Yaml file provided to the constructor.
     */
    public Applicant build() {
        Applicant a = new Applicant();
        Yaml yaml = new Yaml();
        Map<String, Object> map;
        try {
            map = yaml.load(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new Error(e);
        }

        a.setName((String) map.get("name"));

        // Cast may fail if the Yaml is incorrect. Ideally we should provide
        // clean error messages.
        @SuppressWarnings("unchecked")
        Map<String, Integer> skills = (Map<String, Integer>) map.get("skills");

        for (String skill : skills.keySet()) {
            Integer value = skills.get(skill);
            a.setSkill(skill, value);
        }



        @SuppressWarnings("unchecked")
        Map<String, Map<String, Object>> expMap =
                (Map<String, Map<String, Object>>) map.get("experience");

        int yearsCount = 0;
        int companiesCount = 0;
        for (Map.Entry<String, Map<String, Object>> entry : expMap.entrySet()) {
            String s = entry.getKey();
            Map<String, Object> exp = entry.getValue();

            int start = (int) exp.get("start");
            int end = (int) exp.get("end");
            int years = Math.abs(end - start);
            yearsCount += years;
            ++companiesCount;

            @SuppressWarnings("unchecked")
            List<String> keywords = (List<String>) exp.get("keywords");

            a.addExp(new ApplicantExperience(s, start, end, keywords, years));
        }

        a.setExpYears(yearsCount);
        a.setCompaniesCount(companiesCount);

        return a;
    }
}
