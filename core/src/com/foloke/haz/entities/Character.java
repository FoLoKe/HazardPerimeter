package com.foloke.haz.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.foloke.haz.components.Costume;
import com.foloke.haz.components.Damage;
import com.foloke.haz.components.Weapon;

public class Character extends Pawn {
    private Weapon weapon;
    private Costume costume;

    public Character(TextureRegion textureRegion, World world) {
        super(textureRegion, world);
        weapon = new Weapon(textureRegion, world, this);

        radiationCap = 1000;
        bioCap = 100;
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        super.render(batch, delta);

        weapon.render(batch, delta);

        hp -= radiation / 1000;

        if(bio == 100) {
            hp -= 0.01f;
        }

        if(hp <= 0) {
            destroyed = true;
        }
    }

    public void setCostume(Costume costume) {
        this.costume = costume;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public void shoot() {
        weapon.shoot();
    }

    @Override
    public void applyDamage(Damage damage) {
        float value = costume.onDamage(damage);

        switch (damage.type) {
            case RAD:
                radiation = Float.min(radiation + value, radiationCap);
                break;
            case BIO:
                bio = Float.min(bio + value, bio);
                break;
            default:
                hp -= damage.value;
                break;
        }
    }
}
