package com.alc.rendering;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

/**
 * Created by alc on 04.10.2015.
 */
public class Utils {
    public static FloatBuffer createFlippedBuffer(Vertex[] vertices) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length * Vertex.SIZE);

        for (Vertex vertex : vertices) {
            buffer.put(vertex.getPosition().x);
            buffer.put(vertex.getPosition().y);
            buffer.put(vertex.getPosition().z);
            buffer.put(vertex.getTexCoord().x);
            buffer.put(vertex.getTexCoord().y);
            buffer.put(vertex.getNormal().x);
            buffer.put(vertex.getNormal().y);
            buffer.put(vertex.getNormal().z);
        }

        buffer.flip();

        return buffer;
    }

    public static IntBuffer createFlippedBuffer(int[] indices) {
        IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);

        buffer.put(indices);

        buffer.flip();

        return buffer;
    }

    public static FloatBuffer createFlippedBuffer(Matrix4f matrix) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);

        buffer.put(matrix.m00);
        buffer.put(matrix.m01);
        buffer.put(matrix.m02);
        buffer.put(matrix.m03);

        buffer.put(matrix.m10);
        buffer.put(matrix.m11);
        buffer.put(matrix.m12);
        buffer.put(matrix.m13);

        buffer.put(matrix.m20);
        buffer.put(matrix.m21);
        buffer.put(matrix.m22);
        buffer.put(matrix.m23);

        buffer.put(matrix.m30);
        buffer.put(matrix.m31);
        buffer.put(matrix.m32);
        buffer.put(matrix.m33);

        buffer.flip();

        return buffer;
    }

    public static String[] removeEmptyStrings(String[] data) {
        ArrayList<String> result = new ArrayList<String>();

        for (int i = 0; i < data.length; i++)
            if (!data[i].equals("")) {
                result.add(data[i]);
            }

        String[] res = new String[result.size()];
        result.toArray(res);

        return result.toArray(res);
    }

    public static int[] toPrimitive(Integer[] integerArray) {

        int[] result = new int[integerArray.length];
        for (int i = 0; i < integerArray.length; i++) {
            result[i] = integerArray[i];
        }
        return result;
    }
}
