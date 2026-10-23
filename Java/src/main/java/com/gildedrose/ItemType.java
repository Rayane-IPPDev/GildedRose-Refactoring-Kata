package com.gildedrose;

enum ItemType {
    NORMAL {
        @Override
        void updateQuality(Item item) {
            int decrease = item.sellIn <= 0 ? 2 : 1;
            item.quality = Math.max(0, item.quality - decrease);
        }
    },
    AGED_BRIE {
        @Override
        void updateQuality(Item item) {
            int increase = item.sellIn <= 0 ? 2 : 1;
            item.quality = Math.min(50, item.quality + increase);
        }
    },
    SULFURAS {
        @Override
        void updateQuality(Item item) {
            // Objet légendaire : la qualité ne change jamais
        }

        @Override
        void updateSellIn(Item item) {
            // Objet légendaire : pas de date de péremption
        }
    },
    BACKSTAGE {
        @Override
        void updateQuality(Item item) {
            if (item.sellIn <= 0) {
                item.quality = 0;
                return;
            }

            int increase = 1;
            if (item.sellIn <= 5) {
                increase = 3;
            } else if (item.sellIn <= 10) {
                increase = 2;
            }
            item.quality = Math.min(50, item.quality + increase);
        }
    },
    CONJURED {
        @Override
        void updateQuality(Item item) {
            int decrease = item.sellIn <= 0 ? 4 : 2;
            item.quality = Math.max(0, item.quality - decrease);
        }
    };

    abstract void updateQuality(Item item);

    void updateSellIn(Item item) {
        item.sellIn = item.sellIn - 1;
    }

    static ItemType from(String name) {
        return switch (name) {
            case "Aged Brie" -> AGED_BRIE;
            case "Sulfuras, Hand of Ragnaros" -> SULFURAS;
            case "Backstage passes to a TAFKAL80ETC concert" -> BACKSTAGE;
            case "Conjured" -> CONJURED;
            default -> NORMAL;
        };
    }
}
