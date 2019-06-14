package com.haulmont.testtask.builders;

import com.haulmont.testtask.entities.CountGenresDTO;
import com.haulmont.testtask.entities.Genre;
import com.haulmont.testtask.scripts.GenreScripts;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

import java.util.ArrayList;

public class GenreScreenBuilder {

    public void initGenreTable(ComponentContainer parentLayout) {
        Table table = new Table();

        ArrayList<String> params = new ArrayList<String>();
        ArrayList<String> args = new ArrayList<String>();

        VerticalLayout vl = new VerticalLayout();

        table.addContainerProperty("Id", Long.class, null);
        table.addContainerProperty("Название",  String.class, null);
        table.addContainerProperty("Удалить",        Button.class,    null);
        table.addContainerProperty("Изменить",        Button.class,    null);

        table.setPageLength(5);

        GenreScripts gs = new GenreScripts();
        ArrayList<Genre> genres = gs.selectFilterGenres(params,args);
        int i = 1;
        for (Genre genre:genres) {

            Button deleteField = new Button("Удалить");
            deleteField.setData(genre.getId());
            deleteField.addClickListener(new Button.ClickListener() {
                public void buttonClick(Button.ClickEvent event) {
                    Long iid = (Long)event.getButton().getData();
                    System.out.println(iid);
                    gs.deleteGenre(iid.toString());
                    initGenreTable(parentLayout);
                }
            });
            deleteField.addStyleName("link");

            Button editField = new Button("Изменить");
            editField.setData(genre.getId());
            editField.addClickListener(new Button.ClickListener() {
                public void buttonClick(Button.ClickEvent event) {
                    Long iid = (Long)event.getButton().getData();
                    System.out.println(iid);
                    parentLayout.getUI().addWindow(createModalEditWindow(genre, parentLayout));
                }
            });
            editField.addStyleName("link");

            table.addItem(new Object[]{genre.getId(),genre.getName(),deleteField, editField}, i);
            i++;
        }
        table.setPageLength(table.size());

        Button backButton = new Button("Назад");
        backButton.addClickListener(e->{
            MainBuilder mb = new MainBuilder();
            mb.backToMain(parentLayout);
        });

        Button addButton = new Button("Добавить");
        addButton.addClickListener(e->{
            initAddGenreWindow(parentLayout);
        });

        Button showButton = new Button("Показать статистику");
        showButton.addClickListener(e->{
            initShowStat(parentLayout);
        });

        parentLayout.removeAllComponents();

        vl.setMargin(false);
        vl.setSpacing(true);

        VerticalLayout buttons = new VerticalLayout();
        HorizontalLayout topMenu = new HorizontalLayout();

        buttons.setSpacing(true);
        topMenu.setSpacing(true);

        buttons.addComponent(showButton);
        buttons.addComponent(addButton);

        topMenu.addComponent(buttons);

        vl.addComponent(backButton);
        vl.addComponent(topMenu);
        vl.addComponent(table);

        parentLayout.addComponent(vl);
    }


//        МОДАЛЬНОЕ ОКНО ДЛЯ ИЗМЕНЕНИЯ!!
    public Window createModalEditWindow(Genre genre, ComponentContainer parentLayout){
        Window subWindow = new Window("Изменить Жанр");
        VerticalLayout subContent = new VerticalLayout();
        subWindow.setContent(subContent);

        TextField nameEditField = new TextField();
        nameEditField.setCaption("Вписывать название здесь:");
        nameEditField.setValue(genre.getName());
        subContent.addComponent(nameEditField);

        Button editButton = new Button("Изменить");
        editButton.addClickListener(e->{
            ArrayList<String> params = new ArrayList<String>();
            ArrayList<String> args = new ArrayList<String>();

            args.add("name");
            params.add(nameEditField.getValue());
            params.add(String.valueOf(genre.getId()));

            GenreScripts gs = new GenreScripts();
            gs.updateGenre(params,args);
            initGenreTable(parentLayout);
            subWindow.close();
        });
        subContent.addComponent(editButton);
        subWindow.center();
        subWindow.setModal(true);

        return subWindow;
    }

    public void initAddGenreWindow(ComponentContainer parentLayout){
        ArrayList<String> params = new ArrayList<String>();
        ArrayList<String> args = new ArrayList<String>();

        TextField nameField = new TextField();
        nameField.setCaption("Вписывать название здесь:");

        GenreScripts gs = new GenreScripts();

        Button backButton = new Button("Назад");
        backButton.addClickListener(e->{
            GenreScreenBuilder mb = new GenreScreenBuilder();
            mb.initGenreTable(parentLayout);
        });

        Button addButton = new Button("Добавить");
        addButton.addClickListener(e->{

            if(!nameField.equals("")){
                args.add("name");
                params.add(nameField.getValue());
            }

            System.out.println(params.toString());
            System.out.println(args.toString());

            gs.addGenre(params, args);
            initGenreTable(parentLayout);
        });

        parentLayout.removeAllComponents();

        VerticalLayout vl = new VerticalLayout();
        HorizontalLayout hl = new HorizontalLayout();

        vl.setSpacing(true);
        hl.setSpacing(true);

        hl.addComponent(nameField);
        hl.addComponent(addButton);
        vl.addComponent(backButton);
        vl.addComponent(hl);

        parentLayout.addComponent(vl);
    }

    public void initShowStat(ComponentContainer parentLayout){
        GenreScripts gs = new GenreScripts();

        ArrayList<Genre> genres = gs.selectFilterGenres(new ArrayList<String>(),new ArrayList<String>());
        ArrayList<CountGenresDTO> counts = gs.showCountGenres();

        Table table = new Table();

        table.addContainerProperty("Id", Long.class, null);
        table.addContainerProperty("Название",  String.class, null);
        table.addContainerProperty("Количество Книг",        Integer.class,    null);

        int i = 0;
        for (Genre genre: genres) {
            boolean isExist = false;
            for (CountGenresDTO dto: counts) {
                if(dto.getGenreId() == genre.getId()){
                    table.addItem(new Object[]{genre.getId(),genre.getName(),dto.getCount()}, i);
                    isExist = !isExist;
                }
            }
            if(!isExist){
                table.addItem(new Object[]{genre.getId(),genre.getName(), 0}, i);
            }
            i++;
        }

        Button backButton = new Button("Назад");
        backButton.addClickListener(e->{
            initGenreTable(parentLayout);
        });

        table.setPageLength(table.size());

        parentLayout.removeAllComponents();
        parentLayout.addComponent(backButton);
        parentLayout.addComponent(table);
    }
}
