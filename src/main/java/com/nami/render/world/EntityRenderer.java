package com.nami.render.world;

import com.nami.entity.Entity;
import com.nami.shader.world.object.ObjectShader;

public class EntityRenderer extends ModelRenderer {

    public void renderEntity(Entity entity, ObjectShader shader) {
        shader.setTransformationMatrix(entity.getTransformationMatrix());
        renderModel(entity.getModel(), shader);
    }

}
