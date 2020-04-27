package com.foloke.haz.components;

import com.foloke.haz.entities.Drop;
import com.foloke.haz.entities.Entity;
import com.foloke.haz.screens.GameScreen;
import com.foloke.haz.utils.ItemsDataSheet;

import java.util.ArrayList;
import java.util.Objects;

public class Inventory {

    private ArrayList<Item> items;
    private float maxVolume = 1000;
    private float volume;
    public Entity parent;

    public Inventory(Entity parent) {
        items = new ArrayList<>();
        volume = 0;
        this.parent = parent;
    }

    public boolean add(Item item) {
        float plusVolume = ItemsDataSheet.getVolume(item.ID) * item.count;
        if(volume + plusVolume <= maxVolume) {
            volume += plusVolume;
            if(items.contains(item)) {

                items.get(items.indexOf(item)).count += item.count;
                return true;
            }
            items.add(item);
            return true;
        }
        return false;
    }

    public boolean add(Item item, int count) {
        float plusVolume = ItemsDataSheet.getVolume(item.ID) * count;
        if(volume + plusVolume <= maxVolume) {
            volume += plusVolume;
            if(items.contains(item)) {

                items.get(items.indexOf(item)).count += count;
                return true;
            }
            items.add(new Item(item.ID, count));
            return true;
        }
        return false;
    }

    public boolean remove(Item item) {
        if(items.contains(item)) {
            Item allItem = items.get(items.indexOf(item));
            if(allItem.count > item.count) {
                allItem.count -= item.count;
            } else if (allItem.count == item.count) {
                items.remove(item);
            } else {
                return false;
            }

            return true;
        }

        return false;
    }

    public boolean remove(Item item, int count) {
        if(items.contains(item)) {
            Item allItem = items.get(items.indexOf(item));
            if(allItem.count > count) {
                allItem.count -= count;
            } else if (allItem.count == count) {
                items.remove(item);
            } else {
                return false;
            }

            return true;
        }

        return false;
    }

    public ArrayList<Item> getAll() {
        return items;
    }

    public void clearAll() {
        items.clear();
    }

    public static class Item {
        public int ID;
        public int count;

        public Item(int ID, int count) {
            this.ID = ID;
            this.count = count;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Item item = (Item) o;
            return ID == item.ID;
        }

        @Override
        public int hashCode() {
            return Objects.hash(ID);
        }
    }
}
