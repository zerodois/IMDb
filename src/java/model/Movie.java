/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author felipe
 */
public class Movie extends Json {
    private int id;
    private String title, year;
    private final Json json;

    public Movie () {
        json = new Json();
    }
    
    public int getId() {
        return id;
    }

    public String getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    @Override
    public String serialize() {
        json.addItem("id", id);
        json.addItem("title", title);
        json.addItem("year", year);
        return json.serialize();
    }
}
