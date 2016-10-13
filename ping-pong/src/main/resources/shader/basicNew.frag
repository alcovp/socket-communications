#version 330 core

in vec2 textCoordFrag;
in vec3 norm;
in vec4 pos;
//in vec4 shadow_coord;

out vec4 fragColor;

uniform sampler2D tex;
uniform samplerCubeShadow depth_texture;
uniform vec3 ambient;
uniform vec3 lightC;
uniform vec3 lightP;
uniform vec3 lightA;
uniform vec3 eye;
uniform vec2 nearFarPlane;

void main() {
    vec3 direction = normalize(lightP - pos.xyz);
    float cosTheta = clamp(dot(norm, direction), 0, 1);
    float distance = distance(pos.xyz, lightP);
    float attenuation = 1 / (lightA.x + lightA.y * distance + lightA.z * distance * distance);
    vec3 diffuseFactor = lightC * cosTheta * attenuation;

    vec3 reflection = reflect(direction, norm);
    vec3 eyeDirection = normalize(pos.xyz - eye);
    float cosAlpha = clamp(dot(eyeDirection, reflection), 0, 1);
    vec3 specularFactor = lightC * pow(cosAlpha, 5);

    vec3 lightToFrag = pos.xyz - lightP;
    // only for samplerCube. not for shitty samplerCubeShadow
    float distanceToLight = length(lightToFrag);
    float currentDistanceToLight = (distanceToLight - nearFarPlane.x) /
                (nearFarPlane.y - nearFarPlane.x);
    currentDistanceToLight = clamp(currentDistanceToLight, 0, 1);
//    float referenceDistanceToLight	= texture(depth_texture, lightToFrag).r;
//    float f = float(referenceDistanceToLight > currentDistanceToLight);
      float f = texture(depth_texture, vec4(-lightToFrag, currentDistanceToLight));
    // end.
//    float depth = dot(lightToFrag, lightToFrag) / 10;
//    float f = texture(depth_texture, vec4(normalize(lightToFrag), depth - 0.005));

    vec3 lightFactor = clamp(diffuseFactor * f + specularFactor * f + ambient, 0, 1);
    fragColor = texture2D(tex, textCoordFrag.xy) * vec4(lightFactor, 1);
}
