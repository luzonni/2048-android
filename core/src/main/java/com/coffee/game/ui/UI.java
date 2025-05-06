package com.coffee.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.coffee.Activity;
import com.coffee.Engine;
import com.coffee.game.Game;
import com.coffee.menu.Menu;
import com.coffee.shared.shared.Button;

public class UI implements Activity {

    private final Button backMenu;
    private final Button restoreButton;

    public UI() {
        float x = Engine.getWidth();
        float y = Engine.getHeight() - Engine.SIZE();
        this.restoreButton = new Button("Back", x, y);
        this.restoreButton.setColor(new Color(0xbcaba0ff));
        this.restoreButton.setPosition(x - this.restoreButton.getBounds().width, y - this.restoreButton.getBounds().height);
        this.backMenu = new Button("Menu", x, y);
        this.backMenu.setColor(new Color(0xbcaba0ff));
        this.backMenu.setPosition(0, y - this.restoreButton.getBounds().height);
    }

    @Override
    public void tick() {
        if(this.restoreButton.clicked()) {
            Game.getGame().restore();
        }
        if(this.backMenu.clicked()) {
            Game.getGrid().dispose();
            Engine.setActivity(new Menu());
            return;
        }
        Rectangle bounds = Game.getGrid().getBounds();
    }

    @Override
    public void render() {
        restoreButton.render();
        backMenu.render();
    }

    @Override
    public void dispose() {
        restoreButton.dispose();
        backMenu.dispose();
    }
}
