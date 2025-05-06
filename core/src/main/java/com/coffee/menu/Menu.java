package com.coffee.menu;

import com.badlogic.gdx.graphics.Color;
import com.coffee.Activity;
import com.coffee.Engine;
import com.coffee.game.Game;
import com.coffee.shared.shared.Button;

import java.util.HashMap;
import java.util.Map;

public class Menu implements Activity {

    private String[] names = {"3x3", "4x4", "5x5", "6x6", "4x6"};
    private final Map<String, Button> buttons;

    public Menu() {
        Engine.setSCALE(10);
        float x = Engine.getWidth()/2f;
        float y = Engine.getHeight()/2f;
        this.buttons = new HashMap<>();
        for(int i = 0; i < names.length; i++) {
            String name = names[i];
            buttons.put(name, new Button(name, x, y + 200*i));
            Button button = buttons.get(name);
            button.setColor(new Color(0xbbac9fff));
            float yy = y + button.getBounds().getHeight()/2 - (button.getBounds().height+20)*i + (button.getBounds().height*name.length())/2;
            button.setPosition(x - button.getBounds().getWidth()/2, yy);
        }
    }

    @Override
    public void tick() {
        if(buttons.get(names[0]).clicked()) {
            Engine.setActivity(new Game(3, 3));
        }
        if(buttons.get(names[1]).clicked()) {
            Engine.setActivity(new Game(4, 4));
        }
        if(buttons.get(names[2]).clicked()) {
            Engine.setActivity(new Game(5, 5));
        }
        if(buttons.get(names[3]).clicked()) {
            Engine.setActivity(new Game(6, 6));
        }
        if(buttons.get(names[4]).clicked()) {
            Engine.setActivity(new Game(4, 6));
        }
    }

    @Override
    public void render() {
        for(String name : names) {
            buttons.get(name).render();
        }
    }

    @Override
    public void dispose() {
        for(String name : names) {
            buttons.get(name).dispose();
        }
    }
}
