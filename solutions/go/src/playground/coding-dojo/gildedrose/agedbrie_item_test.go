package gildedrose

import (

	. "github.com/onsi/ginkgo"
	. "github.com/onsi/gomega"
)

var _ = Describe("aged brie item", func() {

	const (
		DefaultSellIn    = 2
		DefaultQuality   = 5
	)

	Context("one day has passed", func() {
		It("sell-in should decrease by 1", func() {
			item := Create(AgedBrieItemName, DefaultSellIn, DefaultQuality)
			item.Change()
			Expect(item.GetSellIn()).To(Equal(DefaultSellIn - 1))
		})
	})

	Context("one day passed and sell by date has not passed", func() {
		It("quality should increase by 1", func() {
			item := Create(AgedBrieItemName, DefaultSellIn, DefaultQuality)
			item.Change()
			Expect(item.GetQuality()).To(Equal(DefaultQuality + 1))
		})
	})

	Context("one day passed and sell by date has not passed", func() {
		It("quality should be never more than 50", func() {
			item := Create(AgedBrieItemName, DefaultSellIn, MaxQuality)
			item.Change()
			Expect(item.GetQuality()).To(Equal(MaxQuality))
		})
	})

	Context("sell by date has passed", func() {
		It("quality upgrades twice as fast", func() {
			item := Create(AgedBrieItemName, 0, DefaultQuality)
			item.Change()
			Expect(item.GetQuality()).To(Equal(DefaultQuality + 1*2))
		})
	})
})
