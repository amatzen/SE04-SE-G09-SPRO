package dk.sdu.mmmi.swe.gtg.commonhud;

public interface HudSPI {

    public void addHealth(int value);
    public void setHealth(int value);
    public int getHealth();
    public void loseHealth(int value);

    public void addMoney(int value);
    public void removeMoney(int value);
    public int getMoney();


    public void addWanted(int value);
    public void decreaseWanted(int value);
    public int getWanted();

}
