package com.foloke.haz.utils;

public class ItemsDataSheet {
    private ItemsDataSheet() {}

    public static ItemInfo[] itemInfoList = new ItemInfo[]{
            new ItemInfo(0, 1f, "debug", 1),
            new ItemInfo(1, 2f, "debug", 5),
            new ItemInfo(2, 5f, "debug", 2),
            new ItemInfo(3, 5f, "debug", 1),
            new ItemInfo(4, 5f, "debug", 1),
            new ItemInfo(5, 5f, "debug", 1),
            new ItemInfo(6, 5f, "debug", 1),
            new ItemInfo(7, 5f, "debug", 1),
            new ItemInfo(8, 5f, "debug", 1),
            new ItemInfo(9, 5f, "debug", 1),
            new ItemInfo(10, 5f, "debug", 1),
            new ItemInfo(11, 5f, "debug", 1),
            new ItemInfo(12, 5f, "debug", 1)
    };

    public static float getVolume(int ID) {
        for (ItemInfo itemInfo: itemInfoList) {
            if(itemInfo.ID == ID) {
                return itemInfo.volume;
            }
        }
        return 0;
    }

    public static String getName(int ID) {
        for (ItemInfo itemInfo: itemInfoList) {
            if(itemInfo.ID == ID) {
                return itemInfo.name;
            }
        }

        return "none";
    }

    public static int getValue(int ID) {
        for (ItemInfo itemInfo: itemInfoList) {
            if(itemInfo.ID == ID) {
                return itemInfo.value;
            }
        }

        return 0;
    }

    public static class ItemInfo {
        int ID;
        float volume;
        String name;
        int value;

        public ItemInfo(int ID, float volume, String name, int value) {
            this.ID = ID;
            this.volume = volume;
            this.name = name;
            this.value = value;
        }
    }
}
