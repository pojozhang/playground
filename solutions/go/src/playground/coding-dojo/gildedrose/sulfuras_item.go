package gildedrose

const SulfurasItemName = "sulfuras"

type SulfurasItem struct {
	NormalItem
}

func (i *SulfurasItem) Change() {
	i.decreaseSellIn()
}

func init() {
	Register(SulfurasItemName, func(sellIn, quality int) Item {
		return &SulfurasItem{NormalItem{sellIn, quality}}
	})
}
