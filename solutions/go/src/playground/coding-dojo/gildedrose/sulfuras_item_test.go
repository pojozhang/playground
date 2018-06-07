package gildedrose

import (
	. "github.com/onsi/ginkgo"
	. "github.com/onsi/gomega"
)

var _ = Describe("sulfuras item", func() {

	const (
		DefaultSellIn  = 2
		DefaultQuality = 5
	)

	Context("one day has passed", func() {
		It("sell-in should decrease by 1", func() {
			item := Create(SulfurasItemName, DefaultSellIn, DefaultQuality)
			item.Change()
			Expect(item.GetSellIn()).To(Equal(DefaultSellIn - 1))
		})
	})

	Context("one day passed", func() {
		It("quality should not change", func() {
			item := Create(SulfurasItemName, DefaultSellIn, DefaultQuality)
			item.Change()
			Expect(item.GetQuality()).To(Equal(DefaultQuality))
		})
	})
})
