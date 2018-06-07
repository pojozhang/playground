package gildedrose

const BackstageItemName = "backstage"

type BackstageItem struct {
	NormalItem
}

func (i *BackstageItem) Change() {
	i.decreaseSellIn()
	i.increaseQuality()
	if 1 <= i.sellIn && i.sellIn <= 10 {
		i.increaseQuality()
	}
	if 1 <= i.sellIn && i.sellIn <= 5 {
		i.increaseQuality()
	}
	if i.sellIn <= 0 {
		i.quality = 0
	}
}

func init() {
	Register(BackstageItemName, func(sellIn, quality int) Item {
		return &BackstageItem{NormalItem{sellIn, quality}}
	})
}
