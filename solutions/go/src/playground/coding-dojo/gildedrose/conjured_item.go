package gildedrose

const ConjuredItemName = "conjured"

type ConjuredItem struct {
	NormalItem
}

func (i *ConjuredItem) Change() {
	i.decreaseSellIn()
	for n := 0; n < 2; n++ {
		i.decreaseQuality()
	}
}

func init() {
	Register(ConjuredItemName, func(sellIn, quality int) Item {
		return &ConjuredItem{NormalItem{sellIn, quality}}
	})
}
