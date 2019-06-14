package com.haulmont.testtask.builders;

import com.haulmont.testtask.entities.Book;
import com.haulmont.testtask.scripts.BookScripts;
import com.vaadin.ui.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Date;
import java.sql.*;
import java.util.List;
import java.util.Set;

public class BookScreenBuilder {
    private static String name = "";
    private static String author = "";
    private static String publisher = "";

    public void initBookTable(ComponentContainer parentLayout) {
        Table table = new Table();

        ArrayList<String> params = new ArrayList<String>();
        ArrayList<String> args = new ArrayList<String>();

        table.addContainerProperty("Id", Long.class, null);
        table.addContainerProperty("Название",  String.class, null);
        table.addContainerProperty("Id автора",  Long.class, null);
        table.addContainerProperty("Id жанра",  Long.class, null);
        table.addContainerProperty("Издание",  String.class, null);
        table.addContainerProperty("Год",  Date.class, null);
        table.addContainerProperty("Город",  String.class, null);
        table.addContainerProperty("Удалить",        Button.class,    null);
        table.addContainerProperty("Изменить",        Button.class,    null);

        table.setPageLength(5);

        VerticalLayout vl = new VerticalLayout();

        TextField nameField = new TextField();
        nameField.setCaption("Вписывать название здесь:");
        nameField.setValue(name);

        TextField authorField = new TextField();
        authorField.setCaption("Вписывать id автора здесь:");
        authorField.setValue(author);

        List<String> publishers = new ArrayList<>();
        publishers.add(new String("Москва"));
        publishers.add(new String("Питер"));
        publishers.add(new String("O’Reilly"));

        NativeSelect ls = new NativeSelect("Выбрать издание здесь:");
        ls.addItems(publishers);

        ls.setValue(publisher);

        Button filterButton = new Button("Фильтровать");
        filterButton.addClickListener(e->{
//            id = idField.getValue();
            name = nameField.getValue();
            author = authorField.getValue();
//            genre = genreField.getValue();
            if(!ls.isEmpty()) {
                publisher = (String) ls.getValue();
            }
            else{
                publisher = "";
            }
            initBookTable(parentLayout);
        });

        if(!name.equals("")){
            args.add("name");
            params.add("%"+nameField.getValue()+"%");
        }
        if(!author.equals("")){
            args.add("author");
            params.add(authorField.getValue());
        }
        if(!(ls.isEmpty())){
            args.add("publisher");
            params.add((String)ls.getValue());
        }

        BookScripts bs = new BookScripts();
        ArrayList<Book> books = bs.selectFilterBooks(params,args);
        int i = 1;
        for (Book book:books) {

            Button deleteField = new Button("Удалить");
            deleteField.setData(book.getId());
            deleteField.addClickListener(new Button.ClickListener() {
                public void buttonClick(Button.ClickEvent event) {
                    Long iid = (Long)event.getButton().getData();
                    bs.deleteBook(iid.toString());
                    initBookTable(parentLayout);
                }
            });
            deleteField.addStyleName("link");

            Button editField = new Button("Изменить");
            editField.setData(book.getId());
            editField.addClickListener(new Button.ClickListener() {
                public void buttonClick(Button.ClickEvent event) {
                    parentLayout.getUI().addWindow(createModalEditWindow(book, parentLayout));
                }
            });
            editField.addStyleName("link");

            table.addItem(new Object[]{book.getId(),book.getName(), book.getAuthor(), book.getGenre(), book.getPublisher(), book.getYear(), book.getCity(), deleteField, editField}, i);
            i++;
        }

        table.setPageLength(table.size());

        Button backButton = new Button("Назад");
        backButton.addClickListener(e->{
            name = "";
            author = "";
            publisher = "";
            MainBuilder mb = new MainBuilder();
            mb.backToMain(parentLayout);
        });

        Button addButton = new Button("Добавить");
        addButton.addClickListener(e->{
            name = "";
            author = "";
            publisher = "";
            initAddBookWindow(parentLayout);
        });

        parentLayout.removeAllComponents();

        vl.setMargin(false);
        vl.setSpacing(true);

        VerticalLayout filters = new VerticalLayout();
        VerticalLayout buttons = new VerticalLayout();
        HorizontalLayout topMenu = new HorizontalLayout();

        buttons.setSpacing(true);
        topMenu.setSpacing(true);

        filters.addComponent(nameField);
        filters.addComponent(authorField);
        filters.addComponent(ls);

        buttons.addComponent(filterButton);
        buttons.addComponent(addButton);

        topMenu.addComponent(filters);
        topMenu.addComponent(buttons);

        vl.addComponent(backButton);
        vl.addComponent(topMenu);
        vl.addComponent(table);

        parentLayout.addComponent(vl);
    }


