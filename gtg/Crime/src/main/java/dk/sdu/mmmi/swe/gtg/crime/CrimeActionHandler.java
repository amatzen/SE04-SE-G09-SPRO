package dk.sdu.mmmi.swe.gtg.crime;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.ATMBalancePart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.PlayerPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.commoncrime.ICrimeAction;
import dk.sdu.mmmi.swe.gtg.wantedlevelsystemcommon.services.IWantedLevelSystem;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class CrimeActionHandler implements ICrimeAction {

    @Reference
    private IEngine engine;

    @Reference
    private IWantedLevelSystem wantedLevelSystem;

    public void commit(Entity entity) {
        ATMBalancePart atmBalance = entity.getPart(ATMBalancePart.class);
        if (atmBalance != null) {
            PlayerPart player = engine.getEntitiesFor(Family.builder().with(PlayerPart.class).get()).get(0).getPart(PlayerPart.class);
            player.deposit(atmBalance.getBalance());

            atmBalance.setRobbed(true);
            atmBalance.destroy();

            wantedLevelSystem.reportCrime(10f);
        }
    }

}
