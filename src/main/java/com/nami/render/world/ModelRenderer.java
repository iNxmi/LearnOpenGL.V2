package com.nami.render.world;

import com.nami.shader.world.object.ObjectShader;

public class ModelRenderer extends MeshRenderer {

    public void renderModel(Model model, ObjectShader shader) {
        shader.setMaterial(model.material());
        renderMesh(model.mesh());
    }

}
