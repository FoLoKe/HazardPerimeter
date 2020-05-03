package com.foloke.haz.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.esotericsoftware.spine.*;
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
    Animation walkAnimation;
    Animation idleAnimation;
    Animation currentAnimation;
    Animation aimAnimation;
    IkConstraint rightHandIkConstraint;
    IkConstraint leftHandIkConstraint;

    public Character(World world) {
        super(0.4f, 0.4f, world);
        weapon = new Weapon(GameScreen.texture, world, this);
        costume = new Costume();

        radiationCap = 1000;
        bioCap = 100;

        this.sightPoint = new Vector2();
        regionSkeleton = SkeletonUtils.getSkeletonInstance("Test");

        AnimationStateData stateData = new AnimationStateData(regionSkeleton.getData());
        state = new AnimationState(stateData);

        walkAnimation = regionSkeleton.getData().findAnimation("walk");
        idleAnimation = regionSkeleton.getData().findAnimation("idle");
        //aimAnimation = regionSkeleton.getData().findAnimation("aiming");


        IkConstraintData data = regionSkeleton.getData().findIkConstraint("bone_ik");
        rightHandIkConstraint = new IkConstraint(data, regionSkeleton);

        IkConstraintData data1 = regionSkeleton.getData().findIkConstraint("bone_ik1");
        leftHandIkConstraint = new IkConstraint(data1, regionSkeleton);
        currentAnimation = idleAnimation;
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

        Animation animation;
        if(Math.abs(body.getLinearVelocity().x) > 0.2f) {
            animation = walkAnimation;
        } else {
            animation = idleAnimation;
        }

        if(animation != currentAnimation) {
            AnimationState.TrackEntry trackEntry = state.setAnimation(0, animation, true);
            trackEntry.setMixDuration(0.5f);
            currentAnimation = animation;
        }




        regionSkeleton.setFlipX(direction);
        regionSkeleton.setPosition(body.getPosition().x * GameScreen.PPM, body.getPosition().y * GameScreen.PPM);
        state.update(Gdx.graphics.getDeltaTime());
        state.apply(regionSkeleton);

        regionSkeleton.updateWorldTransform();

        Bone boneRT = rightHandIkConstraint.getTarget();
        boneRT.setWorldX(sightPoint.x * GameScreen.PPM);
        boneRT.setWorldY(sightPoint.y * GameScreen.PPM);
        rightHandIkConstraint.apply();

        Bone boneLT = leftHandIkConstraint.getTarget();
        boneLT.setWorldX((sightPoint.x + 0.2f * (direction ? -1 : 1)) * GameScreen.PPM);
        boneLT.setWorldY(sightPoint.y * GameScreen.PPM);
        leftHandIkConstraint.apply();

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
