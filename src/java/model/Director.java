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
public class Director extends Json {
    private String name;
    private int id;
    private final Json json;
    
    public Director () {
        json = new Json();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String serialize() {
        json.addItem("id", id);
        json.addItem("name", name.replace("\"", "\\\""));
        return json.serialize();
    }
}
