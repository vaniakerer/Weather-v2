package com.example.ivan.weatherapp.data.database.migration;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by ivan
 */

public class DbMigration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema realmSchema = realm.getSchema();
        if (oldVersion == 1) {
            RealmObjectSchema schema = realmSchema.get("DbCity");
            schema.addField("number", int.class);
            oldVersion++;
        }

        if (oldVersion == 2) {
            RealmObjectSchema schema = realmSchema.get("DbCity");
            schema.addField("numberTwo", int.class);
            RealmObjectSchema numberTwo = schema.transform(obj -> obj.set("numberTwo", 100));
        }

        if (oldVersion == 3) {
            RealmObjectSchema citySchema = realmSchema.get("DbCity");
            citySchema.removeField("number");
            citySchema.removeField("numberTwo");

            RealmObjectSchema weatherSchema = realmSchema.get("DbWeather");
            weatherSchema.removeField("total");
            weatherSchema.removeField("testTotal");

            weatherSchema.addField("summOfTemperatureAndWindSpeed", double.class);
            weatherSchema.transform(obj -> obj.set("summOfTemperatureAndWindSpeed", ((double) obj.get("temperature")) + ((double) obj.get("windSpeed"))));

        }

    }
}
