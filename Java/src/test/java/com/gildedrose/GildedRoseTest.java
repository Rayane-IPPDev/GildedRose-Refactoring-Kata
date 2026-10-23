package com.gildedrose;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Disabled
    @Test
    void foo() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("fixme", app.items[0].name);
    }
    GildedRose gildedRose;

    @Test
    void updateQualityForSulfuras(){
        Item[] items = new Item[] {
            new Item("Sulfuras, Hand of Ragnaros", 0, 80), //
            new Item("Sulfuras, Hand of Ragnaros", -1, 80),
        };
        gildedRose=new GildedRose(items);
        gildedRose.updateQuality();
        Assertions.assertEquals(0,items[0].sellIn);
        Assertions.assertEquals(80,items[0].quality);

        Assertions.assertEquals(-1,items[1].sellIn);
        Assertions.assertEquals(80,items[1].quality);


    }
    @Test
    void updateQualityForAged(){
        Item[] items = new Item[] {
            new Item("Aged Brie", 2, 0)
        };
        gildedRose=new GildedRose(items);
        gildedRose.updateQuality();
        Assertions.assertEquals(1,items[0].sellIn);
        Assertions.assertEquals(1,items[0].quality);



    }
    @Test
    void updateQualityForBackstage(){
        Item[] items = new Item[] {
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
        };
        gildedRose=new GildedRose(items);
        gildedRose.updateQuality();
        Assertions.assertEquals(14,items[0].sellIn);
        Assertions.assertEquals(21,items[0].quality);



    }

}
