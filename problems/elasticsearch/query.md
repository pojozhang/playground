# 查询

首先我们建立一个索引方便讲解，索引对应的mappings如下所示。

```json
{
    "query_test_index": {
        "mappings": {
            "properties": {
                "keyword_field": {
                    "type": "keyword"
                },
                "text_field": {
                    "type": "text",
                    "analyzer": "standard"
                }
            }
        }
    }
}
```

- Term Query

对于未分词的字段，Term Query要求字段的值和要查询的目标值完全一致。比如下面这个查询，当`keyword_field`的值是"hello world"时，查询中的目标值也必须是"hello world"才能进行匹配。

```json
// 数据
{
    "keyword_field": "hello world"
}

// 查询
"query": {
    "term" : { "keyword_field" : "hello world" }
}
```

对于分词的字段，Term Query要求字段进行分词后的值至少有一个和要查询的目标值完全一致。下面的查询是无法查到结果的，这是因为`text_field`分词后得到的是"hello"和"world"，不包含目标值"hello world"，因此不匹配。

```json
// 数据
{
    "text_field": "hello world"
}

// 查询
"query": {
    "term" : { "keyword_field" : "hello world" }
}
```

如果我们把查询的目标值改成"hello"就可以查出该条数据。

```json
"query": {
    "term" : { "keyword_field" : "hello" }
}
```

- Match Query

- Match Phrase Query

- Match Phrase Prefix Query

- Multi Match Query

- Wildcard Query

- Query String Query

- Prefix Query

- Regexp Query

- Fuzzy Query

- Wrapper Query

- Nested Query

- Exists Query

> 关于以上几种查询的使用，可以在[这里](https://github.com/pojozhang/playground/blob/master/solutions/java/src/test/java/playground/elasticsearch/QueryTest.java)查看示例代码。
