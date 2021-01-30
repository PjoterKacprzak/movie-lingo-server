package com.example.movielingo.model;

public class MovieFlashCard {

private String movieName;
private boolean isMovie;
private  int season;
private  int episode;
private String difficulty;
private  String sourceLanguage;
private  String targetLanguage;



    public String getMovieName() {
        return movieName;
    }

    public boolean isMovie() {
        return isMovie;
    }

    public int getSeason() {
        return season;
    }

    public int getEpisode() {
        return episode;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    @Override
    public String toString() {
        return "MovieFlashCard{" +
                "movieName='" + movieName + '\'' +
                ", isMovie=" + isMovie +
                ", season=" + season +
                ", episode=" + episode +
                ", difficulty='" + difficulty + '\'' +
                ", sourceLanguage='" + sourceLanguage + '\'' +
                ", targetLanguage='" + targetLanguage + '\'' +
                '}';
    }
}
