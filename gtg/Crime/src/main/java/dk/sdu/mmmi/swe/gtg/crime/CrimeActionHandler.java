package dk.sdu.mmmi.swe.gtg.crime;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.ATMBalancePart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.TexturePart;
import dk.sdu.mmmi.swe.gtg.commoncrime.ICrimeAction;
import dk.sdu.mmmi.swe.gtg.commonhud.HudSPI;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class CrimeActionHandler implements ICrimeAction {

    @Reference
    private HudSPI hud;

    public static Music cashSound;

    public void commit(Entity entity) {
        ATMBalancePart atmBalance = entity.getPart(ATMBalancePart.class);
        if (atmBalance != null) {
            hud.addMoney(atmBalance.getBalance());

            cashSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/Cash.mp3"));
            cashSound.setVolume(0.3f);
            cashSound.play();

            atmBalance.setRobbed(true);
            entity.addPart(getBodyTexture());
            atmBalance.destroy();
        }
    }

    private TexturePart getTexture(String path) {
        final TexturePart texturePart = new TexturePart();
        FileHandle file = Gdx.files.internal(path);
        Texture texture = new Texture(file);
        TextureRegion textureRegion = new TextureRegion(texture);
        texturePart.setRegion(textureRegion);
        return texturePart;
    }

    private TexturePart getBodyTexture() {
        return getTexture("assets/atmRobbed.png");
    }

}
