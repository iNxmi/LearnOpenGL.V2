#version 460 core
layout (location = 0) in vec3 lPos;
layout (location = 1) in vec3 lNor;
layout (location = 2) in vec2 lTex;

struct Camera {
    vec3 position;
    mat4 projectionMatrix;
    mat4 viewMatrix;
};

uniform Camera uCamera;
uniform mat4 uTransformationMatrix;

out vec3 vPos;
out vec3 vNor;
out vec2 vTex;
out vec3 camPos;

void main() {
    vec4 wPos = uTransformationMatrix * vec4(lPos, 1.0);

    vPos = vec3(wPos);
    vNor = vec3(uTransformationMatrix * vec4(lNor, 0.0));
    vTex = lTex;

    camPos = uCamera.position;

    gl_Position = uCamera.projectionMatrix * uCamera.viewMatrix * wPos;
}