package com.nami.gui.text;

import com.nami.render.gui.text.TextMesh;
import org.joml.Vector2f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Text {

    private final float spacing = 2;

    private Font font;
    private TextMesh mesh;

    public Text(String str, Font font) {
        this.font = font;

        FontMeta meta = font.meta();
        char[] chars = str.toCharArray();

        FloatBuffer positions = FloatBuffer.allocate(chars.length * 4 * 2);
        FloatBuffer texCoords = FloatBuffer.allocate(chars.length * 4 * 2);
        IntBuffer indices = IntBuffer.allocate(chars.length * 3 * 2);

        float cursor = 0;
        for (int i = 0; i < chars.length; i++) {
            char chr = chars[i];
            FontChar fontChar = meta.characters().get(chr);

            //Positions
            Vector2f topLeft = new Vector2f(cursor, 0).div(1920,1080);
            positions.put(topLeft.x());
            positions.put(topLeft.y());

            Vector2f topRight = new Vector2f(cursor + fontChar.width(), 0).div(1920,1080);
            positions.put(topRight.x());
            positions.put(topRight.y());

            Vector2f bottomLeft = new Vector2f(cursor, fontChar.height()).div(1920,1080);
            positions.put(bottomLeft.x());
            positions.put(bottomLeft.y());

            Vector2f bottomRight = new Vector2f(cursor + fontChar.width(), fontChar.height()).div(1920,1080);
            positions.put(bottomRight.x());
            positions.put(bottomRight.y());

            //Texture Coords
            Vector2f topLeftC = new Vector2f(fontChar.x() / meta.width(), fontChar.y() / meta.height());
            texCoords.put(topLeftC.x());
            texCoords.put(topLeftC.y());

            Vector2f topRightC = new Vector2f((fontChar.x() + fontChar.width()) / meta.width(), fontChar.y() / meta.height());
            texCoords.put(topRightC.x());
            texCoords.put(topRightC.y());

            Vector2f bottomLeftC = new Vector2f(fontChar.x() / meta.width(), (fontChar.y() + fontChar.height()) / meta.height());
            texCoords.put(bottomLeftC.x());
            texCoords.put(bottomLeftC.y());

            Vector2f bottomRightC = new Vector2f((fontChar.x() + fontChar.width()) / meta.width(), (fontChar.y() + fontChar.height()) / meta.height());
            texCoords.put(bottomRightC.x());
            texCoords.put(bottomRightC.y());

            //Indices
            indices.put(i * 4 + 1);
            indices.put(i * 4 + 2);
            indices.put(i * 4);

            indices.put(i * 4 + 3);
            indices.put(i * 4 + 2);
            indices.put(i * 4 + 1);

            cursor += fontChar.advance() + ((i + 1) * spacing);
        }

        mesh = new TextMesh(positions.array(), texCoords.array(), indices.array());
    }

    public Font getFont() {
        return font;
    }

}
