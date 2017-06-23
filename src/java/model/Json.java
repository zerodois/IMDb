/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author felipe
 */
public class Json {
    private String json;
    public Json () {
        json = "{";
    }
    protected String validate(String str) {
        if (str == null)
            return null;
        return str.replaceAll("\"", "\\\\\"");
    }
    private void verify () {
        if (!json.equals("{"))
            json += ",";
    }
    public void addItem (String keyname, String value) {
        verify();
        json += "\"" + validate(keyname) + "\" : \"" + validate(value) + "\"";
    }
    public void addItem (String keyname, int value) {
        verify();
        json += "\"" + validate(keyname) + "\" : " + value;
    }
    public void addItem (String keyname, Json value) {
        verify();
        json += "\"" + validate(keyname) + "\" : " + value.serialize();
    }
    public void addArray (String keyname, ArrayList<Json> arr) {
        verify();
        json += "\"" + keyname + "\" : [";
        for (int i=0; i<arr.size(); i++) {
            if (i>0)
                json += ", ";
            json += arr.get(i).serialize();
        }
        json += "]";
    }
    public String serialize () {
        json += "}";
        return json;
    }
}
