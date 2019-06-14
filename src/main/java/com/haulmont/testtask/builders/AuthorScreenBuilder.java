package com.haulmont.testtask.builders;

import com.haulmont.testtask.entities.Author;
import com.haulmont.testtask.scripts.AuthorScripts;
import com.vaadin.ui.*;

import java.util.ArrayList;

public class AuthorScreenBuilder {

    public void initAuthorTable(ComponentContainer parentLayout) {
        Table table = new Table();

        ArrayList<String> params = new ArrayList<String>();
        ArrayList<String> args = new ArrayList<String>();

        table.addContainerProperty("Id", Long.class, null);
        table.addContainerProperty("Имя",  String.class, null);
        table.addContainerProperty("Фамилия",  String.class, null);
        table.addContainerProperty("Отчество",  String.class, null);
        table.addContainerProperty("Удалить",        Button.class,    null);
        table.addContainerProperty("Изменить",        Button.class,    null);

        table.setPageLength(5);

        VerticalLayout vl = new VerticalLayout();

        AuthorScripts as = new AuthorScripts();
        ArrayList<Author> authors = as.selectFilterAuthors(params,args);
        int i = 1;
        for (Author author:authors) {

            Button deleteField = new Button("Удалить");
            deleteField.setData(author.getId());
            deleteField.addClickListener(new Button.ClickListener() {
                public void buttonClick(Button.ClickEvent event) {
                    Long iid = (Long)event.getButton().getData();
                    as.deleteAuthor(iid.toString());
                    initAuthorTable(parentLayout);
                }
            });
            deleteField.addStyleName("link");

            Button editField = new Button("Изменить");
            editField.setData(author.getId());
            editField.addClickListener(new Button.ClickListener() {
                public void buttonClick(Button.ClickEvent event) {
                    parentLayout.getUI().addWindow(createModalEditWindow(author, parentLayout));
                }
            });
            editField.addStyleName("link");

            table.addItem(new Object[]{author.getId(),author.getFirstName(), author.getSecondName(), author.getThirdName(), deleteField, editField}, i);
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
            initAddAuthorWindow(parentLayout);
        });

        parentLayout.removeAllComponents();

        vl.setMargin(false);
        vl.setSpacing(true);

        VerticalLayout buttons = new VerticalLayout();
        HorizontalLayout topMenu = new HorizontalLayout();

        buttons.setSpacing(true);
        topMenu.setSpacing(true);

        buttons.addComponent(addButton);

        topMenu.addComponent(buttons);

        vl.addComponent(backButton);
        vl.addComponent(topMenu);
        vl.addComponent(table);

        parentLayout.addComponent(vl);
    }


    //        МОДАЛЬНОЕ ОКНО ДЛЯ ИЗМЕНЕНИЯ!!
    public Window createModalEditWindow(Author author, ComponentContainer parentLayout){
        Window subWindow = new Window("Изменить автора");
        VerticalLayout subContent = new VerticalLayout();
        subWindow.setContent(subContent);

        TextField firstNameEditField = new TextField();
        firstNameEditField.setCaption("Вписывать имя здесь:");
        firstNameEditField.setValue(author.getFirstName());
        subContent.addComponent(firstNameEditField );

        TextField secondNameEditField = new TextField();
        secondNameEditField.setCaption("Вписывать фамилию здесь:");
        secondNameEditField.setValue(author.getSecondName());
        subContent.addComponent(secondNameEditField);

        TextField thirdNameEditField = new TextField();
        thirdNameEditField.setCaption("Вписывать отчество здесь:");
        thirdNameEditField.setValue(author.getThirdName());
        subContent.addComponent(thirdNameEditField);

        Button editButton = new Button("Изменить");
        editButton.addClickListener(e->{

            ArrayList<String> params = new ArrayList<String>();
            ArrayList<String> args = new ArrayList<String>();

            args.add("firstName");
            params.add(firstNameEditField.getValue());
            args.add("secondName");
            params.add(secondNameEditField.getValue());
            args.add("thirdName");
            params.add(thirdNameEditField.getValue());
            params.add(String.valueOf(author.getId()));

            AuthorScripts as = new AuthorScripts();
            as.updateAuthor(params,args);
            initAuthorTable(parentLayout);
            subWindow.close();
        });
        subContent.addComponent(editButton);
        subWindow.center();
        subWindow.setModal(true);

        return subWindow;
    }


    public void initAddAuthorWindow(ComponentContainer parentLayout){
        ArrayList<String> params = new ArrayList<String>();
        ArrayList<String> args = new ArrayList<String>();

        TextField firstNameField = new TextField();
        firstNameField.setCaption("Вписывать имя здесь:");

        TextField secondNameField = new TextField();
        secondNameField.setCaption("Вписывать фамилию здесь:");

        TextField thirdNameField = new TextField();
        thirdNameField.setCaption("Вписывать отчество здесь:");
        AuthorScripts as = new AuthorScripts();

        Button backButton = new Button("Назад");
        backButton.addClickListener(e->{
            AuthorScreenBuilder asb = new AuthorScreenBuilder();
            asb.initAuthorTable(parentLayout);
        });

        Button addButton = new Button("Добавить");
        addButton.addClickListener(e->{

            if(!firstNameField.equals("")){
                args.add("firstName");
                params.add(firstNameField.getValue());
            }
            if(!secondNameField.equals("")){
                args.add("secondName");
                params.add(secondNameField.getValue());
            }
            if(!thirdNameField.equals("")){
                args.add("thirdName");
                params.add(thirdNameField.getValue());
            }

            System.out.println(params.toString());
            System.out.println(args.toString());

            as.addAuthor(params, args);
            initAuthorTable(parentLayout);
        });

        parentLayout.removeAllComponents();

        VerticalLayout vl = new VerticalLayout();
        HorizontalLayout hl = new HorizontalLayout();

        vl.setSpacing(true);
        hl.setSpacing(true);

        hl.addComponent(firstNameField);
        hl.addComponent(secondNameField);
        hl.addComponent(thirdNameField);
        hl.addComponent(addButton);
        vl.addComponent(backButton);
        vl.addComponent(hl);

        parentLayout.addComponent(vl);

    }



}
