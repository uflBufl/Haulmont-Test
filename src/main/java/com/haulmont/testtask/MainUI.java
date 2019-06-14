package com.haulmont.testtask;

import com.haulmont.testtask.builders.AuthorScreenBuilder;
import com.haulmont.testtask.builders.BookScreenBuilder;
import com.haulmont.testtask.builders.GenreScreenBuilder;
import com.haulmont.testtask.entities.Genre;
import com.haulmont.testtask.scripts.GenreScripts;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import sun.net.www.content.text.Generic;

import java.util.ArrayList;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
//        layout.setSizeFull();
        layout.setMargin(true);

        ConnectToDB ctdb = new ConnectToDB();
        String test = ctdb.Connect();
        layout.addComponent(new Label(test));

        Button button3 = new Button("Жанры");
        button3.addClickListener(e-> {GenreScreenBuilder gsb = new GenreScreenBuilder(); gsb.initGenreTable(layout);});

        Button button4 = new Button("Авторы");
        button4.addClickListener(e-> {
            AuthorScreenBuilder asb = new AuthorScreenBuilder(); asb.initAuthorTable(layout);});

        Button button5 = new Button("Книги");
        button5.addClickListener(e-> {
            BookScreenBuilder bsb = new BookScreenBuilder(); bsb.initBookTable(layout);});

        layout.removeAllComponents();
        layout.addComponent(button3);
        layout.addComponent(button4);
        layout.addComponent(button5);

        layout.setMargin(true);
        layout.setSpacing(true);

        setContent(layout);
    }
}