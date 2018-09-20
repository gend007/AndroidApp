package com.example.gend.mydiary;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gend on 2017/11/08.
 */

public class Diary extends RealmObject {
    @PrimaryKey
    public  long id;
    public String title;
    public String bodyText;
    public String date;
    public byte[] image;
}
