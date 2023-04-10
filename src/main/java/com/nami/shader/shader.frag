#version 330 core
in vec3 bPos;
in vec3 bNor;
in vec2 bTex;
in vec3 fragPos;

struct Material {
    sampler2D textureSampler;
    sampler2D specularMap;
    float shininess;
};

uniform Material material;

out vec4 FragColor;

void main() {
    FragColor = mix(texture(material.textureSampler, bTex), texture(material.specularMap, bTex), 0.99999) * material.shininess;
}