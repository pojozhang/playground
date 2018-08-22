package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestToGoatLatinCase1(t *testing.T) {
	assert.Equal(t, "Imaa peaksmaaa oatGmaaaa atinLmaaaaa", toGoatLatin("I speak Goat Latin"))
}

func TestToGoatLatinCase2(t *testing.T) {
	assert.Equal(t, "heTmaa uickqmaaa rownbmaaaa oxfmaaaaa umpedjmaaaaaa overmaaaaaaa hetmaaaaaaaa azylmaaaaaaaaa ogdmaaaaaaaaaa", toGoatLatin("The quick brown fox jumped over the lazy dog"))
}
