#version 460 core

in vec2 vPos;
in vec2 vTex;

uniform sampler2D uFontAtlas;

out vec4 FragColor;

void main() {
    FragColor = texture(uFontAtlas, vTex);
}
