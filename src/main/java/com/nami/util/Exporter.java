package com.nami.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Exporter {

    public static void exportMesh(String name, FloatBuffer positions, FloatBuffer normals, FloatBuffer texCoords, IntBuffer faces, String path) throws IOException {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("o %s\n", name));

        for (int i = 0; i < positions.limit() / 3; i++)
            sb.append(String.format("v %s %s %s\n", positions.get(i * 3), positions.get(i * 3 + 1), positions.get(i * 3 + 2)));

        for (int i = 0; i < normals.limit() / 3; i++)
            sb.append(String.format("vn %s %s %s\n", normals.get(i * 3), normals.get(i * 3 + 1), normals.get(i * 3 + 2)));

        for (int i = 0; i < texCoords.limit() / 2; i++)
            sb.append(String.format("vt %s %s\n", texCoords.get(i * 2), texCoords.get(i * 2 + 1)));

        for (int i = 0; i < faces.limit() / 3; i++) {
            int i1 = faces.get(i * 3);
            int i2 = faces.get(i * 3 + 1);
            int i3 = faces.get(i * 3 + 2);
            sb.append(String.format("f %s/%s/%s %s/%s/%s %s/%s/%s\n", i1, i1, i1, i2, i2, i2, i3, i3, i3));
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.append(sb.toString());
        writer.close();
    }

}
