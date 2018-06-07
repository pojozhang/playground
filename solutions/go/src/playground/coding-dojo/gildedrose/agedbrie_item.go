package gildedrose

const AgedBrieItemName = "aged brie"

type AgedBrieItem struct {
	NormalItem
}

func (i *AgedBrieItem) Change() {
	i.decreaseSellIn()
	i.increaseQuality()
	if i.sellIn < 0 {
		i.increaseQuality()
	}
}

func init() {
	Register(AgedBrieItemName, func(sellIn, quality int) Item {
		return &AgedBrieItem{NormalItem{sellIn, quality}}
	})
}
