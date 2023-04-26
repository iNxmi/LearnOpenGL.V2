package com.nami.shader.gui;

import com.nami.shader.ShaderProgram;
import com.nami.shader.UniformManager;
import org.joml.Matrix3f;

public abstract class GUIShader extends ShaderProgram {

    private int uTransformationMatrix;

    public GUIShader(String vsPath, String fsPath) throws Exception {
        super(vsPath, fsPath);
    }

    @Override
    public void getUniformLocations(UniformManager uniformManager) {
        uTransformationMatrix = uniformManager.getLocation("uTransformationMatrix");
    }

    public void setTransformationMatrix(Matrix3f transformationMatrix) {

    }

}
