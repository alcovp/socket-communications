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
    public static Model loadModel(String path, String modelId) {

        try {
            Model model = null;
            BufferedReader reader = new BufferedReader(new FileReader(path + modelId));

            List<Vertex> vertices = new ArrayList<>();
            List<Vector3f> positions = new ArrayList<>();
            List<Vector2f> textureCoordinates = new ArrayList<>();
            List<Vector3f> normals = new ArrayList<>();
            List<Integer> indices = new ArrayList<>();

            String[] modelIdSegments = modelId.split("\\.");
            String extension = modelIdSegments[modelIdSegments.length - 1];
            SupportedFormats format = SupportedFormats.findByExtension(extension);
            if (format != null) {
                switch (format) {
                    case OBJ:
                        fillModelArraysFromOBJ(reader, vertices, positions, textureCoordinates, normals, indices);
                        break;
                    case MD5:
                        fillModelArraysFromMD5(reader, vertices, positions, textureCoordinates, normals, indices);
                        break;
                    default:
                        throw new IllegalStateException("Unknown model format: " + format);
                }

                reader.close();

                Vertex[] verticesArray = vertices.toArray(new Vertex[vertices.size()]);
                Integer[] indicesArray = indices.toArray(new Integer[indices.size()]);
                model = new Model(verticesArray, Utils.toPrimitive(indicesArray));
            }

            return model;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void fillModelArraysFromOBJ(BufferedReader reader, List<Vertex> vertices, List<Vector3f> positions,
                                               List<Vector2f> textureCoordinates, List<Vector3f> normals,
                                               List<Integer> indices) throws IOException {
        String line;
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
                    textureCoordinates.add(new Vector2f(Float.valueOf(tokens[1]),
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
                                textureCoordinates.get(Integer.valueOf(vertexIndices[1]) - 1),
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
    }

    private static void fillModelArraysFromMD5(BufferedReader reader, List<Vertex> vertices, List<Vector3f> positions,
                                               List<Vector2f> textureCoordinates, List<Vector3f> normals,
                                               List<Integer> indices) throws IOException {
        String line;
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
                    textureCoordinates.add(new Vector2f(Float.valueOf(tokens[1]),
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
                                textureCoordinates.get(Integer.valueOf(vertexIndices[1]) - 1),
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
    }

    public static Texture loadTexture(String path, String textureId) {
        return new Texture(path, textureId);
    }

    private enum SupportedFormats {
        OBJ("obj"),
        MD5("md5");

        private String extension;

        SupportedFormats(String extension) {
            this.extension = extension;
        }

        public static SupportedFormats findByExtension(String ext) {
            if (ext != null) {
                for (SupportedFormats format : SupportedFormats.values()) {
                    if (ext.equalsIgnoreCase(format.getExtension())) {
                        return format;
                    }
                }
            }
            return null;
        }

        private String getExtension() {
            return extension;
        }
    }
}
