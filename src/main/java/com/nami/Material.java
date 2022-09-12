package com.nami;

import com.nami.render.Texture;

public record Material(Texture texture, Texture specular, float shininess) {
}
