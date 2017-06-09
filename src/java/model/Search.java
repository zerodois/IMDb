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
    private String title, year, genre;
    private int results_per_page, page;

    public Search(String title, String year, String genre, int results_per_page, int page) {
        this.title = title;
        this.year = year;
        this.page = page;
        this.genre = genre;
        this.results_per_page = results_per_page;
    }
    public Search(String title, int results_per_page, int page) {
        this.title = title;
        this.page = page;
        this.year = null;
        this.genre = null;
        this.results_per_page = results_per_page;
    }
    
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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
