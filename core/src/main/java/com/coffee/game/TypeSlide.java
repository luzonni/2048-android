package com.coffee.game;

public enum TypeSlide {

    Up(0, -1), Right(1, 0), Down(0, 1), Left(-1, 0);

    private int nx, ny;

    TypeSlide(int nx, int ny) {
        this.nx = nx;
        this.ny = ny;
    }

    public int getNx() {
        return nx;
    }

    public int getNy() {
        return ny;
    }
}
