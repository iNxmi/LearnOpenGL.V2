package com.nami.shader.light;

import com.nami.shader.ShaderProgram;
import com.nami.shader.UniformManager;
import org.joml.Vector3f;

public class LightShader extends ShaderProgram {

    private int uLightColor;

    public LightShader() throws Exception {
        super("./src/main/java/com/nami/shader/shader.vert", "./src/main/java/com/nami/shader/light/shader.frag");
    }

    @Override
    public void getUniformLocations(UniformManager uniformManager) throws Exception {
        uLightColor = uniformManager.getLocation("lightColor");
    }

    public void setLightColor(Vector3f lightColor) {
        getUniformManager().setUniform(uLightColor, lightColor);
    }

}
