package com.esp.alarm.utils;

import io.realm.Realm;
import io.realm.RealmModel;

public class RealmUtils {
    public static int getNextPrimaryKey(Realm realm, Class<? extends RealmModel> objectClass) {
        Number currentIdNum = realm.where(objectClass).max("id");
        int nextPrimaryKey;
        if (currentIdNum == null) {
            nextPrimaryKey = 1;
        } else {
            nextPrimaryKey = currentIdNum.intValue() + 1;
        }
        return nextPrimaryKey;
    }
}
