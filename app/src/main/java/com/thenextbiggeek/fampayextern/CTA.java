package com.thenextbiggeek.fampayextern;

/**
 * A plain Old Java class instance that mirrors the CTA attribute from the API
 */

public class CTA {
    private String text, bg_color, url, text_color;

    public CTA(String text) {
        this.text = text;
    }

    public CTA(String text, String bg_color) {
        this.text = text;
        this.bg_color = bg_color;
    }

    public CTA(String text, String bg_color, String url) {
        this.text = text;
        this.bg_color = bg_color;
        this.url = url;
    }

    public CTA(String text, String bg_color, String url, String text_color) {
        this.text = text;
        this.bg_color = bg_color;
        this.url = url;
        this.text_color = text_color;
    }

    public String getBg_color() {
        return bg_color;
    }

    public String getText() {
        return text;
    }

    public String getText_color() {
        return text_color;
    }

    public String getUrl() {
        return url;
    }

    public void setBg_color(String bg_color) {
        this.bg_color = bg_color;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setText_color(String text_color) {
        this.text_color = text_color;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
