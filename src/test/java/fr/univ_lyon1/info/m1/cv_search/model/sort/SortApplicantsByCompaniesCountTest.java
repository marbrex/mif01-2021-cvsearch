package fr.univ_lyon1.info.m1.cv_search.model.sort;

import fr.univ_lyon1.info.m1.cv_search.model.applicant.Applicant;
import fr.univ_lyon1.info.m1.cv_search.model.applicant.ApplicantBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Executable;

import static org.junit.jupiter.api.Assertions.*;

class SortApplicantsByCompaniesCountTest {
    SortApplicantsByCompaniesCount c;

    @BeforeEach
    void setUp(){
        c = new SortApplicantsByCompaniesCount();
    }
    @Test
    @DisplayName("Simple comparaison should work")
    void testCompare() {
        // Given
        ApplicantBuilder builder1 = new ApplicantBuilder("applicant1.yaml");
        ApplicantBuilder builder2 = new ApplicantBuilder("applicant8.yaml");

        // When
        Applicant a = builder1.build();
        Applicant b = builder2.build();

        // Then
        assertEquals(0, c.compare(a,b), "Basic comparaison between two applicants" );
    }

    @Test
    @DisplayName("Ensure correct handling of one null applicant")
    void testNullCompare(){
        // Given
        ApplicantBuilder builder1 = new ApplicantBuilder("applicant1.yaml");

        // When
        Applicant a = builder1.build();
        Applicant b = null;

        // Then
        assertEquals(-666, c.compare(a,b), "Nullptr managed");
    }
}