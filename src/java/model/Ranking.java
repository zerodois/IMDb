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
public class Ranking extends Json {
    private Actor actor;
    private Director director;
    private int amount;
    private final Json json;
    
    public Ranking () {
        this.json = new Json();
    }
    
    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    @Override
    public String serialize() {
        json.addItem("actor", actor);
        json.addItem("director", director);
        json.addItem("amount", amount);
        return json.serialize();
    }
}
