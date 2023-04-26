package com.nami.shader.world.object;

import com.nami.light.SpotLight;
import com.nami.render.world.Material;
import com.nami.light.DirectionalLight;
import com.nami.light.PointLight;
import com.nami.shader.world.WorldShader;
import com.nami.shader.UniformManager;

public class ObjectShader extends WorldShader {

    private int uMaterialColor, uMaterialTexture, uMaterialSpecularExponent;
    private int uDirectionalLightDirection, uDirectionalLightColor;
    private int uPointLightPosition, uPointLightColor, uPointLightAttenuation;
    private int uSpotLightPosition, uSpotLightAttenuation, uSpotLightColor, uSpotLightAngle;

    public ObjectShader() throws Exception {
        super("./src/main/java/com/nami/shader/world/object/shader.frag");
    }

    @Override
    public void getUniformLocations(UniformManager uniformManager) {
        uMaterialColor = uniformManager.getLocation("uMaterial.color");
        uMaterialTexture = uniformManager.getLocation("uMaterial.text");
        uMaterialSpecularExponent = uniformManager.getLocation("uMaterial.specularExponent");

        uDirectionalLightDirection = uniformManager.getLocation("uDirectionalLight.direction");
        uDirectionalLightColor = uniformManager.getLocation("uDirectionalLight.color");

        uPointLightPosition = uniformManager.getLocation("uPointLight.position");
        uPointLightColor = uniformManager.getLocation("uPointLight.color");
        uPointLightAttenuation = uniformManager.getLocation("uPointLight.attenuation");

        uSpotLightPosition = uniformManager.getLocation("uSpotLight.position");
        uSpotLightColor = uniformManager.getLocation("uSpotLight.color");
        uSpotLightAttenuation = uniformManager.getLocation("uSpotLight.attenuation");
        uSpotLightAngle = uniformManager.getLocation("uSpotLight.angle");
    }

    public void setMaterial(Material material) {
        getUniformManager().setUniform(uMaterialColor, material.getColor());
        material.getTexture().bind(0);
        getUniformManager().setUniform(uMaterialTexture, 0);
        getUniformManager().setUniform(uMaterialSpecularExponent, material.getSpecularExponent());
    }

    public void setDirectionalLight(DirectionalLight light) {
        getUniformManager().setUniform(uDirectionalLightDirection, light.getDirection());
        getUniformManager().setUniform(uDirectionalLightColor, light.getColor());
    }

    public void setPointLight(PointLight light) {
        getUniformManager().setUniform(uPointLightPosition, light.getPosition());
        getUniformManager().setUniform(uPointLightColor, light.getColor());
        getUniformManager().setUniform(uPointLightAttenuation, light.getAttenuation());
    }

    public void setSpotLight(SpotLight light) {
        getUniformManager().setUniform(uSpotLightPosition, light.getPosition());
        getUniformManager().setUniform(uSpotLightAttenuation, light.getAttenuation());
        getUniformManager().setUniform(uSpotLightColor, light.getColor());
        getUniformManager().setUniform(uSpotLightAngle, light.getAngle());
    }

}
