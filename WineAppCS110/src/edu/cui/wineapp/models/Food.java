package edu.cui.wineapp.models;

import java.io.Serializable;

/**
 * Created by Chris on 5/16/13.
 */
public class Food implements Serializable{

    private String name;
    private String link;
    private String source_link;
    private int source_id;
    private String image;

    public Food(String name, String link, String source_link, int source_id, String image) {
        this.name = name;
        this.link = link;
        this.source_link = source_link;
        this.source_id = source_id;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSource_link() {
        return source_link;
    }

    public void setSource_link(String source_link) {
        this.source_link = source_link;
    }

    public int getSource_id() {
        return source_id;
    }

    public void setSource_id(int source_id) {
        this.source_id = source_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
