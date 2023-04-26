package com.nami.gui.text;

import java.util.Map;

public record FontMeta(String name, float size, boolean bold, boolean italic, float width, float height,
                       Map<Character, FontChar> characters) {
}
