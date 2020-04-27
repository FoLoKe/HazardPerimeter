package com.foloke.haz;

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

        if (userDataA != null && userDataB != null) {
            if (userDataA instanceof Pawn) {
                Pawn pawn = (Pawn) userDataA;
                if (userDataB instanceof String) {
                    if ((userDataB).equals("collision")) {
                        pawn.getController().setInAir(false);
                    }
                } else if (userDataB instanceof Contactable) {
                    pawn.contact((Contactable) userDataB);
                }
            } else if (userDataB instanceof Pawn) {
                Pawn pawn = (Pawn) userDataB;
                if (userDataA instanceof String) {
                    if ((userDataA).equals("collision")) {
                        pawn.getController().setInAir(false);
                    }
                } else if (userDataA instanceof Contactable) {
                    pawn.contact((Contactable) userDataA);
                }
            } else if (contact.isEnabled()) {
                if (userDataA instanceof Bullet) {
                    if (userDataB instanceof Entity) {
                        ((Bullet) userDataA).hit((Entity) userDataB);
                    } else {
                        ((Bullet) userDataA).hit(null);
                    }
                    System.out.println("hit");
                } else if (userDataB instanceof Bullet) {
                    if (userDataA instanceof Entity) {
                        ((Bullet) userDataB).hit((Entity) userDataA);
                    } else {
                        ((Bullet) userDataB).hit(null);
                    }
                    System.out.println("hit");
                }
            }
        }
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
