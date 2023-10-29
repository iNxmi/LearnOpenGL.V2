package com.nami.graphics.shader.world;

import com.nami.camera.Camera;
import com.nami.graphics.shader.Shader;
import org.joml.Matrix4f;

public abstract class WorldShader extends Shader {

    private final int uCameraProjectionMatrix, uCameraViewMatrix, uCameraPosition;
    private final int uTransformationMatrix;

    public WorldShader(String fsPath) throws Exception {
        super("src/main/java/com/nami/graphics/shader/world/shader.vert", fsPath);

        uCameraProjectionMatrix = uniformManager().getLocation("uCamera.projectionMatrix");
        uCameraViewMatrix = uniformManager().getLocation("uCamera.viewMatrix");
        uCameraPosition = uniformManager().getLocation("uCamera.position");
        uTransformationMatrix = uniformManager().getLocation("uTransformationMatrix");
    }

    public void setCamera(Camera camera) {
        uniformManager().setUniform(uCameraProjectionMatrix, camera.getProjectionMatrix());
        uniformManager().setUniform(uCameraViewMatrix, camera.getViewMatrix());
        uniformManager().setUniform(uCameraPosition, camera.position);
    }

    public void setTransformationMatrix(Matrix4f transformationMatrix) {
        uniformManager().setUniform(uTransformationMatrix, transformationMatrix);
    }

}
