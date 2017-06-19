/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.regex.Pattern;

/**
 *
 * @author felipe
 */
public class Actor extends Json {
    private String name, sex;
    private int id, credit;
    private Json json;
    private String character;
    
    public Actor () {
        json = new Json();
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public Json getJson() {
        return json;
    }

    public void setJson(Json json) {
        this.json = json;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }
    
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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
        json.addItem("sex", sex);
        json.addItem("character", character);
        json.addItem("credit", credit);
        return json.serialize();
    }
}
