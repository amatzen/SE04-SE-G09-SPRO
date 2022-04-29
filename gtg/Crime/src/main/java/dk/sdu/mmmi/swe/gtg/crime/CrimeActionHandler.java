package dk.sdu.mmmi.swe.gtg.crime;

import dk.sdu.mmmi.swe.gtg.commoncrime.ICrimeAction;
import org.osgi.service.component.annotations.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Component
public class CrimeActionHandler implements ICrimeAction {

    public void commit() {
        throw new NotImplementedException();
    }
}
