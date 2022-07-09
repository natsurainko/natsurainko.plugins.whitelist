package natsurainko.plugins.whitelist.model;

public class WhiteListItem {
    public String uuid;
    public String qNumber;
    public String id;

    public WhiteListItem(String uuid, String qNumber, String id)
    {
        this.uuid = uuid;
        this.qNumber = qNumber;
        this.id = id;
    }
}
