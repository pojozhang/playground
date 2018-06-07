package gildedrose

import (
	. "github.com/onsi/ginkgo"
	. "github.com/onsi/gomega"
)

var _ = Describe("normal item", func() {

	const (
		NormalItemName = "normal"
		DefaultSellIn  = 2
		DefaultQuality = 5
	)

	Context("one day has passed", func() {
		It("sell-in should decrease by 1", func() {
			item := Create(NormalItemName, DefaultSellIn, DefaultQuality)
			item.Change()
			Expect(item.GetSellIn()).To(Equal(DefaultSellIn - 1))
		})
	})

	Context("one day passed and sell by date has not passed", func() {
		It("quality should decrease by 1", func() {
			item := Create(NormalItemName, DefaultSellIn, DefaultQuality)
			item.Change()
			Expect(item.GetQuality()).To(Equal(DefaultQuality - 1))
		})
	})

	Context("one day passed and sell by date has not passed", func() {
		It("quality is never negative", func() {
			item := Create(NormalItemName, DefaultSellIn, 0)
			item.Change()
			Expect(item.GetQuality()).To(Equal(0))
		})
	})

	Context("sell by date has passed", func() {
		It("quality degrades twice as fast", func() {
			item := Create(NormalItemName, 0, DefaultQuality)
			item.Change()
			Expect(item.GetQuality()).To(Equal(DefaultQuality - 1*2))
		})
	})
})
