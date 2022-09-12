#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec3 aNor;
layout (location = 2) in vec2 aTex;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 worldMatrix;

out vec3 bPos;
out vec3 bNor;
out vec2 bTex;
out vec3 fragPos;

void main() {
    bPos = aPos;
    bNor = aNor;
    bTex = aTex;
    fragPos = vec3(worldMatrix * vec4(aPos, 1.0));

    gl_Position = projectionMatrix * viewMatrix * worldMatrix * vec4(aPos, 1.0);
}