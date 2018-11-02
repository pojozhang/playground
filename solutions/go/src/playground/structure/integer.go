package structure

type Integer int

func (i Integer) compareTo(other interface{}) int {
	that := other.(Integer)
	return int(i - that)
}

func (i Integer) GreaterThan(other interface{}) bool {
	that := other.(Integer)
	return i > that
}

func (i Integer) LessThan(other interface{}) bool {
	that := other.(Integer)
	return i < that
}

func (i Integer) Equals(other interface{}) bool {
	that := other.(Integer)
	return i == that
}
