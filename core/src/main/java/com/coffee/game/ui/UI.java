package com.coffee.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.coffee.Activity;
import com.coffee.Engine;
import com.coffee.game.Game;
import com.coffee.game.ui.shared.Button;

public class UI implements Activity {

    private final Button restoreButton;

    public UI() {
        float xRB = Engine.getWidth();
        float yRB = Engine.getHeight();
        this.restoreButton = new Button("Back", xRB, yRB);
        this.restoreButton.setColor(new Color(0xbcaba0ff));
    }

    @Override
    public void tick() {
        if(this.restoreButton.clicked()) {
            Game.getGame().restore();
        }
        Rectangle bounds = Game.getGrid().getBounds();
        this.restoreButton.setPosition(bounds.x + bounds.width - restoreButton.getBounds().width, bounds.y + bounds.getHeight() + Game.SIZE()/2f);
    }

    @Override
    public void render() {
        restoreButton.render();
    }

    @Override
    public void dispose() {
        restoreButton.dispose();
    }
}
