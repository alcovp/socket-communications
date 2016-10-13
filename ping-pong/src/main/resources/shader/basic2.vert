#version 330

// attributes
layout(location = 0) in vec3	i_position;	// xyz - position
layout(location = 2) in vec3	i_normal;	// xyz - normal

// matrices
uniform mat4 u_modelMat;
uniform mat4 u_modelViewProjMat;

// position of the vertex in world space to the fragement shader
out vec4 o_worldPosition;

void main(void)
{
   // world space position of the vertex
   o_worldPosition	= u_modelMat * vec4(i_position, 1);

   // screen space position of the vertex
   gl_Position	= u_modelViewProjMat * vec4(i_position, 1);
}