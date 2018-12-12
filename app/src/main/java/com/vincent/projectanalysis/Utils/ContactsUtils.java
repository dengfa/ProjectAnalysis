package com.vincent.projectanalysis.utils;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.RawContacts.Data;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dengfa on 2018/12/6.
 */

public class ContactsUtils {
    //家校盒子教师通知提醒 - 通讯录姓名
    public static final String CONTACT_NAME = "家校盒子教师通知提醒";

    /**
     * 添加联系人
     */
    public static void addContact(Context context, List<String> phoneNumbers) throws Exception {
        if (phoneNumbers == null || phoneNumbers.isEmpty()) {
            return;
        }
        LogUtil.d("vincent", "addContact");
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        int rawContactInsertIndex = 0;
        ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
                .withValue(RawContacts.ACCOUNT_TYPE, null)
                .withValue(RawContacts.ACCOUNT_NAME, null)
                .build());
        ops.add(ContentProviderOperation.newInsert(android.provider.ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
                .withValue(StructuredName.DISPLAY_NAME, CONTACT_NAME)
                .build());
        for (String number : phoneNumbers) {
            ops.add(ContentProviderOperation.newInsert(android.provider.ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex)
                    .withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
                    .withValue(Phone.NUMBER, number)
                    .withValue(Phone.TYPE, Phone.TYPE_MOBILE)
                    .build());
        }
        context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
    }

    /**
     * 更新家校盒子教师通知提醒电话列表
     */
    public static void update(Context context, List<String> phoneNumberList) throws Exception {
        Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            if (TextUtils.equals(CONTACT_NAME, contactName)) {
                LogUtil.d(contactName + " - " + contactId);
                Cursor phone = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                while (phone.moveToNext()) {
                    String phoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    LogUtil.d(phoneNumber);
                    if (phoneNumberList.contains(phoneNumber)) {
                        phoneNumberList.remove(phoneNumber);
                    }
                }
            }
        }
        addContact(context, phoneNumberList);
    }
}
