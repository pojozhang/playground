package structure

type Comparable interface {
	GreaterThan(interface{}) bool
	LessThan(interface{}) bool
	Equals(interface{}) bool
}
