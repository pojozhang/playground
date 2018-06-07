package gildedrose

import (
	. "github.com/onsi/ginkgo"
	. "github.com/onsi/gomega"
)

var _ = Describe("conjured item", func() {

	const (
		DefaultSellIn  = 2
		DefaultQuality = 5
	)

	Context("one day has passed", func() {
		It("sell-in should decrease by 1", func() {
			item := Create(ConjuredItemName, DefaultSellIn, DefaultQuality)
			item.Change()
			Expect(item.GetSellIn()).To(Equal(DefaultSellIn - 1))
		})

		It("quality should decrease by 2", func() {
			item := Create(ConjuredItemName, DefaultSellIn, DefaultQuality)
			item.Change()
			Expect(item.GetQuality()).To(Equal(DefaultQuality - 2))
		})

		It("quality is never negative", func() {
			item := Create(ConjuredItemName, DefaultSellIn, 0)
			item.Change()
			Expect(item.GetQuality()).To(Equal(0))
		})
	})
})
