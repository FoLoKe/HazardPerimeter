package com.foloke.haz.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.foloke.haz.entities.Bullet;
import com.foloke.haz.entities.Pawn;
import com.foloke.haz.screens.GameScreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Weapon {
    public World world;
    public TextureRegion textureRegion;
    public Pawn holder;
    public float damage;
    private List<Bullet> bullets;

    public final int maxMagazineSize;
    public final int maxReloadTime;
    public final int fireRate;

    public boolean reloading = false;

    public int reloadingTime = 0;
    public int magazine = 0;
    public int toLoad = 0;
    public int nextFire;


    public Weapon(TextureRegion textureRegion, World world, Pawn holder) {
        this.world = world;
        this.textureRegion = textureRegion;
        this.holder = holder;
        this.bullets = new ArrayList<>();

        maxMagazineSize = 8;
        maxReloadTime = 45 * 2;
        fireRate = 15;

        magazine = maxMagazineSize;
    }

    public void render(SpriteBatch batch, float delta) {
        for(Bullet bullet : bullets) {
            bullet.render(batch, delta);
        }

        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            if (!bullet.active) {
                bulletIterator.remove();
                bullet.destroy();
            }
        }

        if(reloading) {
            if(reloadingTime == 0) {
                magazine = maxMagazineSize;
                reloading = false;
            }
            reloadingTime--;
        }

        if(nextFire > 0) {
            nextFire--;
        }
    }

    public void shoot() {
        if(magazine > 0) {
            if (nextFire <= 0) {
                Bullet bullet = new Bullet(textureRegion, world, this);
                bullets.add(bullet);
                bullet.shoot(holder.getPosition(), new Vector2(holder.getDirection() ? -1 : 1, 0));
                nextFire = fireRate;
                magazine--;
            }
        } else {
            if(!reloading) {
                reload(100);
            }
        }
    }

    public void reload(int bullets) {
        if(bullets > 0) {
            reloading = true;
            toLoad = Integer.min(bullets, maxMagazineSize);
            reloadingTime = maxReloadTime;
            GameScreen.rsound.play();
        }
    }
}
