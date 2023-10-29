package com.nami.graphics.shader.world.object;

import com.nami.graphics.render.Material;
import com.nami.graphics.shader.UniformManager;
import com.nami.graphics.shader.world.WorldShader;
import com.nami.light.SpotLight;
import com.nami.light.DirectionalLight;
import com.nami.light.PointLight;

public class ObjectShader extends WorldShader {

    private int uMaterialColor, uMaterialTexture, uMaterialSpecularExponent;
    private int uDirectionalLightDirection, uDirectionalLightColor;
    private int uPointLightPosition, uPointLightColor, uPointLightAttenuation;
    private int uSpotLightPosition, uSpotLightAttenuation, uSpotLightColor, uSpotLightAngle;

    public ObjectShader() throws Exception {
        super("./src/main/java/com/nami/graphics/shader/world/object/shader.frag");

        uMaterialColor = uniformManager().getLocation("uMaterial.color");
        uMaterialTexture = uniformManager().getLocation("uMaterial.text");
        uMaterialSpecularExponent = uniformManager().getLocation("uMaterial.specularExponent");

        uDirectionalLightDirection = uniformManager().getLocation("uDirectionalLight.direction");
        uDirectionalLightColor = uniformManager().getLocation("uDirectionalLight.color");

        uPointLightPosition = uniformManager().getLocation("uPointLight.position");
        uPointLightColor = uniformManager().getLocation("uPointLight.color");
        uPointLightAttenuation = uniformManager().getLocation("uPointLight.attenuation");

        uSpotLightPosition = uniformManager().getLocation("uSpotLight.position");
        uSpotLightColor = uniformManager().getLocation("uSpotLight.color");
        uSpotLightAttenuation = uniformManager().getLocation("uSpotLight.attenuation");
        uSpotLightAngle = uniformManager().getLocation("uSpotLight.angle");
    }

    public void setMaterial(Material material) {
        uniformManager().setUniform(uMaterialColor, material.getColor());
        material.getTexture().bind(0);
        uniformManager().setUniform(uMaterialTexture, 0);
        uniformManager().setUniform(uMaterialSpecularExponent, material.getSpecularExponent());
    }

    public void setDirectionalLight(DirectionalLight light) {
        uniformManager().setUniform(uDirectionalLightDirection, light.getDirection());
        uniformManager().setUniform(uDirectionalLightColor, light.getColor());
    }

    public void setPointLight(PointLight light) {
        uniformManager().setUniform(uPointLightPosition, light.getPosition());
        uniformManager().setUniform(uPointLightColor, light.getColor());
        uniformManager().setUniform(uPointLightAttenuation, light.getAttenuation());
    }

    public void setSpotLight(SpotLight light) {
        uniformManager().setUniform(uSpotLightPosition, light.getPosition());
        uniformManager().setUniform(uSpotLightAttenuation, light.getAttenuation());
        uniformManager().setUniform(uSpotLightColor, light.getColor());
        uniformManager().setUniform(uSpotLightAngle, light.getAngle());
    }

}
