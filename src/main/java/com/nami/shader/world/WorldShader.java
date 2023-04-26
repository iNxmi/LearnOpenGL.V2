package com.nami.shader.world;

import com.nami.camera.Camera;
import com.nami.shader.ShaderProgram;
import org.joml.Matrix4f;

public abstract class WorldShader extends ShaderProgram {

    private int uCameraProjectionMatrix, uCameraViewMatrix, uCameraPosition;
    private int uTransformationMatrix;

    public WorldShader(String fsPath) throws Exception {
        super("./src/main/java/com/nami/shader/world/shader.vert", fsPath);

        uCameraProjectionMatrix = getUniformManager().getLocation("uCamera.projectionMatrix");
        uCameraViewMatrix = getUniformManager().getLocation("uCamera.viewMatrix");
        uCameraPosition = getUniformManager().getLocation("uCamera.position");
        uTransformationMatrix = getUniformManager().getLocation("uTransformationMatrix");
    }

    public void setCamera(Camera camera) {
        getUniformManager().setUniform(uCameraProjectionMatrix, camera.getProjectionMatrix());
        getUniformManager().setUniform(uCameraViewMatrix, camera.getViewMatrix());
        getUniformManager().setUniform(uCameraPosition, camera.getPosition());
    }

    public void setTransformationMatrix(Matrix4f transformationMatrix) {
        getUniformManager().setUniform(uTransformationMatrix, transformationMatrix);
    }

}
