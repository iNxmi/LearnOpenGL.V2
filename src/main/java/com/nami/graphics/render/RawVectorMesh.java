package com.nami.graphics.render;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;

public record RawVectorMesh(Vector3f[] vertices, Vector3f[] normals, Vector2f[] textureCoords, Vector3i[] indices) {

}
