package com.nami.util;

import org.joml.Vector2d;

import java.util.Random;

public class NoiseGenerator {

    private final int width, height, cellSize;
    private final double[][] grid;
    private final Vector2d[][] gradients;
    private final Random rand;

    public NoiseGenerator(long seed, int width, int height, int cellSize) {
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;

        this.grid = new double[width + 1][height + 1];
        this.gradients = new Vector2d[width / cellSize + 1][height / cellSize + 1];

        this.rand = new Random(seed);

        for (int y = 0; y < height + 1; y++)
            for (int x = 0; x < width + 1; x++)
                grid[x][y] = rand.nextDouble();

        for (int y = 0; y < height / cellSize + 1; y++)
            for (int x = 0; x < width / cellSize + 1; x++)
                gradients[x][y] = new Vector2d(rand.nextDouble(), rand.nextDouble());
    }

    public double get(double x, double y) {
        int cellX = (int) Math.floor(x / cellSize);
        int cellY = (int) Math.floor(y / cellSize);

        double cellFractionX = (x / cellSize) - cellX;
        double cellFractionY = (y / cellSize) - cellY;

        double dot1 = gradients[cellX][cellY].dot(new Vector2d(cellFractionX, cellFractionY));
        double dot2 = gradients[cellX + 1][cellY].dot(new Vector2d(cellFractionX - 1, cellFractionY));
        double dot3 = gradients[cellX][cellY + 1].dot(new Vector2d(cellFractionX, cellFractionY - 1));
        double dot4 = gradients[cellX + 1][cellY + 1].dot(new Vector2d(cellFractionX - 1, cellFractionY - 1));

        double xInterpolated1 = lerp(dot1, dot2, fade(cellFractionX));
        double xInterpolated2 = lerp(dot3, dot4, fade(cellFractionX));
        double yInterpolated = lerp(xInterpolated1, xInterpolated2, fade(cellFractionY));

        return yInterpolated;
    }

    private double lerp(double a, double b, double t) {
        return (1 - t) * a + t * b;
    }

    private double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

}
