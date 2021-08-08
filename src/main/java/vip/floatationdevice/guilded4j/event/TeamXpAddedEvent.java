package vip.floatationdevice.guilded4j.event;

import java.util.ArrayList;

public class TeamXpAddedEvent extends GuildedEvent
{
    int xpAmount=0;
    ArrayList<String> userIds=new ArrayList<String>();

    public TeamXpAddedEvent(Object source){super(source);}

    public int getXpAmount(){return this.xpAmount;}
    public ArrayList<String> getUserIds(){return this.userIds;}

    public TeamXpAddedEvent setXpAmount(int amount){this.xpAmount=amount;return this;}
    public TeamXpAddedEvent setUserIds(ArrayList<String> userIds){return this;}
}
