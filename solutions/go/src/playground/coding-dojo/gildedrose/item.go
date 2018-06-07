package gildedrose

type Item interface {
	GetSellIn() int
	GetQuality() int
	Change()
}

var initializers = map[string]func(sellIn, quality int) Item{}

func Register(name string, initializer func(sellIn, quality int) Item) {
	initializers[name] = initializer
}

func Create(name string, sellIn, quality int) Item {
	initializer, ok := initializers[name]
	if !ok {
		initializer = initializers["default"]
	}
	return initializer(sellIn, quality)
}
