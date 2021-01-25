package com.example.movielingo.model;

public class MovieFlashCard {

private String movieName;
private boolean isMovie;
private  int season;
private  int episode;
private String difficulty;


    @Override
    public String toString() {
        return "MovieFlashCard{" +
                "movieName='" + movieName + '\'' +
                ", isMovie=" + isMovie +
                ", season=" + season +
                ", episode=" + episode +
                ", difficulty='" + difficulty + '\'' +
                '}';
    }

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
}
