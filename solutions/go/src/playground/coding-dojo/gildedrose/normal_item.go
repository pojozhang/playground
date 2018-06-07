package gildedrose

const MaxQuality = 50

type NormalItem struct {
	sellIn  int
	quality int
}

func (i *NormalItem) GetSellIn() int {
	return i.sellIn
}

func (i *NormalItem) GetQuality() int {
	return i.quality
}

func (i *NormalItem) decreaseSellIn() {
	i.sellIn--
}

func (i *NormalItem) decreaseQuality() {
	if i.quality > 0 {
		i.quality--
	}
}

func (i *NormalItem) increaseQuality() {
	if i.quality < MaxQuality {
		i.quality++
	}
}

func (i *NormalItem) Change() {
	i.decreaseSellIn()
	i.decreaseQuality()
	if i.sellIn < 0 {
		i.decreaseQuality()
	}
}

func init() {
	Register("default", func(sellIn, quality int) Item {
		return &NormalItem{sellIn, quality}
	})
}
