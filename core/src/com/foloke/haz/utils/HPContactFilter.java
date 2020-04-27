package com.foloke.haz.utils;


import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.foloke.haz.entities.Bullet;
import com.foloke.haz.entities.Drop;

public class HPContactFilter implements ContactFilter {
    @Override
    public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
        Object userDataA = fixtureA.getUserData();
        Object userDataB = fixtureB.getUserData();

        if(userDataA instanceof Bullet && userDataB instanceof Drop) {
            return false;
        } else if (userDataA instanceof Drop && userDataB instanceof Bullet) {
            return false;
        }

        return true;
    }
}
