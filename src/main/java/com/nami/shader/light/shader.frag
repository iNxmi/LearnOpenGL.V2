#version 330 core
in vec3 bPos;
in vec3 bNor;
in vec2 bTex;
in vec3 fragPos;

uniform vec3 lightColor;

out vec4 FragColor;

void main() {
    FragColor = vec4(lightColor, 1.0);
}