#version 330

layout (location = 0) in vec4 position;
layout (location = 1) in vec2 textCoord;

out vec2 textCoordFrag;

uniform mat4 mvpt;

void main() {
    textCoordFrag = textCoord;
    gl_Position = mvpt * vec4(position);
}
