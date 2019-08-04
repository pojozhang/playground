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

对于未分词的字段，Term Query要求查询的目标值和文档中字段的值完全一致。比如下面这个查询，查询的目标值是"hello world"，当且仅当文档的`keyword_field`字段的值是"hello world"时才能进行匹配。

```json
// 文档
{
    "keyword_field": "hello world"
}

// 查询
{
    "query": {
        "term" : { "keyword_field" : "hello world" }
    }
}
```

对于分词的字段，Term Query要求字段进行分词后的值至少有一个和要查询的目标值完全一致。下面的查询是无法查到结果的，这是因为`text_field`分词后得到的是"hello"和"world"，不包含目标值"hello world"，因此不匹配。

```json
// 文档
{
    "text_field": "hello world"
}

// 查询
{   "query": {
        "term" : { "keyword_field" : "hello world" }
    }
}
```

如果我们把查询的目标值改成"hello"就可以查出该条数据。

```json
{   "query": {
        "term" : { "keyword_field" : "hello" }
    }
}
```

- Wildcard Query

Wildcard Query支持通配符，`?`匹配一个字符，`*`匹配0个或多个字符。

以下查询可以匹配到结果。

```json
// 文档
{
    "keyword_field": "I like driving"
}

// 查询
{
    "query": {
        "wildcard" : { "keyword_field" : "I lik*" }
    }
}
```

以下查询也可以匹配到结果。

```json
// 文档
{
    "text_field": "I like driving"
}

// 查询
{
    "query": {
        "wildcard" : { "text_field" : "lik*" }
    }
}
```

但是下面这个查询查不到结果，这是因为Wildcard Query**不会**对查询词进行分词，而`text_field`字段的值会被解析成"I"、"like"、"driving"，都无法和"I lik*"匹配。

```json
// 文档
{
    "text_field": "I like driving"
}

// 查询
{
    "query": {
        "wildcard" : { "text_field" : "I lik*" }
    }
}
```

- Match Query

Match Query会在查询之前对目标词进行分词，比如用"hello world"可以匹配到"hello, my world"，这是因为"hello world"进行分词后会被分为"hello"和"world"。需要注意的是文档中只需要包含一个经过分词后的搜索关键字就能匹配查询，比如"hello god"也可以匹配到"hello, my world"，尽管后者不包含"god"。

```json
// 文档
{
    "text_field": "hello, my world"
}

// 查询
{
    "query": {
        "match" : { "text_field" : "hello world" }
    }
}
```

- Match Phrase Query

Match Phrase Query会对把输入的短语作为一个整体进行查询，查询短语中所有的单词都包含，并且单词的顺序也相同的文档才会匹配，比如"I like driving"可以匹配到文档"I like driving and reading"，不能匹配到"I like reading"。

```json
// 文档
{
    "text_field": "I like driving and reading"
}

// 查询
{
    "query": {
        "match_phrase" : { "text_field" : "I like driving" }
    }
}
```

如果我们希望"I like reading"可以匹配到"I like driving and reading"，可以使用`slop`参数，它表示短语中单词之间的间距的最大范围，比如`slop`是2，那么就表示两个单词之间最多允许有另外的2个单词。当`slop`是2时，"I like reading"就可以匹配到"I driving driving and reading"，这是因为文档中"reading"和"like"之间差了两个单词的距离。`slop`的默认值是0。

```json
// 查询
{
  "query": {
    "match_phrase": {
      "text_field": {
        "query": "I like reading",
        "slop": 2
      }
    }
  }
}
```

- Match Phrase Prefix Query

Match Phrase Prefix Query和Match Phrase Query类似，只是对于短语中的最后一个单词可以进行前缀匹配，比如"I like d"可以匹配到"I like driving and reading"。该查询方式同样支持`slop`参数，当`slop`设置为2时，"I like r"可以匹配到"I like driving and reading"。

- Multi Match Query



- Query String Query

- Prefix Query

- Regexp Query

- Fuzzy Query

- Wrapper Query

> 关于以上几种查询的使用，可以在[这里](https://github.com/pojozhang/playground/blob/master/solutions/java/src/test/java/playground/elasticsearch/QueryTest.java)查看示例代码。

## 参考

1. [《Elasticsearch Query DSL 整理总结（三）—— Match Phrase Query 和 Match Phrase Prefix Query》](https://www.cnblogs.com/reycg-blog/p/10012238.html)
