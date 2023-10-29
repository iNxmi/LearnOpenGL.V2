package com.nami.noise;

import com.nami.util.TwoKeyMap;

public abstract class NoiseGenerator {

    private TwoKeyMap<Float, Float, Float> cache = new TwoKeyMap<>();

    public float generate(float x, float y) {
        if (!cache.containsKeys(x, y))
            cache.put(x, y, onGenerate(x, y));

        return cache.get(x, y);
    }

    protected abstract float onGenerate(float x, float z);

}
