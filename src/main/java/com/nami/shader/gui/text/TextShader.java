package com.nami.shader.gui.text;

import com.nami.shader.ShaderProgram;
import com.nami.shader.UniformManager;
import com.nami.render.Texture;

public class TextShader extends ShaderProgram {

    private int uFontAtlas;

    public TextShader() throws Exception {
        super("./src/main/java/com/nami/shader/text/shader.vert", "./src/main/java/com/nami/shader/text/shader.frag");
    }

    @Override
    public void getUniformLocations(UniformManager uniformManager) {
        uFontAtlas = uniformManager.getLocation("uFontAtlas");
    }

    public void setFontAtlas(Texture texture) {
        texture.bind(0);
        getUniformManager().setUniform(uFontAtlas, 0);
    }

}
