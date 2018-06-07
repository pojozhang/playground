package gildedrose

import (
	. "github.com/onsi/ginkgo"
	. "github.com/onsi/gomega"
)

var _ = Describe("backstage item", func() {

	const (
		DefaultSellIn  = 12
		DefaultQuality = 5
	)

	Context("one day has passed", func() {
		It("sell-in should decrease by 1", func() {
			item := Create(BackstageItemName, DefaultSellIn, DefaultQuality)
			item.Change()
			Expect(item.GetSellIn()).To(Equal(DefaultSellIn - 1))
		})
	})

	Context("one day passed and sell by date has not passed", func() {
		It("quality should increase by 1", func() {
			item := Create(BackstageItemName, DefaultSellIn, DefaultQuality)
			item.Change()
			Expect(item.GetQuality()).To(Equal(DefaultQuality + 1))
		})
	})

	Context("one day passed and 6 - 10 days left", func() {
		It("quality should increase by 2", func() {
			item := Create(BackstageItemName, 10, DefaultQuality)
			item.Change()
			Expect(item.GetQuality()).To(Equal(DefaultQuality + 2))
		})
	})

	Context("one day passed and 1 - 5 days left", func() {
		It("quality should increase by 3", func() {
			item := Create(BackstageItemName, 5, DefaultQuality)
			item.Change()
			Expect(item.GetQuality()).To(Equal(DefaultQuality + 3))
		})
	})

	Context("one day passed and 0 day lefts", func() {
		It("quality upgrades twice as fast", func() {
			item := Create(BackstageItemName, 0, DefaultQuality)
			item.Change()
			Expect(item.GetQuality()).To(Equal(0))
		})
	})
})
