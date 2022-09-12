package com.nami;

import org.joml.Matrix4f;
import org.joml.Math;
import org.joml.Vector3f;

public class Transformation {

    public static Matrix4f getProjectionMatrix(float fov, float aspect, float zNear, float zFar) {
        Matrix4f mat = new Matrix4f();
        genProjectionMatrix(mat, fov, aspect, zNear, zFar);
        return mat;
    }

    public static void genProjectionMatrix(Matrix4f mat, float fov, float aspect, float zNear, float zFar) {
        mat.identity();
        mat.perspective(fov, aspect, zNear, zFar);
    }

    public static Matrix4f getViewMatrix(Vector3f position, Vector3f rotation, Vector3f scale) {
        Matrix4f mat = new Matrix4f();
        genViewMatrix(mat, position, rotation, scale);
        return mat;
    }

    public static void genViewMatrix(Matrix4f mat, Vector3f position, Vector3f rotation, Vector3f scale) {
        mat.identity();
        mat.rotateX(rotation.x);
        mat.rotateY(rotation.y);
        mat.rotateZ(rotation.z);
        mat.translate(-position.x,-position.y,-position.z);
        mat.scale(scale);
    }

    public static Matrix4f getWorldMatrix(Vector3f position, Vector3f rotation, Vector3f scale) {
        Matrix4f mat = new Matrix4f();
        genWorldMatrix(mat, position, rotation, scale);
        return mat;
    }

    public static void genWorldMatrix(Matrix4f mat, Vector3f position, Vector3f rotation, Vector3f scale) {
        mat.identity();
        mat.translate(position);
        mat.rotateX(Math.toRadians(rotation.x));
        mat.rotateY(Math.toRadians(rotation.y));
        mat.rotateZ(Math.toRadians(rotation.z));
        mat.scale(scale);
    }

}
