package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestReverseStringCase1(t *testing.T) {
	assert.Equal(t, "olleh", reverseString("hello"))
}

func TestReverseStringCase2(t *testing.T) {
	assert.Equal(t, "amanaP :lanac a ,nalp a ,nam A", reverseString("A man, a plan, a canal: Panama"))
}
