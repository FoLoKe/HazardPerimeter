package com.foloke.haz.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.foloke.haz.components.Costume;
import com.foloke.haz.components.Damage;
import com.foloke.haz.components.Weapon;
import com.foloke.haz.screens.GameScreen;
import com.foloke.haz.utils.SkeletonUtils;

public class Character extends Pawn {
    private Weapon weapon;
    private Costume costume;
    private Vector2 sightPoint;
    AnimationState state;

    public Character(World world) {
        super(world);
        weapon = new Weapon(GameScreen.texture, world, this);
        costume = new Costume();

        radiationCap = 1000;
        bioCap = 100;

        this.sightPoint = new Vector2();
        regionSkeleton = SkeletonUtils.getSkeletonInstance("Test");

        AnimationStateData stateData = new AnimationStateData(regionSkeleton.getData());
        state = new AnimationState(stateData);

        Animation animation = regionSkeleton.getData().findAnimation("walkAmimation");
        state.setAnimation(0, animation, true);
        //state.addAnimation(0, animation, true, 0);
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

        regionSkeleton.setPosition(body.getPosition().x * GameScreen.PPM, body.getPosition().y * GameScreen.PPM);
        state.update(Gdx.graphics.getDeltaTime());
        state.apply(regionSkeleton);
        regionSkeleton.updateWorldTransform();

        GameScreen.skeletonRenderer.draw(batch, regionSkeleton);
    }

    public void setCostume(Costume costume) {
        this.costume = costume;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public void shoot() {
        weapon.shoot(sightPoint);
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

    @Override
    public void interact(Entity entity) {

    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Costume getCostume() {
        return costume;
    }

    @Override
    public void debug(ShapeRenderer shapeRenderer) {
        super.debug(shapeRenderer);
        shapeRenderer.line(body.getPosition().scl(GameScreen.PPM), sightPoint.scl(GameScreen.PPM));
    }

    @Override
    public void setSight(Vector2 sightPoint) {
        this.sightPoint.set(sightPoint);
    }
}
