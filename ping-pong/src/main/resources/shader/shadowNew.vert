#version 430 core

layout (location = 0) in vec4 position;

uniform mat4 mvps;
uniform mat4 ms;

out vec4 worldSpacePosition;

void main() {
    worldSpacePosition = ms * position;
    gl_Position = mvps * position;
}
