package fr.univ_lyon1.info.m1.cv_search.model.sort;

import fr.univ_lyon1.info.m1.cv_search.model.applicant.Applicant;
import fr.univ_lyon1.info.m1.cv_search.model.applicant.ApplicantBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortApplicantsBySkillsAverageTest {
    SortApplicantsBySkillsAverage c;

    @BeforeEach
    void setUp() {
        c = new SortApplicantsBySkillsAverage();
    }

    @Test
    @DisplayName("Simple comparaison should work")
    void compare() {
        // Given
        ApplicantBuilder builder1 = new ApplicantBuilder("applicant1.yaml");
        ApplicantBuilder builder2 = new ApplicantBuilder("applicant8.yaml");

        // When
        Applicant a = builder1.build();
        Applicant b = builder2.build();

        // Then
        assertEquals(0, c.compare(a,b), "Basic comparaison between two applicants" );
    }
}