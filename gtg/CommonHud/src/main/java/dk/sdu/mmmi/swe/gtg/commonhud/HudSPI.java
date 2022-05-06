package dk.sdu.mmmi.swe.gtg.commonhud;

public interface HudSPI {
    public void setHealth(int value);
    public int getHealth();
    public void setWanted();
    public int getWanted();
    public void setMoney();
    public int getMoney();
}
