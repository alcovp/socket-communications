#version 330 core

in vec2 textCoordFrag;
in vec3 norm;
in vec4 pos;
in vec4 shadow_coord;

out vec4 fragColor;

uniform sampler2D tex;
uniform sampler2DShadow depth_texture;
uniform vec3 ambient;
uniform vec3 lightC;
uniform vec3 lightP;
uniform vec3 lightA;
uniform vec3 eye;
uniform vec3 coloring;
uniform float shine;

float getMixFactor(in vec3 coloring_in) {
    return clamp(length(coloring_in) * 100, 0, 0.5);
}

void main() {
    vec3 direction = normalize(lightP - pos.xyz);
    float cosTheta = clamp(dot(norm, direction), 0, 1);
    float distance = distance(pos.xyz, lightP);
    float attenuation = 1 / (lightA.x + lightA.y * distance + lightA.z * distance * distance);
    vec3 diffuseFactor = lightC * cosTheta * attenuation;

    vec3 reflection = reflect(direction, norm);
    vec3 eyeDirection = normalize(pos.xyz - eye);
    float cosAlpha = clamp(dot(eyeDirection, reflection), 0, 1);
    vec3 specularFactor = lightC * pow(cosAlpha, 32);

    float f = textureProj(depth_texture, shadow_coord);
    vec3 lightFactor = clamp(diffuseFactor * f + ambient, 0, 1);

    vec4 litColor = mix(texture2D(tex, textCoordFrag.xy), vec4(coloring, 1), getMixFactor(coloring)) * vec4(lightFactor, 1);
    fragColor = clamp(litColor + vec4(specularFactor * shine * f, 1), 0, 1);
}
