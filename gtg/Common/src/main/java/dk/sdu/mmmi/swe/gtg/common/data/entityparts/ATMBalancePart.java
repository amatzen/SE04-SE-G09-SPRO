package dk.sdu.mmmi.swe.gtg.common.data.entityparts;

public class ATMBalancePart implements IEntityPart {
    @Override
    public void destroy() {
        System.out.println("destroy");
    }
}
