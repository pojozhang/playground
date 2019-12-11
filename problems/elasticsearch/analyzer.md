# 分词器

可以使用以下接口查看各个分词器的分词效果。

```shell
GET _analyze
{
  "analyzer": "分词器名称",
  "text": "待分词的字符串"
}
```

## 内置分词器

### Standard

ES的默认分词器，根据单词进行切分，并把切分后的单词转为小写，停词不会被过滤。

### Simple

会把字符串中所有非字母的字符去掉，并按单词进行切分，停词不会被过滤。

### Whitespcae

根据空格进行切分。

### Stop

在Simple分词器的基础上加入停词过滤功能。

### Keyword

不对结果进行分词。

### Pattern

根据正则表达式进行分词。

## 自定义分词器

一个分词器包含以下几个组件：

1. Character Filter

对输入的文本进行预处理，然后把处理后的文本交给Tokenizer进行分词。

常见的Character Filter有：

- HTML Strip Character Filter：过滤文本中的HTML标签。
- Mapping Character Filter：把源文本替换成目标文本。
- Pattern Replace Character Filter：通过正则表达式替换文本。

2. Tokenizer

对文本进行切分，输出字符数组，交给Token Filter。

常见的Tokenizer有：

- Standard Tokenizer：根据单词界线进行切分，并且会去掉文本中的标点符号，比如“hello world!”会被切分成“hello”和“world”。
- Letter Tokenizer：根据非字母的字符进行切分，比如“hello,world”会被切分成“hello”和“world”。
- Lowercase Tokenizer：在Letter Tokenizer的基础上把所有字符转成小写。
- Whitespace Tokenizer：根据空格进行切分。

3. Token Filter

对Tokenizer切分后的字符数组进一步处理。

常见的Token Filter有：

- Stop Token Filter：过滤掉停词。
- Lowercase Token Filter：把字符转成小写。
- Uppercase Token Filter：把字符转成大写。

通过以下命令可以测试自定义分词器的效果：

```shell
GET _analyze
{
  "tokenizer": "$tokenizer",
  "char_filter": [$char_filter_array],
  "filter": [$token_filter_array],
  "text": "$text"
}
```

通过设置索引的settings可以创建总定义分词器，在下面的例子中我们分别配置了Tokenizer、Char Filter和Token Filter，并将他们组成一个新的自定义分词器my_custom_analyzer。

```shell
PUT test_index
{
  "settings": {
    "analysis": {
      "analyzer": {
        "my_custom_analyzer": {
          "type": "custom",
          "char_filter": [
            "emoticons"
          ],
          "tokenizer": "punctuation",
          "filter": [
            "lowercase",
            "english_stop"
          ]
        }
      },
      "tokenizer": {
        "punctuation": {
          "type": "pattern",
          "pattern": "[ .,!?]"
        }
      },
      "char_filter": {
        "emoticons": {
          "type": "mapping",
          "mappings": [
            ":) => happy",
            ":( => sad"
          ]
        }
      },
      "filter": {
        "english_stop": {
          "type": "stop",
          "stopwords": "and"
        }
      }
    }
  }
}
```

通过以下语句可以测试自定义分词器的效果。

```shell
GET test_index/_analyze
{
  "analyzer": "my_custom_analyzer",
  "text": "I'm a :) person, and you?"
}
```

运行结果如下：

```json
{
  "tokens" : [
    {
      "token" : "i'm",
      "start_offset" : 0,
      "end_offset" : 3,
      "type" : "word",
      "position" : 0
    },
    {
      "token" : "a",
      "start_offset" : 4,
      "end_offset" : 5,
      "type" : "word",
      "position" : 1
    },
    {
      "token" : "happy",
      "start_offset" : 6,
      "end_offset" : 8,
      "type" : "word",
      "position" : 2
    },
    {
      "token" : "person",
      "start_offset" : 9,
      "end_offset" : 15,
      "type" : "word",
      "position" : 3
    },
    {
      "token" : "you",
      "start_offset" : 21,
      "end_offset" : 24,
      "type" : "word",
      "position" : 5
    }
  ]
}
```

可以看到分词后“and”被去掉了，这是因为我们把停词设置成了“and”。
