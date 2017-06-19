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
public class Search {
    private String title, year, genre, category, language;
    private String directors[], actors[];
    private int results_per_page, page;

    private boolean empty (String field) {
        return field == null || field == "";
    }
    private boolean empty (String []field) {
        return field == null || field.length == 0;
    }
    
    public boolean isEmpty () {
        return empty(title)
                && empty(year)
                && empty(genre)
                && empty(language)
                && empty(directors)
                && empty(actors);
    }
    
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public String[] getDirectors() {
        return directors;
    }

    public void setDirectors(String[] directors) {
        this.directors = directors;
    }

    public String[] getActors() {
        return actors;
    }

    public void setActors(String[] actors) {
        this.actors = actors;
    }
    
    public void setPage(int page) {
        this.page = page;
    }
    
    public int getPage () {
        return page;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getResults_per_page() {
        return results_per_page;
    }

    public void setResults_per_page(int results_per_page) {
        this.results_per_page = results_per_page;
    }
}
