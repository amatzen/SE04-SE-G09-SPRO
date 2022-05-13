package dk.sdu.mmmi.swe.gtg.core.internal;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.TransformPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.family.IFamily;
import dk.sdu.mmmi.swe.gtg.core.internal.managers.Engine;
import dk.sdu.mmmi.swe.gtg.core.internal.managers.FamilyManager;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CoreIntegrationTest {

    @Mock
    private FamilyManager familyManager;

    @InjectMocks
    private Engine engine;

    @Test
    public void engineFamilyManagerIntegrationTest() {

        Entity e = new Entity();
        e.addPart(new TransformPart());


        engine.addEntity(e);

        IFamily family = Family.builder().with(TransformPart.class).get();
        assertTrue(family.matches(e));

    }

}
