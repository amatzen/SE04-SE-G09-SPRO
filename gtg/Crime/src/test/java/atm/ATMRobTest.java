package atm;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.ATMBalancePart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.PlayerPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.crime.CrimeActionHandler;
import dk.sdu.mmmi.swe.gtg.wantedlevelsystemcommon.services.IWantedLevelSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ATMRobTest {
    @InjectMocks
    private CrimeActionHandler crimeAction;

    @Mock
    private IWantedLevelSystem wantedLevelSystem;

    @Mock
    private IEngine engine;

    private Entity player;
    private List<Entity> entities = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        player = new Entity();
        player.addPart(new PlayerPart());
    }

    @Test
    void robberyTest() {
        int balance = 100;

        Entity atm = new Entity();
        atm.addPart(new ATMBalancePart(balance));

        Assertions.assertEquals(balance, atm.getPart(ATMBalancePart.class).getBalance());

        Mockito.when(engine.getEntitiesFor(Family.builder().with(PlayerPart.class).get()))
                .thenAnswer(i -> Arrays.asList(player));


        Assertions.assertEquals(0, player.getPart(PlayerPart.class).getBalance());

        crimeAction.commit(atm);

        Assertions.assertEquals(balance, player.getPart(PlayerPart.class).getBalance());

    }
}
