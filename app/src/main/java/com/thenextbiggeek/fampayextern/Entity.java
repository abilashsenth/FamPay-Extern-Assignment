package com.thenextbiggeek.fampayextern;

/**
 * A plain Old Java class instance that mirrors the Entity attribute from the API
 */

public class Entity {
    private String text, color, url, font_style;

    public Entity(String text){
        this.text = text;
    }
    public Entity(String text, String color){
        this.text = text;
        this.color = color;
    }
    public Entity(String text, String color, String url){
        this.text = text;
        this.color = color;
        this.url = url;
    }
    public Entity(String text, String color, String url, String font_style){
        this.text = text;
        this.color = color;
        this.url = url;
        this.font_style = font_style;
    }


    public String getUrl() {
        return url;
    }

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }

    public String getFont_style() {
        return font_style;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setFont_style(String font_style) {
        this.font_style = font_style;
    }
}
