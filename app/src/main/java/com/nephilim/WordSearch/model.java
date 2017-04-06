package com.nephilim.WordSearch;

import java.util.ArrayList;

/**
 * Created by hp on 05-Mar-17.
 */

public class model {
    String id;

    String definitions;
    String examples;
   ArrayList <String> synonym;
   ArrayList <String> antonym;

    public ArrayList<String> getSynonym() {
        return synonym;
    }

    public void setSynonym(ArrayList<String> synonym) {
        this.synonym = synonym;
    }

    public ArrayList<String> getAntonym() {
        return antonym;
    }

    public void setAntonym(ArrayList<String> antonym) {
        this.antonym = antonym;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDefinitions() {
        return definitions;
    }

    public void setDefinitions(String definitions) {
        this.definitions = definitions;
    }

    public String getExamples() {
        return examples;
    }

    public void setExamples(String examples) {
        this.examples = examples;
    }
}
