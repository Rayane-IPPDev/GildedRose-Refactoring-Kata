package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    GildedRose gildedRose;

    @Test
    void updateQualityForSulfuras_shouldNotDecreaseQuality() {
        Item[] items = new Item[]{
            new Item("Sulfuras, Hand of Ragnaros", 0, 80), //
            new Item("Sulfuras, Hand of Ragnaros", -1, 80),
        };
        gildedRose = new GildedRose(items);
        gildedRose.updateQuality();
        assertEquals(0, items[0].sellIn);
        assertEquals(80, items[0].quality);

        assertEquals(-1, items[1].sellIn);
        assertEquals(80, items[1].quality);


    }

    @Test
    void updateQualityForAgedBrie_shouldIncreaseQuality() {
        Item[] items = new Item[]{
            new Item("Aged Brie", 2, 0)
        };
        gildedRose = new GildedRose(items);
        gildedRose.updateQuality();
        assertEquals(1, items[0].sellIn);
        assertEquals(1, items[0].quality);
    }

    @Test
    void updateQualityForBackstage_shouldIncreaseQuality() {
        Item[] items = new Item[]{
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
        };
        gildedRose = new GildedRose(items);
        gildedRose.updateQuality();
        assertEquals(14, items[0].sellIn);
        assertEquals(21, items[0].quality);
    }

    @Test
    void updateQualityForBackstage_shouldIncreaseQualityNotDone() {
        // TODO
    }

    }