    //        МОДАЛЬНОЕ ОКНО ДЛЯ ИЗМЕНЕНИЯ!!
    public Window createModalEditWindow(Book book, ComponentContainer parentLayout){
        Window subWindow = new Window("Изменить книгу");
        VerticalLayout subContent = new VerticalLayout();
        subWindow.setContent(subContent);

        List<String> publishers = new ArrayList<>();
        publishers.add(new String("Москва"));
        publishers.add(new String("Питер"));
        publishers.add(new String("O’Reilly"));

        TextField nameEditField = new TextField();
        nameEditField.setCaption("Вписывать название здесь:");
        nameEditField.setValue(book.getName());
        subContent.addComponent(nameEditField);

        TextField authorEditField = new TextField();
        authorEditField.setCaption("Вписывать id автора здесь:");
        authorEditField.setValue(String.valueOf(book.getAuthor()));
        subContent.addComponent(authorEditField);

        TextField genreEditField = new TextField();
        genreEditField.setCaption("Вписывать id жанра здесь:");
        genreEditField.setValue(String.valueOf(book.getGenre()));
        subContent.addComponent(genreEditField);

        NativeSelect publisherEditField = new NativeSelect("Выбрать издателя здесь:");
        publisherEditField.addItems(publishers);
        publisherEditField.setValue(book.getPublisher());
        subContent.addComponent(publisherEditField);

        DateField yearEditField = new DateField();
        yearEditField.setCaption("Вписывать год здесь:");
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            yearEditField.setValue(dateFormat.parse(book.getYear().toString()));
        }
        catch (Exception e){
            System.out.println(e);
        }
        subContent.addComponent(yearEditField);



        TextField cityEditField = new TextField();
        cityEditField.setCaption("Вписывать город здесь:");
        cityEditField.setValue(book.getCity());
        subContent.addComponent(cityEditField);

        Button editButton = new Button("Изменить");
        editButton.addClickListener(e->{
            ArrayList<String> params = new ArrayList<String>();
            ArrayList<String> args = new ArrayList<String>();

            args.add("name");
            params.add(nameEditField.getValue());
            args.add("author");
            params.add(authorEditField.getValue());
            args.add("genre");
            params.add(genreEditField.getValue());
            args.add("publisher");
            params.add((String)publisherEditField.getValue());
            args.add("year");

            if(!yearEditField.getValue().toString().equals("")){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                params.add(dateFormat.format(yearEditField.getValue()));
            }
            else{
                params.add("");
            }

            args.add("city");
            params.add(cityEditField.getValue());
            params.add(String.valueOf(book.getId()));

            BookScripts bs = new BookScripts();
            bs.updateBook(params,args);
            initBookTable(parentLayout);
            subWindow.close();
        });
        subContent.addComponent(editButton);
        subWindow.center();
        subWindow.setModal(true);

        return subWindow;
    }

    public void initAddBookWindow(ComponentContainer parentLayout){
        ArrayList<String> params = new ArrayList<String>();
        ArrayList<String> args = new ArrayList<String>();

        TextField nameField = new TextField();
        nameField.setCaption("Вписывать название здесь:");

        TextField authorField = new TextField();
        authorField.setCaption("Вписывать id автора здесь:");

        TextField genreField = new TextField();
        genreField.setCaption("Вписывать id жанра здесь:");

        List<String> publishers = new ArrayList<>();
        publishers.add(new String("Москва"));
        publishers.add(new String("Питер"));
        publishers.add(new String("O’Reilly"));

        NativeSelect publisherField = new NativeSelect("Выбрать издание здесь:");
        publisherField.addItems(publishers);

        DateField yearField = new DateField();
        yearField.setCaption("Вписывать год здесь:");

        TextField cityField = new TextField();
        cityField.setCaption("Вписывать город здесь:");
        BookScripts bs = new BookScripts();

        Button backButton = new Button("Назад");
        backButton.addClickListener(e->{
            name = "";
            author = "";
            publisher = "";
            BookScreenBuilder bsb = new BookScreenBuilder();
            bsb.initBookTable(parentLayout);
        });

        Button addButton = new Button("Добавить");
        addButton.addClickListener(e->{

            if(!nameField.equals("")){
                args.add("name");
                params.add(nameField.getValue());
            }
            if(!authorField.equals("")){
                args.add("author");
                params.add(authorField.getValue());
            }
            if(!genreField.equals("")){
                args.add("genre");
                params.add(genreField.getValue());
            }
            if(!publisherField.isEmpty()){
                args.add("publisher");
                params.add((String)publisherField.getValue());
            }
            if(!yearField.getValue().toString().equals("")){
                args.add("year");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String str = dateFormat.format(yearField.getValue());
                params.add(str);
            }
            if(!cityField.equals("")){
                args.add("city");
                params.add(cityField.getValue());
            }

            System.out.println(params.toString());
            System.out.println(args.toString());

            bs.addBook(params, args);
            initBookTable(parentLayout);
        });

        parentLayout.removeAllComponents();

        VerticalLayout vl = new VerticalLayout();
        HorizontalLayout hl = new HorizontalLayout();

        vl.setSpacing(true);
        hl.setSpacing(true);

        hl.addComponent(nameField);
        hl.addComponent(authorField);
        hl.addComponent(genreField);
        hl.addComponent(publisherField);
        hl.addComponent(yearField);
        hl.addComponent(cityField);
        hl.addComponent(addButton);
        vl.addComponent(backButton);
        vl.addComponent(hl);

        parentLayout.addComponent(vl);
    }


}
