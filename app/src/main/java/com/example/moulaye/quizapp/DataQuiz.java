package com.example.moulaye.quizapp;

public class DataQuiz {

    private int id;
    private String pays;
    private String capitale;
    private String monnaie;


    public DataQuiz(int id, String pays, String capitale, String monnaie) {
        this.id=id;
        this.pays = pays;
        this.capitale = capitale;
        this.monnaie = monnaie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getCapitale() {
        return capitale;
    }

    public void setCapitale(String capitale) {
        this.capitale = capitale;
    }

    public String getMonnaie() {
        return monnaie;
    }

    public void setMonnaie(String monnaie) {
        this.monnaie = monnaie;
    }


    public String toString(){
        return "ID :"+id+ " le pays est : "+pays+" sa capitale est : "+capitale+" et sa monnaie est : "+monnaie;
    }


}
