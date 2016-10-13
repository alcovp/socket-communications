#version 330

// shadow map
uniform samplerCube	u_shadowCubeMap;

// world space position of the fragment
in vec4	o_worldPosition;

// color to the framebuffer
layout(location = 0) out vec4 resultingColor;

// position of the point light source
uniform vec3	u_lightPos;
// distances to near and far cliping planes
uniform vec2	u_nearFarPlane;

void main(void)
{
   // difference between position of the light source and position of the fragment
   vec3 fromLightToFragment = u_lightPos - o_worldPosition.xyz;
   // normalized distance to the point light source
   float distanceToLight = length(fromLightToFragment);
   float currentDistanceToLight = (distanceToLight - u_nearFarPlane.x) /
            (u_nearFarPlane.y - u_nearFarPlane.x);
   currentDistanceToLight = clamp(currentDistanceToLight, 0, 1);
   // normalized direction from light source for sampling
   fromLightToFragment = normalize(fromLightToFragment);

   // sample shadow cube map
   float referenceDistanceToLight	= texture(u_shadowCubeMap, -fromLightToFragment).r;
   // compare distances to determine whether the fragment is in shadow
   float shadowFactor = float(referenceDistanceToLight > currentDistanceToLight);

   // output color
   resultingColor.rgb = vec3(shadowFactor);
   resultingColor.a = 1;
}