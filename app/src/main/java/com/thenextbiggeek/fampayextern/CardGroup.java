package com.thenextbiggeek.fampayextern;

import java.util.ArrayList;

public class CardGroup {

/*
    public enum DesignTypes {
        SMALL_DISPLAY_CARD("HC1"),
        BIG_DISPLAY_CARD("HC3"),
        IMAGE_CARD("HC5"),
        SMALL_CARD_WITH_ARROW("HC6"),
        DYNAMIC_WIDTH_CARD("HC9")
    }
*/

    private String design_type, name;
    private ArrayList<Card> cards;
    private int height; //used only for hc9
    private boolean is_scrollable;

    public CardGroup(String name, String design_type, ArrayList<Card> cards, boolean is_scrollable) {
        this.name = name;
        this.design_type = design_type;
        this.cards = cards;
        this.is_scrollable = is_scrollable;
    }

    public CardGroup(String name, String design_type, int height, ArrayList<Card> cards, boolean is_scrollable) {
        this.name = name;
        this.design_type = design_type;
        this.height = height;
        this.cards = cards;
        this.is_scrollable = is_scrollable;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }


    public ArrayList<Card> getCards() {
        return cards;
    }

    public String getDesign_type() {
        return design_type;
    }

    public boolean isIs_scrollable() {
        return is_scrollable;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIs_scrollable(boolean is_scrollable) {
        this.is_scrollable = is_scrollable;
    }


    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void setDesign_type(String design_type) {
        this.design_type = design_type;
    }
}




