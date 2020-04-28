package com.foloke.haz.components;

public class Costume {
    //defence stats 0-1 float
    public float biteDefence;
    public int defenceClass;
    public float explosionDefence;
    public float bioDefence;
    public float radDefence;
    public float shockDefence;
    public float thermalDamage;

    //durability
    public float durability;

    public Costume() {

    }

    public float onDamage(Damage damage) {
        float value = damage.value;
        if(durability > 0) {
            switch (damage.type) {
                case BITE:
                    durability -= 2;
                    return value - value * biteDefence;
                case PENETRATION:
                    durability -= 1;
                    if (damage.damageClass < defenceClass) {
                        return value / 8;
                    } else if (damage.damageClass == defenceClass) {
                        return value / 4;
                    } else {
                        return value;
                    }
                case EXPLOSION:
                    durability -= value / 2;
                    return value - value * explosionDefence;
                case BIO:
                    return value - value * bioDefence;
                case RAD:
                    return value - value * radDefence;
                case SHOCK:
                    return value - value * shockDefence;
                case THERMAL:
                    durability -= value;
                    return value - value * thermalDamage;

            }
        }

        return value;
    }
}
