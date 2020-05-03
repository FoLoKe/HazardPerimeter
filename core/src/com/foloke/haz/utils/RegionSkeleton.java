package com.foloke.haz.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.Slot;
import com.esotericsoftware.spine.attachments.RegionAttachment;

public class RegionSkeleton  extends Skeleton {
    public RegionSkeleton(SkeletonData data) {
        super(data);
    }

    public void setRegionToSlot(TextureRegion textureRegion, Slot slot) {
        RegionAttachment rg = new RegionAttachment("debug");
        Bone bone = slot.getBone();
        rg.setRegion(textureRegion);
        rg.setX(bone.getX());
        rg.setY(bone.getY());
        rg.setRotation(bone.getRotation());
        rg.setScaleX(1f);
        rg.setScaleY(1f);
        rg.setHeight(rg.getRegion().getRegionHeight());
        rg.setWidth(rg.getRegion().getRegionWidth());
        rg.updateOffset();

        slot.setAttachment(rg);
    }
}
