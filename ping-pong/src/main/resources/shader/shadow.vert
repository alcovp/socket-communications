#version 430 core

layout (location = 0) in vec4 position;

uniform mat4 mvps;

void main() {
    gl_Position = mvps * vec4(position);
}
