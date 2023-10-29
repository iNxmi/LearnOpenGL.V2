package com.nami.noise;

import personthecat.fastnoise.FastNoise;
import personthecat.fastnoise.data.FractalType;
import personthecat.fastnoise.data.NoiseType;

public class SimplexNoiseGenerator extends NoiseGenerator {

    private final FastNoise gen;
    private final float stretch;

    public SimplexNoiseGenerator(int seed, float stretch, float lacunarity, float persistance, int octaves) {
        this.gen = FastNoise.createDescriptor().
                noise(NoiseType.SIMPLEX).
                fractal(FractalType.NONE).
                range(0, 1).

                seed(seed).
                lacunarity(lacunarity).
                octaves(octaves).

                generate();

        this.stretch = stretch;
    }

    @Override
    protected float onGenerate(float x, float y) {
        return gen.getNoiseScaled(x / stretch, y / stretch);
    }

}
