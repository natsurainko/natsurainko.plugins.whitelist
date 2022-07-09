package natsurainko.plugins.whitelist.model;

import java.util.List;

public class ProfileResponse {
    private String id;
    private String name;
    private List<Property> properties;

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
    public List<Property> getProperties() {
        return properties;
    }
}

class Property {
    private String name;
    private String value;
    private String signature;

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
    public String getSignature() {
        return signature;
    }

}
