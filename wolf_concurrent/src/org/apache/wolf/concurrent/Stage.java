package org.apache.wolf.concurrent;

public enum Stage {
	MUTATION,GOSSIP;
    public String getJmxType()
    {
        switch (this)
        {
            case MUTATION:
            case GOSSIP:
                return "request";
            default:
                throw new AssertionError("Unknown stage " + this);
        }
    }

    public String getJmxName()
    {
        String name = "";
        for (String word : toString().split("_"))
        {
            name += word.substring(0, 1) + word.substring(1).toLowerCase();
        }
        return name + "Stage";
    }
}
