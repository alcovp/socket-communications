package com.alc.rendering;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

/**
 * Created by alc on 04.10.2015.
 */
public class Shader {
    private int program;
    private int shadowProgram;
    private int shadowTestProgram;
    private Map<String, Integer> uniforms;

    public Shader() {

        uniforms = new HashMap<>();

        program = glCreateProgram();
        addVertexShader(loadShader("basic.vert"), program);
        addFragmentShader(loadShader("basic.frag"), program);
        compileShader(program);

        addUniform("mvp", program);
        addUniform("model", program);
        addUniform("ambient", program);
        addUniform("lightP", program);
        addUniform("lightC", program);
        addUniform("lightA", program);
        addUniform("eye", program);
        addUniform("tex", program);
        addUniform("depth_texture", program);
        addUniform("shadow_matrix", program);

        shadowProgram = glCreateProgram();
        addVertexShader(loadShader("shadow.vert"), shadowProgram);
        addFragmentShader(loadShader("shadow.frag"), shadowProgram);
        compileShader(shadowProgram);

        addUniform("mvps", shadowProgram);

        shadowTestProgram = glCreateProgram();
        addVertexShader(loadShader("shadowTest.vert"), shadowTestProgram);
        addFragmentShader(loadShader("shadowTest.frag"), shadowTestProgram);
        compileShader(shadowTestProgram);

        addUniform("mvpt", shadowTestProgram);

    }

    private void AddProgram(String text, int type, int program) {
        int shader = glCreateShader(type);

        if (shader == 0) {
            throw new IllegalStateException("Shader creation failed: Could not find valid memory location when adding shader");
        }

        glShaderSource(shader, text);
        glCompileShader(shader);

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
            throw new IllegalStateException(glGetShaderInfoLog(shader, 1024));
        }

        glAttachShader(program, shader);
    }

    private void addVertexShader(String text, int program) {
        AddProgram(text, GL_VERTEX_SHADER, program);
    }

    private void addGeometryShader(String text, int program) {
        AddProgram(text, GL_GEOMETRY_SHADER, program);
    }

    private void addFragmentShader(String text, int program) {
        AddProgram(text, GL_FRAGMENT_SHADER, program);
    }

    private void compileShader(int program) {
        glLinkProgram(program);

        if (glGetProgrami(program, GL_LINK_STATUS) == 0) {
            throw new IllegalStateException(glGetProgramInfoLog(program, 1024));
        }

        glValidateProgram(program);

        if (glGetProgrami(program, GL_VALIDATE_STATUS) == 0) {
            throw new IllegalStateException(glGetProgramInfoLog(program, 1024));
        }
    }

    private String loadShader(String fileName) {
        StringBuilder shaderSource = new StringBuilder();
        final String INCLUDE_DIRECTIVE = "#include";

        try {
            BufferedReader shaderReader = new BufferedReader(new FileReader("src/main/resources/shader/" + fileName));
            String line;

            while ((line = shaderReader.readLine()) != null) {
                if (line.startsWith(INCLUDE_DIRECTIVE)) {
                    shaderSource.append(loadShader(line.substring(INCLUDE_DIRECTIVE.length() + 2, line.length() - 1)));
                } else
                    shaderSource.append(line).append("\n");
            }

            shaderReader.close();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return shaderSource.toString();
    }

    public void addUniform(String uniform, int program) {
        int uniformLocation = glGetUniformLocation(program, uniform);

        if (uniformLocation == -1) {
            throw new IllegalStateException("Could not find uniform " + uniform);
        }

        uniforms.put(uniform, uniformLocation);
    }

    public void setUniform(String name, float value) {
        glUniform1f(uniforms.get(name), value);
    }

    public void setUniform(String name, int value) {
        glUniform1i(uniforms.get(name), value);
    }

    public void setUniform(String name, Vector3f value) {
        glUniform3f(uniforms.get(name), value.x, value.y, value.z);
    }

    public void setUniform(String name, Vector4f value) {
        glUniform4f(uniforms.get(name), value.x, value.y, value.z, value.w);
    }

    public void setUniform(String name, Matrix4f value) {
        glUniformMatrix4fv(uniforms.get(name), false, Utils.createFlippedBuffer(value));
    }

    public void use() {
        glUseProgram(program);
    }

    public void useShadow() {
        glUseProgram(shadowProgram);
    }

    public void useShadowTest() {
        glUseProgram(shadowTestProgram);
    }

    public void dispose() {
        glDeleteBuffers(program);
        glDeleteBuffers(shadowProgram);
    }
}

