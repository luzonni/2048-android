package com.coffee.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.coffee.Engine;

public class Touch {

    private float xTouch, yTouch;

    private boolean pressing;
    private boolean clicked;
    private boolean sliding;

    private float deltaX, deltaY;

    public void tick() {
        Input input = Gdx.input;
        if(input.justTouched()) {
            clicked = true;
            sliding = true;
            xTouch = input.getX();
            yTouch = input.getY();
        }
        if(input.isTouched()) {
            deltaX = input.getDeltaX();
            deltaY = input.getDeltaY();
        }
        this.pressing = input.isTouched();
    }

    public boolean isPressing(Rectangle rec) {
        Vector3 touchPos = new Vector3(xTouch, yTouch, 0);
        Engine.getCam().unproject(touchPos);
        return pressing && rec.contains(touchPos.x, touchPos.y);
    }

    public boolean isPressed(Rectangle rec) {
        Vector3 touchPos = new Vector3(xTouch, yTouch, 0);
        Engine.getCam().unproject(touchPos);
        if(clicked && rec.contains(touchPos.x, touchPos.y)) {
            clicked = false;
            return true;
        }
        return false;
    }

    public TypeSlide getSlide() {
        if (sliding && (Math.abs(deltaX) > 10 || Math.abs(deltaY) > 10)) {
            sliding = false;
            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                if (deltaX > 0) {
                    return TypeSlide.Right;
                } else {
                    return TypeSlide.Left;
                }
            } else {
                if (deltaY > 0) {
                    return TypeSlide.Up;
                } else {
                    return TypeSlide.Down;
                }
            }
        }
        return TypeSlide.Idle;
    }

}
