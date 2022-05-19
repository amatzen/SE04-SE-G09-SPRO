package dk.sdu.mmmi.swe.gtg.atm;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.ATMBalancePart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.IEntityPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.PlayerPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.commoncrime.ICrimeAction;
import dk.sdu.mmmi.swe.gtg.wantedlevelsystemcommon.services.IWantedLevelSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ATMTest {
    @Mock
    private ICrimeAction crimeAction;

    @Mock
    private IWantedLevelSystem wantedLevelSystem;

    @Mock
    private IEngine engine;

    private List<Entity> entities = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        Entity player = new Entity();
        player.addPart(new PlayerPart());

        doAnswer(i -> {
            this.entities.add(player);
            return null;
        }).when(engine).addEntity(player);

        engine.addEntity(player);
    }

    @Test
    void robberyTest() {
        int balance = 100;

        ATM atm = new ATM();
        atm.addPart(new ATMBalancePart(balance));

        assertEquals(balance, atm.getPart(ATMBalancePart.class).getBalance());

        mock(
            engine.getEntitiesFor(Family.builder().with(PlayerPart.class).get())
                    .get(0)
                    .getPart(PlayerPart.class)
        ).thenReturn(i -> this.entities.get(0).getPart(PlayerPart.class));

        PlayerPart player = engine.getEntitiesFor(Family.builder().with(PlayerPart.class).get())
                .get(0)
                .getPart(PlayerPart.class);

        doAnswer(i -> {
            player.deposit(atm.getPart(ATMBalancePart.class).getBalance());

            return null;
        }).when(crimeAction).commit(atm);


        assertEquals(0, player.getBalance());

        crimeAction.commit(atm);

        assertEquals(balance, player.getBalance());

    }
}
