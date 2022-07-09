package natsurainko.plugins.whitelist.model;

public enum WhiteListFilterType {
    UUID(0, "uuid"),
    QNUMBER(1,"qNumber"),
    ID(2,"id");

    private int index;
    private String name;

    WhiteListFilterType(int index,String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
