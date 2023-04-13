#version 330 core
layout (location = 0) in vec3 lPos;
layout (location = 1) in vec3 lNor;
layout (location = 2) in vec2 lTex;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 worldMatrix;

out vec3 vPos;
out vec3 vNor;
out vec2 vTex;

void main() {
    vec4 wPos = worldMatrix * vec4(lPos, 1.0);

    vPos = vec3(wPos);
    vNor = vec3(worldMatrix * vec4(lNor, 0.0));
    vTex = lTex;

    gl_Position = projectionMatrix * viewMatrix * wPos;
}