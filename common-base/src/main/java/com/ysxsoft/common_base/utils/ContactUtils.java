package com.ysxsoft.common_base.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.core.content.ContentResolverCompat;

import com.google.gson.Gson;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ContactUtils {
    public static void getContact(Context context, String keyword,OnContactResponseListener listener){
        //获取手机通讯录
        Cursor cursor= ContentResolverCompat.query(context.getContentResolver(), ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER}
                ,null,null,null,null);
        List<Contact> data=new ArrayList<>();
        if(cursor!=null){
            while (cursor.moveToNext()){
                Contact contact=new Contact();
                String contactId=cursor.getString(0);
                String displayName=cursor.getString(1);
                String phoneNumber=cursor.getString(2);
                contact.setContactId(contactId);
                contact.setDisplayName(displayName);
                String p=phoneNumber.replaceAll(" ","").replaceAll("-","").replaceAll("\\+86","");
                if(p.length()==11){
                    contact.setPhoneNumber(p);
                    data.add(contact);
                }
            }
        }
        //获取sim卡的通讯录数据
//        Cursor cursor2=ContentResolverCompat.query(context.getContentResolver(), Uri.parse("content://icc/adn"),
//                new String[]{ContactsContract.CommonDataKinds.Phone.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER}
//                ,null,null,null,null);
//        if(cursor2!=null){
//            while (cursor2.moveToNext()){
//                Contact contact=new Contact();
//                String contactId=cursor2.getString(0);
//                String displayName=cursor2.getString(1);
//                String phoneNumber=cursor2.getString(2);
//                contact.setContactId(contactId);
//                contact.setDisplayName(displayName);
//                contact.setPhoneNumber(phoneNumber);
//                data.add(contact);
//            }
//        }
        Map<String,List<Contact>> map=parseData(data);
        Collection<List<Contact>> c=map.values();
        List<Contact> all = new ArrayList<>();
        Iterator<List<Contact>> d = c.iterator();
        while (d.hasNext()) {
            if("".equals(keyword)){
                all.addAll(d.next());
            }else{
                List<Contact> citys=d.next();
                for (int i = 0; i <citys.size(); i++) {
                    if(citys.get(i).getDisplayName().contains(keyword)||citys.get(i).getPhoneNumber().contains(keyword)){
                        all.add(citys.get(i));
                    }
                }
            }
        }
        Collections.sort(all, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.getLetter().compareTo(o2.getLetter());
            }
        });
        if(listener!=null){
            listener.onResponse(map,all,getPosition(map));
        }
    }

    /**
     * 排序通讯录
     * @param contacts
     * @return
     */
    public static Map<String, List<Contact>> parseData(List<Contact> contacts) {
        Map<String, List<Contact>> maps = new HashMap<>();//把所有数据分组
        List<Contact> contacts1 = new ArrayList<>();
        for (int i = 0; i < contacts.size(); i++) {
            Contact c = new Contact();
            c.setLetter(PinYinUtils.getFirst(contacts.get(i).getDisplayName()));
            c.setPhoneNumber(contacts.get(i).getPhoneNumber());
            c.setContactId(contacts.get(i).getContactId());
            c.setDisplayName(contacts.get(i).getDisplayName());
            contacts1.add(c);
        }
        Collections.sort(contacts1, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                String o = o1.getLetter();
                String a = o2.getLetter();
                return Collator.getInstance(Locale.CHINESE).compare(o, a);
            }
        });
        for (int i = 0; i < contacts1.size(); i++) {
            Contact f = contacts1.get(i);
            String a = f.getLetter();//获取首字母
            if (maps.containsKey(a)) {
                List<Contact> list = maps.get(a);
                list.add(f);
            } else {
                List<Contact> list2 = new ArrayList<>();
                list2.add(f);
                maps.put(a, list2);
            }
        }
        return maps;
    }

    public static Map<String, Integer> getPosition(Map<String, List<Contact>> maps) {
        Map<String, Integer> positions = new HashMap<>();
        positions.clear();//清空首字母
        int lastListSize = 0;
        //把所有数据整合  得到首次出现位置 把#号数据放后边
        String[] labels = new String[]{"#","A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        List<Contact> tempList = new ArrayList<>();
        for (int i = 0; i < labels.length; i++) {
            String key = labels[i];
            if (maps.containsKey(key)) {
                List<Contact> ll = maps.get(key);
                tempList.addAll(ll);
                if (positions.isEmpty()) {
                    positions.put(key, lastListSize);//从分组开始计算position
                } else {
                    positions.put(key, lastListSize);
                }
                lastListSize += ll.size();
            }
        }
        return positions;
    }

    public static class Contact{
        private String contactId;
        private String displayName;
        private String phoneNumber;
        private String letter;

        public String getLetter() {
            return letter == null ? "" : letter;
        }

        public void setLetter(String letter) {
            this.letter = letter;
        }

        public String getContactId() {
            return contactId == null ? "" : contactId;
        }

        public void setContactId(String contactId) {
            this.contactId = contactId;
        }

        public String getDisplayName() {
            return displayName == null ? "" : displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getPhoneNumber() {
            return phoneNumber == null ? "" : phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }

    public interface OnContactResponseListener{
        void onResponse(Map<String,List<Contact>> rawData,List<Contact> sortedData,Map<String, Integer> position);
    }
}
