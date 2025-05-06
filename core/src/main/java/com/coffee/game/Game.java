package com.coffee.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.coffee.Activity;
import com.coffee.Engine;
import com.coffee.game.ui.UI;

import java.util.ArrayList;
import java.util.List;

public class Game implements Activity {

    private final UI ui;
    private final Grid grid;
    private final List<Integer[]> gridMemory;

    public Game(int width, int height) {
        Engine.setSCALE(15 - width);
        this.gridMemory = new ArrayList<>();
        this.ui = new UI();
        this.grid = new Grid(width, height);
    }

    public static Game getGame() {
        Activity activity = Engine.getActivity();
        if(activity instanceof Game) {
            return (Game)activity;
        }
        throw new RuntimeException("Not in game");
    }

    public static Grid getGrid() {
        return getGame().grid;
    }

    public void restore() {
        if(gridMemory.isEmpty())
            return;
        Integer[] values = gridMemory.get(gridMemory.size()-1);
        gridMemory.remove(gridMemory.size()-1);
        grid.setGrid(values);
    }

    public void stackGrid() {
        Integer[] values = new Integer[grid.getGrid().length];
        for(int i = 0; i < values.length; i++) {
            Slot slot = grid.getGrid()[i];
            if(!slot.isEmpty()) {
                values[i] = slot.content().getValue();
            }else {
                values[i] = 0;
            }
        }
        this.gridMemory.add(values);
    }

    @Override
    public void tick() {
        grid.tick();
        ui.tick();
    }

    @Override
    public void render() {
        grid.render();
        ui.render();
    }

    @Override
    public void dispose() {
        grid.dispose();
        ui.dispose();
    }

}
