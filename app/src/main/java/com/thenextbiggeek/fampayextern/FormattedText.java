package com.thenextbiggeek.fampayextern;

import java.util.ArrayList;

public class FormattedText {
    private String text;
    private ArrayList<Entity> entities;

    public FormattedText(String text) {
        this.text = text;
    }

    public FormattedText(String text, ArrayList<Entity> entities) {
        this.text = text;
        this.entities = entities;
    }

    public String getText() {
        return text;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }
}
