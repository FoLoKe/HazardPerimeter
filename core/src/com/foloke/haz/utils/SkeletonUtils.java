package com.foloke.haz.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;

import java.util.HashMap;
import java.util.Map;

public class SkeletonUtils {
    private static Map<String, SkeletonData> skeletonMap;
    //TODO: load all from skeletons folder
    public static void init() {
        skeletonMap = new HashMap<>();

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("animations/Test.atlas"));
        SkeletonJson json = new SkeletonJson(atlas);
        json.setScale(0.25f);

        SkeletonData data = json.readSkeletonData(Gdx.files.internal("animations/Test.json"));

        skeletonMap.put("Test", data);
    }

    public static RegionSkeleton getSkeletonInstance(String name) {
        return new RegionSkeleton(skeletonMap.get(name));
    }
}
