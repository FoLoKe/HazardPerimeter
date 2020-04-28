package com.foloke.haz.components;

public class Damage {
    //DamageTypes
    public enum Type {BITE, PENETRATION, EXPLOSION, BIO, RAD, SHOCK, THERMAL}

    public float value;
    public Type type;

    //Damage class mainly for ammo 0 - 7 (no armor - 4+ (6a))
    public int damageClass;

    public Damage(Type type, float value) {
        this.type = type;
        this.value = value;
        damageClass = 1;
    }
}
