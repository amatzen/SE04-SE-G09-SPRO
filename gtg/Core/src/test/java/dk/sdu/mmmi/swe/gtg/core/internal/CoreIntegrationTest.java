package dk.sdu.mmmi.swe.gtg.core.internal;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.TransformPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.family.IFamily;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IFamilyManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CoreIntegrationTest {

    @Mock
    private IFamilyManager familyManager;

    @Mock
    private IEngine engine;

    @Mock
    private Family family;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void engineFamilyManagerIntegrationTest() {

        Entity e = new Entity();
        e.addPart(new TransformPart());

        engine.addEntity(e);

        IFamily family = Family.builder().with(TransformPart.class).get();
        assertTrue(family.matches(e));

    }

}
