#version 330 core

layout (location = 0) in vec4 position;
layout (location = 1) in vec2 textCoord;
layout (location = 2) in vec3 normal;

out vec2 textCoordFrag;
out vec3 norm;
out vec4 pos;
out vec4 shadow_coord;

uniform mat4 mvp;
uniform mat4 model;
uniform mat4 shadow_matrix;

void main() {
    pos = model * position;
    norm = normal;
    gl_Position = mvp * vec4(position);
    textCoordFrag = textCoord;
    shadow_coord = shadow_matrix * pos;
}
