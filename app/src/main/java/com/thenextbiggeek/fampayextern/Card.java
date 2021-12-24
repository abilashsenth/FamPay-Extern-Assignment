package com.thenextbiggeek.fampayextern;

import java.util.ArrayList;

/**
 * A plain Old Java class instance that mirrors the Card attribute from the API
 */

public class Card {
    private String name, title, description, url, bg_color;
    private CardImage icon, bg_image;
    private Gradient bg_gradient;
    private ArrayList<CTA> cta;
    private FormattedText formatted_title;
    private FormattedText formatted_description;

    public Card(String name){
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String getBg_color() {
        return bg_color;
    }

    public CardImage getBg_image() {
        return bg_image;
    }

    public CardImage getIcon() {
        return icon;
    }

    public ArrayList<CTA> getCta() {
        return cta;
    }

    public FormattedText getFormatted_description() {
        return formatted_description;
    }

    public FormattedText getFormatted_title() {
        return formatted_title;
    }

    public Gradient getBg_gradient() {
        return bg_gradient;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setBg_color(String bg_color) {
        this.bg_color = bg_color;
    }

    public void setBg_gradient(Gradient bg_gradient) {
        this.bg_gradient = bg_gradient;
    }

    public void setBg_image(CardImage bg_image) {
        this.bg_image = bg_image;
    }

    public void setCta(ArrayList<CTA> cta) {
        this.cta = cta;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFormatted_description(FormattedText formatted_description) {
        this.formatted_description = formatted_description;
    }

    public void setFormatted_title(FormattedText formatted_title) {
        this.formatted_title = formatted_title;
    }

    public void setIcon(CardImage icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
