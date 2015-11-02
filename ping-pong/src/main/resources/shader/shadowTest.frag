#version 330

layout (location = 0) out vec4 color;

in vec2 textCoordFrag;

uniform sampler2D sampler;

void main() {
    float z = texture(sampler, textCoordFrag).r;
    float n = 1.0;
    float f = 30.0;
    float c = (2.0 * n) / (f + n - z * (f - n));

    color.rgb = vec3(c);
}
