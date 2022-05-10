package dk.sdu.mmmi.swe.gtg.crime;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.ATMBalancePart;
import dk.sdu.mmmi.swe.gtg.commoncrime.ICrimeAction;
import dk.sdu.mmmi.swe.gtg.commonhud.HudSPI;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class CrimeActionHandler implements ICrimeAction {

    @Reference
    private HudSPI hud;

    public void commit(Entity entity) {
        ATMBalancePart atmBalance = entity.getPart(ATMBalancePart.class);
        if (atmBalance != null) {
            hud.addMoney(atmBalance.getBalance());
            atmBalance.destroy();
        }
    }
}
