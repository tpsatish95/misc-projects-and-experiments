package com.myFirstApp.contactmanager.cm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by $@T!$# on 27-07-2014.
 */
public class DatabaseHandler extends SQLiteOpenHelper {


    private final static int DATABASE_VERSION=1;
    private final static String DATABASE_NAME="contactsManager",
    TABLE_CONTACTS = "contactsTable",
    KEY_ID = "id",
    KEY_NAME = "name",
    KEY_PHONE = "phone",
    KEY_EMAIL = "email",
    KEY_ADDRESS = "address",
    KEY_IMAGEURI = "imageUri";





    public DatabaseHandler(Context context) {
        super(context,DATABASE_NAME, null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL("CREATE TABLE "+TABLE_CONTACTS+" ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_NAME+" TEXT,"+KEY_PHONE+" TEXT,"+KEY_EMAIL+" TEXT,"+KEY_ADDRESS+" TEXT,"+KEY_IMAGEURI+" TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS "+TABLE_CONTACTS);
        onCreate(db);
    }


    public void createContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PHONE, contact.getPhone());
        values.put(KEY_EMAIL, contact.getEmail());
        values.put(KEY_ADDRESS, contact.getAddress());
        values.put(KEY_IMAGEURI, contact.getImgUri().toString());

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public void deleteContact(Contact contact)
    {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_CONTACTS,KEY_ID + "=?",new String[] {String.valueOf(contact.getId())} );
        db.close();
    }

    public int getContactsCount()
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM "+TABLE_CONTACTS,null);

        int c = cursor.getCount();

        db.close();
        cursor.close();

         return   c;

    }

    public void updateContact(Contact contact)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID,contact.getId());
        values.put(KEY_NAME,contact.getName());
        values.put(KEY_PHONE,contact.getPhone());
        values.put(KEY_EMAIL,contact.getEmail());
        values.put(KEY_ADDRESS,contact.getAddress());
        values.put(KEY_IMAGEURI,contact.getImgUri().toString());

        db.update(TABLE_CONTACTS,values,KEY_ID + "=?",new String[] {String.valueOf(contact.getId())});

        db.close();

    }

    public List<Contact> getAllContacts()
    {
        List<Contact> contacts = new ArrayList<Contact>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_CONTACTS,null);

        if(cursor.moveToFirst())
        {
            do {
                contacts.add(new Contact(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4), Uri.parse(cursor.getString(5))));

            }while(cursor.moveToNext());

        }

        db.close();
        cursor.close();
        return contacts;
    }

}
