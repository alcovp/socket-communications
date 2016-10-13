#version 430 core

layout (location = 0) out float color;

in vec4	worldSpacePosition;

uniform vec3 lightPs;
uniform vec2 nearFarPlanes;

void main() {
        // distance to light
       float distanceToLight = distance(lightPs, worldSpacePosition.xyz);
       // normalize distance taking into account near and far cliping planes
       // so valid distances should be in [0, 1] range
       color = (distanceToLight - nearFarPlanes.x) /
                (nearFarPlanes.y - nearFarPlanes.x) + 0.005;
       // clamp distances to [0, 1] range
       color = clamp(color, 0, 1);
}
