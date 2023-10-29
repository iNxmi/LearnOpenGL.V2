#version 460 core
in vec3 vPos;
in vec3 vNor;
in vec2 vTex;
in vec3 camPos;

struct Material {
    vec3 color;
    sampler2D text;
    float specularExponent;
};

struct DirectionalLight {
    vec3 direction;
    vec3 color;
};

struct PointLight {
    vec3 position;
    vec3 color;
    vec3 attenuation;
};

struct SpotLight {
    vec3 position;
    vec3 direction;
    vec3 attenuation;
    vec3 color;
    float angle;
};

uniform Material uMaterial;

//#define MAX_DIRECTIONAL_LIGHTS 8
uniform DirectionalLight uDirectionalLight;

//#define MAX_POINT_LIGHTS 8
//uniform PointLight uPointLights[MAX_POINT_LIGHTS];
//
//#define MAX_SPOT_LIGHTS 8
//uniform SpotLight uSpotLights[MAX_SPOT_LIGHTS];

out vec4 FragColor;

void main() {
    vec3 surfaceNormal = normalize(vNor);
    vec3 lightDirection = normalize(-uDirectionalLight.direction);

    //Diffuse
    float diffuseIntensity = max(dot(surfaceNormal, lightDirection), 0.0);

    //Specular
    vec3 viewDirection = normalize(camPos - vPos);
    vec3 reflectionDirection = reflect(-lightDirection, surfaceNormal);
    float specularIntensity = pow(max(dot(viewDirection, reflectionDirection), 0.0), uMaterial.specularExponent);

    vec3 color = (0.1 + diffuseIntensity + specularIntensity) * uDirectionalLight.color * uMaterial.color;
    FragColor = vec4(color, 1.0) * texture(uMaterial.text, vTex);
}