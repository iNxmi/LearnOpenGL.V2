package com.nami.graphics.render;

import java.nio.ByteBuffer;

public record RawTexture(int width, int height, ByteBuffer buffer) {
}
