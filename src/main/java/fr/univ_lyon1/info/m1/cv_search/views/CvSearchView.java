package fr.univ_lyon1.info.m1.cv_search.views;

import javafx.stage.Stage;

public class CvSearchView extends FxmlView {

    public CvSearchView() {
        super();
    }

    public CvSearchView(Stage stage) {
        super(stage);
    }

    public CvSearchView(double width, double height,
                        double minWidth, double minHeight, String title) {
        super(width, height, minWidth, minHeight, title);
    }

}
