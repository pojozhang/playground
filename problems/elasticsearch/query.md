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

Match Phrase Query会对把输入的短语作为一个整体进行查询，并且短语中出现的单词的顺序也要匹配，比如"I like driving"可以匹配到"I like driving and reading"，不能匹配到"I like reading"。

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

如果我们希望"I like reading"可以匹配到"I like driving and reading"，可以使用`slot`参数，它表示短语中单词之间的间距的最大范围，比如`slot`是2，那么就表示两个单词之间最多允许有另外的2个单词。当`slot`是2时，"I like reading"就可以匹配到"I driving driving and reading"，这是因为文档中"reading"和"like"之间差了两个单词的距离。

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

- Multi Match Query

- Query String Query

- Prefix Query

- Regexp Query

- Fuzzy Query

- Wrapper Query

- Nested Query

- Exists Query

> 关于以上几种查询的使用，可以在[这里](https://github.com/pojozhang/playground/blob/master/solutions/java/src/test/java/playground/elasticsearch/QueryTest.java)查看示例代码。

## 参考

1. [《Elasticsearch Query DSL 整理总结（三）—— Match Phrase Query 和 Match Phrase Prefix Query》](https://www.cnblogs.com/reycg-blog/p/10012238.html)
