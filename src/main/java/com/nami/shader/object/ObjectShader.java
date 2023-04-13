package com.nami.shader.object;

import com.nami.shader.ShaderProgram;
import com.nami.shader.UniformManager;
import org.joml.Vector3f;

public class ObjectShader extends ShaderProgram {

    private int uLightPos, uLightColor, uCamPos, uEntityColor;

    public ObjectShader() throws Exception {
        super("./src/main/java/com/nami/shader/shader.vert", "./src/main/java/com/nami/shader/object/shader.frag");
    }

    @Override
    public void getUniformLocations(UniformManager uniformManager) throws Exception {
        uLightPos = uniformManager.getLocation("lightPos");
        uLightColor = uniformManager.getLocation("lightColor");
        uCamPos = uniformManager.getLocation("camPos");
        uEntityColor = uniformManager.getLocation("entityColor");
    }

    public void setLightPos(Vector3f lightPos) {
        getUniformManager().setUniform(uLightPos, lightPos);
    }

    public void setLightColor(Vector3f lightColor) {
        getUniformManager().setUniform(uLightColor, lightColor);
    }

    public void setCamPos(Vector3f camPos) {
        getUniformManager().setUniform(uCamPos, camPos);
    }

    public void setEntityColor(Vector3f entityColor) {
        getUniformManager().setUniform(uEntityColor, entityColor);
    }
}
