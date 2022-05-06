package dk.sdu.mmmi.swe.gtg.crime;

import dk.sdu.mmmi.swe.gtg.commoncrime.ICrimeAction;
import org.osgi.service.component.annotations.Component;

@Component
public class CrimeActionHandler implements ICrimeAction {

    public void commit() {
        System.out.println("Commit crime");
    }
}
