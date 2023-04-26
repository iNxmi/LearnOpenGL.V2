#version 460 core
layout (location = 0) in vec2 lPos;
layout (location = 1) in vec2 lTex;

out vec2 vPos;
out vec2 vTex;

uniform mat3 uTransformationMatrix;

void main() {
    vPos = lPos;
    vTex = lTex;

    gl_Position = vec4(lPos, 0, 1);
}