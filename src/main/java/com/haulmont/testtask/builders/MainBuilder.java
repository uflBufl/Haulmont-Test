package com.haulmont.testtask.builders;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;

public class MainBuilder {

    public void backToMain(ComponentContainer parentLayout){
        Button button3 = new Button("Жанры");
        button3.addClickListener(e-> {GenreScreenBuilder gsb = new GenreScreenBuilder(); gsb.initGenreTable(parentLayout);});

        Button button4 = new Button("Авторы");
        button4.addClickListener(e-> {
            AuthorScreenBuilder asb = new AuthorScreenBuilder(); asb.initAuthorTable(parentLayout);});

        Button button5 = new Button("Книги");
        button5.addClickListener(e-> {
            BookScreenBuilder bsb = new BookScreenBuilder(); bsb.initBookTable(parentLayout);});

        parentLayout.removeAllComponents();
        parentLayout.addComponent(button3);
        parentLayout.addComponent(button4);
        parentLayout.addComponent(button5);
    }

}
