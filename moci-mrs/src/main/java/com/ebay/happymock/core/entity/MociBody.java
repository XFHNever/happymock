package com.ebay.happymock.core.entity;

/**
 * User: jicui
 * Date: 14-8-15
 */
public final class MociBody {
    private String xml;
    private String json;
    private String text;

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "MociBody{" +
                "text='" + text + '\'' +
                ", json='" + json + '\'' +
                ", xml='" + xml + '\'' +
                '}';
    }
}
