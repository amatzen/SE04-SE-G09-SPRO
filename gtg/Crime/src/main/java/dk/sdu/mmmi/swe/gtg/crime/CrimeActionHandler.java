package dk.sdu.mmmi.swe.gtg.crime;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.commoncrime.ICrimeAction;
import org.osgi.service.component.annotations.Component;

@Component
public class CrimeActionHandler implements ICrimeAction {

    public void commit(Entity entity) {
        System.out.println("Committed crime against " + entity.getClass().getSimpleName());
    }
}
