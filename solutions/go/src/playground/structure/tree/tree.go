package tree

import . "playground/structure"

type Tree interface {
	Add(Comparable)
	Remove(Comparable)
}
