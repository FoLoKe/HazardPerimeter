package com.foloke.haz.utils;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.foloke.haz.entities.*;

public class HPContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Object userDataA = contact.getFixtureA().getUserData();
        Object userDataB = contact.getFixtureB().getUserData();

        if(!collision(userDataA, userDataB)) {
            collision(userDataB, userDataA);
        }
    }

    private boolean collision(Object dataA, Object dataB) {
        if (dataA != null && dataB!= null) {
            if (dataA instanceof Entity) {

                //Pawn contacts
                if (dataA instanceof Pawn) {
                    Pawn pawn = (Pawn) dataA;
                    if (dataB instanceof String) {
                        if ((dataB).equals("collision")) {
                            pawn.getController().setInAir(false);
                            return true;
                        }
                    }

                    if (dataB instanceof Contactable) {
                        pawn.contact((Contactable) dataB);
                        return true;
                    }

                }

                //Bullet contacts
                if (dataA instanceof Bullet) {
                    if (dataB instanceof Entity) {
                        ((Bullet) dataA).hit((Entity) dataB);
                    } else {
                        ((Bullet) dataA).hit(null);
                    }

                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public void endContact(Contact contact) {
        Object userDataA = contact.getFixtureA().getUserData();
        Object userDataB = contact.getFixtureB().getUserData();

        if (userDataA instanceof Pawn) {
            Pawn pawn = (Pawn) userDataA;
            if (userDataB instanceof Contactable) {
                pawn.endContact((Contactable) userDataB);
            }
        } else if (userDataB instanceof Pawn) {
            Pawn pawn = (Pawn) userDataB;
            if (userDataA instanceof Contactable) {
                pawn.endContact((Contactable) userDataA);
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Object userDataA = contact.getFixtureA().getUserData();
        Object userDataB = contact.getFixtureB().getUserData();

        if(userDataA != null && userDataB != null) {
            if(userDataA instanceof Drop && userDataB instanceof DynamicEntity) {
                contact.setEnabled(false);
            } else if (userDataB instanceof Drop && userDataA instanceof DynamicEntity) {
                contact.setEnabled(false);
            } else if (userDataA instanceof Pawn && userDataB instanceof Pawn) {
                contact.setEnabled(false);
            }
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
