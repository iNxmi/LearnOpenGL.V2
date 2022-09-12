package com.nami.config;

public record WindowConfig(int width, int height, AspectRatio aspect, int monitor, int fps, boolean vsync, boolean fullscreen) {

}
