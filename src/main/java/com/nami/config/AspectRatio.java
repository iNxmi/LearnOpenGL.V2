package com.nami.config;

public record AspectRatio(int w, int h) {
    public float aspect() {
        return ((float) w / (float) h);
    }
}