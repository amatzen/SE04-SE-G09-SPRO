package dk.sdu.mmmi.swe.gtg.common.data.entityparts;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TexturePart implements IEntityPart{

    private TextureRegion texture;

    public TexturePart() {

    }

    public TexturePart setRegion(TextureRegion texture) {
        this.texture = texture;
        return this;
    }

    @Override
    public void destroy() {

    }
}
