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
public class Data extends Json {
    private ArrayList<Genre> genres;
    private ArrayList<Language> langs;
    private ArrayList<Actor> actors;
    private ArrayList<Director> directors;
    private final Json json;

    public Data () {
        json = new Json();
    }

    public ArrayList<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(ArrayList<Director> directors) {
        this.directors = directors;
    }
    
    public ArrayList<Actor> getActors() {
        return actors;
    }

    public void setActors(ArrayList<Actor> actors) {
        this.actors = actors;
    }
    
    public ArrayList<Language> getLangs() {
        return langs;
    }

    public void setLangs(ArrayList<Language> langs) {
        this.langs = langs;
    }
    
    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }
    
    @Override
    public String serialize() {
        json.addArray("genres", new ArrayList<>(genres));
        json.addArray("languages", new ArrayList<>(langs));
        json.addArray("actors", new ArrayList<>(actors));
        json.addArray("directors", new ArrayList<>(directors));
        return json.serialize();
    }   
}
