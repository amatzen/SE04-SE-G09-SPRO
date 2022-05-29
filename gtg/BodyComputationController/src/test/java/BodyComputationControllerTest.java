import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import dk.sdu.mmmi.swe.gtg.bodycomputationcontroller.internal.BodyComputationController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BodyComputationControllerTest {
    private World testWorld;
    private Body testBody;

    @BeforeEach
    public void setUp() {
        testWorld = new World(new Vector2(0, 0), true);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 0);
        testBody = testWorld.createBody(bodyDef);
    }

    @Test
    public void bodyDirectionTest() {
        BodyComputationController bcc = new BodyComputationController();

        testBody.setLinearVelocity(0, -100);
        Assertions.assertEquals(0, bcc.direction(testBody));

        testBody.setLinearVelocity(0, 100);
        Assertions.assertEquals(1, bcc.direction(testBody));

        testBody.setLinearVelocity(0, 0);
        Assertions.assertEquals(2, bcc.direction(testBody));
    }

}
