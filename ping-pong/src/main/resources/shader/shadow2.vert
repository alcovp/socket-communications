#version 330

// attributes
layout(location = 0) in vec3	i_position;	// xyz - position

// matrices
uniform mat4 u_modelMat;
uniform mat4 u_modelViewProjMat;

// world space position of the vertex to the fragment shader
out vec4	o_worldSpacePosition;

void main(void)
{
   // to world space position
   o_worldSpacePosition = u_modelMat * vec4(i_position, 1);

   // to screen space position
   gl_Position	= u_modelViewProjMat * vec4(i_position, 1);
}