package fr.univ_lyon1.info.m1.cv_search.model.applicant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ApplicantList implements Iterable<Applicant> {
    private List<Applicant> list = new ArrayList<>();

    void add(Applicant a) {
        list.add(a);
    }

    public int size() {
        return list.size();
    }

    @Override
    public Iterator<Applicant> iterator() {
        return list.iterator();
    }

    /**
     * Clear the list of applicants.
     */
    public void clear() {
        list.clear();
    }

    /**
     * Sets the content of the applicant list.
     */
    public void setList(ApplicantList list) {
        this.list = list.list;
    }

    /**
     * Returns the content of the applicant list.
     */
    public List<Applicant> getList() {
        return list;
    }
}
