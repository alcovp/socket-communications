package com.alc.rendering;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alc on 04.10.2015.
 */
class ResourceLoader {
    public static Model loadModel(String modelId) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/model/" + modelId));
            String line;

            List<Vertex> vertices = new ArrayList<>();
            List<Vector3f> positions = new ArrayList<>();
            List<Vector2f> texCoords = new ArrayList<>();
            List<Vector3f> normals = new ArrayList<>();;
            List<Integer> indices = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                tokens = Utils.removeEmptyStrings(tokens);

                switch (tokens[0]) {
                    case "v":
                        positions.add(new Vector3f(Float.valueOf(tokens[1]),
                                Float.valueOf(tokens[2]),
                                Float.valueOf(tokens[3])));
                        break;
                    case "vt":
                        texCoords.add(new Vector2f(Float.valueOf(tokens[1]),
                                1.0f - Float.valueOf(tokens[2])));
                        break;
                    case "vn":
                        normals.add(new Vector3f(Float.valueOf(tokens[1]),
                                Float.valueOf(tokens[2]),
                                Float.valueOf(tokens[3])));
                        break;
                    case "f":
                        for (int i = 1; i < tokens.length; i++) {
                            String[] vertexIndices = tokens[i].split("/");
                            Vertex vertex = new Vertex(
                                    positions.get(Integer.valueOf(vertexIndices[0]) - 1),
                                    texCoords.get(Integer.valueOf(vertexIndices[1]) - 1),
                                    normals.get(Integer.valueOf(vertexIndices[2]) - 1)
                            );

                            if (!vertices.contains(vertex)) {
                                vertices.add(vertex);
                            }
                            indices.add(vertices.indexOf(vertex));
                        }
                        break;
                }
            }

            reader.close();

            Vertex[] verticesArray = vertices.toArray(new Vertex[vertices.size()]);
            Integer[] indicesArray = indices.toArray(new Integer[indices.size()]);
            return new Model(verticesArray, Utils.toPrimitive(indicesArray));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static Texture loadTexture(String textureId) {

        return new Texture("src/main/resources/texture/", textureId);
    }
}
