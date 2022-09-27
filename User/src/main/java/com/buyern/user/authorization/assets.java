package com.buyern.user.authorization;

import java.util.Date;
import java.util.List;

public class assets {
    long id;
    String uid;
    String name;
    Location location;
    AssetType type;
    String images; //LONGTEXT
    List<AssetProperty> properties;
    Date timeRegistered; // current timestamp

    public class AssetProperty {
        int id;
        String name;
        String value;
        String image;
    }

    public class assetPredefinedTypes {
        int id;
        boolean isRequired;
        String name;
        String value;
        String image;
        assets assets;
    }

    public class AssetType {
        int id;
        String name;
        String value;
        int entityId;
    }

    public class Location {

    }
}
