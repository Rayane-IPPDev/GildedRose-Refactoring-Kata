package com.gildedrose;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    private static final String NORMAL = "+5 Dexterity Vest";
    private static final String CONJURED = "Conjured";
    private static final String AGED_BRIE = "Aged Brie";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert";

    GildedRose gildedRose;

    private static int updatedQuality(String name, int sellIn, int quality) {
        Item[] items = new Item[]{new Item(name, sellIn, quality)};
        new GildedRose(items).updateQuality();
        return items[0].quality;
    }

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

    /*
     * Contrat attendu : chaque méthode reçoit le sellIn AVANT la mise à jour du jour
     * ainsi que la qualité courante, et retourne la NOUVELLE qualité.
     */

    @Nested
    class NormalItem {

        @ParameterizedTest(name = "sellIn={0}, quality={1} -> {2}")
        @CsvSource({
            "10, 20, 19",
            "1, 20, 19",
            "1, 1, 0"
        })
        void shouldDecreaseQualityByOne_beforeSellDate(int sellIn, int quality, int expected) {
            assertEquals(expected, updatedQuality(NORMAL, sellIn, quality));
        }

        @ParameterizedTest(name = "sellIn={0}, quality={1} -> {2}")
        @CsvSource({
            "0, 20, 18",
            "-1, 20, 18",
            "-10, 20, 18"
        })
        void shouldDecreaseQualityByTwo_afterSellDate(int sellIn, int quality, int expected) {
            assertEquals(expected, updatedQuality(NORMAL, sellIn, quality));
        }

        @ParameterizedTest(name = "sellIn={0}, quality={1} -> 0")
        @CsvSource({
            "5, 0",
            "0, 0",
            "0, 1",
            "-1, 1",
            "-1, 0"
        })
        void shouldNeverBeNegative(int sellIn, int quality) {
            assertEquals(0, updatedQuality(NORMAL, sellIn, quality));
        }
    }

    @Nested
    class AgedBrie {

        @ParameterizedTest(name = "sellIn={0}, quality={1} -> {2}")
        @CsvSource({
            "10, 0, 1",
            "1, 20, 21",
            "1, 49, 50"
        })
        void shouldIncreaseQualityByOne_beforeSellDate(int sellIn, int quality, int expected) {
            assertEquals(expected, updatedQuality(AGED_BRIE, sellIn, quality));
        }

        @ParameterizedTest(name = "sellIn={0}, quality={1} -> {2}")
        @CsvSource({
            "0, 20, 22",
            "-1, 20, 22",
            "-10, 48, 50"
        })
        void shouldIncreaseQualityByTwo_afterSellDate(int sellIn, int quality, int expected) {
            assertEquals(expected, updatedQuality(AGED_BRIE, sellIn, quality));
        }

        @ParameterizedTest(name = "sellIn={0}, quality={1} -> 50")
        @CsvSource({
            "10, 50",
            "0, 50",
            "-1, 50",
            "0, 49",
            "-1, 49"
        })
        void shouldNeverExceed50(int sellIn, int quality) {
            assertEquals(50, updatedQuality(AGED_BRIE, sellIn, quality));
        }
    }

    @Nested
    class Sulfuras {

        @ParameterizedTest(name = "sellIn={0} -> quality stays 80")
        @CsvSource({"10", "1", "0", "-1", "-10"})
        void shouldNeverChangeQuality(int sellIn) {
            assertEquals(80, updatedQuality(SULFURAS, sellIn, 80));
        }
    }

    @Nested
    class BackstagePasses {

        @ParameterizedTest(name = "sellIn={0}, quality={1} -> {2}")
        @CsvSource({
            "11, 20, 21",
            "15, 20, 21",
            "30, 0, 1"
        })
        void shouldIncreaseQualityByOne_whenMoreThan10DaysLeft(int sellIn, int quality, int expected) {
            assertEquals(expected, updatedQuality(BACKSTAGE, sellIn, quality));
        }

        @ParameterizedTest(name = "sellIn={0}, quality={1} -> {2}")
        @CsvSource({
            "10, 20, 22",
            "8, 20, 22",
            "6, 20, 22"
        })
        void shouldIncreaseQualityByTwo_when10To6DaysLeft(int sellIn, int quality, int expected) {
            assertEquals(expected, updatedQuality(BACKSTAGE, sellIn, quality));
        }

        @ParameterizedTest(name = "sellIn={0}, quality={1} -> {2}")
        @CsvSource({
            "5, 20, 23",
            "3, 20, 23",
            "1, 20, 23"
        })
        void shouldIncreaseQualityByThree_when5To1DaysLeft(int sellIn, int quality, int expected) {
            assertEquals(expected, updatedQuality(BACKSTAGE, sellIn, quality));
        }

        @ParameterizedTest(name = "sellIn={0}, quality={1} -> 0")
        @CsvSource({
            "0, 20",
            "-1, 20",
            "-10, 50"
        })
        void shouldDropQualityToZero_afterConcert(int sellIn, int quality) {
            assertEquals(0, updatedQuality(BACKSTAGE, sellIn, quality));
        }

        @ParameterizedTest(name = "sellIn={0}, quality={1} -> 50")
        @CsvSource({
            "15, 50",
            "15, 49",
            "10, 49",
            "10, 50",
            "5, 48",
            "5, 49",
            "5, 50"
        })
        void shouldNeverExceed50(int sellIn, int quality) {
            assertEquals(50, updatedQuality(BACKSTAGE, sellIn, quality));
        }
    }

    @Nested
    class ConjuredItem {

        @ParameterizedTest(name = "sellIn={0}, quality={1} -> {2}")
        @CsvSource({
            "10, 20, 18",
            "1, 20, 18",
            "1, 2, 0"
        })
        void shouldDecreaseQualityByTwo_beforeSellDate(int sellIn, int quality, int expected) {
            assertEquals(expected, updatedQuality(CONJURED, sellIn, quality));
        }

        @ParameterizedTest(name = "sellIn={0}, quality={1} -> {2}")
        @CsvSource({
            "0, 20, 16",
            "-1, 20, 16",
            "-10, 20, 16"
        })
        void shouldDecreaseQualityByFour_afterSellDate(int sellIn, int quality, int expected) {
            assertEquals(expected, updatedQuality(CONJURED, sellIn, quality));
        }

        @ParameterizedTest(name = "sellIn={0}, quality={1} -> 0")
        @CsvSource({
            "5, 0",
            "5, 1",
            "0, 3",
            "0, 0",
            "-1, 2"
        })
        void shouldNeverBeNegative(int sellIn, int quality) {
            assertEquals(0, updatedQuality(CONJURED, sellIn, quality));
        }

        @Test
        void shouldDegradeTwiceAsFastAsNormalItem() {
            assertEquals(
                2 * (20 - updatedQuality(NORMAL, 5, 20)),
                20 - updatedQuality(CONJURED, 5, 20));
            assertEquals(
                2 * (20 - updatedQuality(NORMAL, -1, 20)),
                20 - updatedQuality(CONJURED, -1, 20));
        }
    }
}
