#version 330

// world space position of the fragment
in vec4	o_worldSpacePosition;

// distance that will be saved to framebuffer
layout (location = 0) out float	resultingColor;

// position of point light sources
uniform vec3 u_lightPos;
// distance from light to near and far cliping planes
uniform vec2 u_nearFarPlane;
// additional offset from the light
uniform float u_depthOffset;

void main(void)
{
   // distance to light
   float distanceToLight = distance(u_lightPos, o_worldSpacePosition.xyz);
   // normalize distance taking into account near and far cliping planes
   // so valid distances should be in [0, 1] range
   resultingColor = (distanceToLight - u_nearFarPlane.x) /
            (u_nearFarPlane.y - u_nearFarPlane.x) + u_depthOffset;
   // clamp distances to [0, 1] range
   resultingColor = clamp(resultingColor, 0, 1);
}
