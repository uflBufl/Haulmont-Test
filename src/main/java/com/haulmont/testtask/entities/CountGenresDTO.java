package com.haulmont.testtask.entities;

public class CountGenresDTO {
    long genreId;
    int count;

    public CountGenresDTO(long genreId, int count){
        this.genreId = genreId;
        this.count = count;
    }

    public long getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
