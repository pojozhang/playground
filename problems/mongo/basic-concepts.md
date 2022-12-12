# 基本概念

## 数据类型

1. null：表示空值或不存在的字段
2. 布尔：true、false
3. 数值：
   1. 64位浮点数，例如`{"x": 3.14}`
   2. 4字节整数，例如`{"x": NumberInt("3")}`
   3. 8字节整数，例如`{"x": NumberLong("3")}`
4. 字符串：任何UTF-8字符串
5. 日期：Monog会把日期存储为64位整数，表示1970年1月1日以来的毫秒数，不包含时区信息，例如`{"x": new Date()}`
6. 正则表达式：查询时可以使用正则表达式，例如`{"x": /foobar/i}`
7. 数组：集合或列表可表示为数组，数组中的元素可以是任意类型，甚至可以是不同的类型
8. 内嵌文档：支持文档嵌套，例如`{"x": {"foo": "bar"}}`
9. Object ID：12字节的ID，是文档的唯一标识，例如`{"x": ObjectId()}`
10. 二进制数据：任意字节的字符串，无法通过mongo shell操作
11. Javascript代码：例如`{"x": function(){ /* ... */ }}`

## 查询操作

### null

匹配键的值是null的文档，但如果文档不存在该键也会匹配到。

`db.col.findOne({"age": null})`

如果需要仅想匹配键的值是null的文档，那么需要用`$exists`进行过滤。

`db.col.findOne({"age": {"$eq": null, "$exists": true}})`

### $in

用来查询一个键的多个值。

`db.col.findOne({"age": {"$in":[1, 5, 10]}})`

## 更新操作

### $set

用来设置一个字段的值，如果字段不存在就创建该字段。

`db.col.updateOne({"_id": ""}, {$set: {"foo": "bar"}})`

### $push

将元素添加到数组末尾，如果数组不存在那么会创建一个数组。

`db.col.updateOne({"_id": ""}, {$push: {"foo": "bar"}})`

配合`$each`、`$slice`、`$sort`可以实现topN列表的效果。

```json
db.col.updateOne({
	"_id": ""
}, {
	$push: {
		"top10": {
			"$each": [{
					"name": "a",
					"score": 1
				},
				{
					"name": "b",
					"score": 2
				}
			],
			"$slice": -10,
			"$sort": {
				"score": -1
			}
		}
	}
})
```

### $addToSet

如果希望把数组当作集合来用，即添加到数组中的元素不重复，那么可以使用`$addToSet`。

### $pop

用来从数组的头部或尾部删除元素。

`db.col.updateOne({"_id": ""}, {$pop: {"foo": 1}})`

### $pull

用于删除数组中所有匹配的元素。

`db.col.updateOne({"_id": ""}, {$pull: {"foo": "bar"}})`
