#version 330 core
in vec3 vPos;
in vec3 vNor;
in vec2 vTex;

uniform vec3 lightPos;
uniform vec3 lightColor;
uniform vec3 camPos;

uniform vec3 entityColor;

out vec4 FragColor;

void main() {
    float ambient = 0.1;

    vec3 norm = normalize(vNor);
    vec3 lightDir = normalize(lightPos - vPos);
    float diffuse = max(dot(norm, lightDir), 0.0);

    float specularStrength = 0.5;
    vec3 camDir = normalize(camPos - vPos);
    vec3 reflectDir = reflect(-lightDir, norm);
    float specular = pow(max(dot(camDir, reflectDir), 0.0), 64.0) * specularStrength;

    vec3 result = (ambient + diffuse + specular) * lightColor * entityColor;
    FragColor = vec4(result, 1.0);
}