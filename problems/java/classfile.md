# 类文件结构

在学习类文件结构之前我们先编写一个简单的类。

```java
package playground;

public class Test {

    private int m;

    public int inc() {
        return m + 1;
    }
}
```

对以上的类使用`javac`进行编译后我们得到一个`.class`文件，它的十六进制表示如下。

```
CA FE BA BE 00 00 00 36 00 13 0A 00 04 00 0F 09 00 03 00 10 07 00 11 07 00 12 01 00 01 6D 01 00 01 49 01 00 06 3C 69 6E 69
74 3E 01 00 03 28 29 56 01 00 04 43 6F 64 65 01 00 0F 4C 69 6E 65 4E 75 6D 62 65 72 54 61 62 6C 65 01 00 03 69 6E 63 01 00
03 28 29 49 01 00 0A 53 6F 75 72 63 65 46 69 6C 65 01 00 09 54 65 73 74 2E 6A 61 76 61 0C 00 07 00 08 0C 00 05 00 06 01 00
0F 70 6C 61 79 67 72 6F 75 6E 64 2F 54 65 73 74 01 00 10 6A 61 76 61 2F 6C 61 6E 67 2F 4F 62 6A 65 63 74 00 21 00 03 00 04
00 00 00 01 00 02 00 05 00 06 00 00 00 02 00 01 00 07 00 08 00 01 00 09 00 00 00 1D 00 01 00 01 00 00 00 05 2A B7 00 01 B1
00 00 00 01 00 0A 00 00 00 06 00 01 00 00 00 03 00 01 00 0B 00 0C 00 01 00 09 00 00 00 1F 00 02 00 01 00 00 00 07 2A B4 00
02 04 60 AC 00 00 00 01 00 0A 00 00 00 06 00 01 00 00 00 08 00 01 00 0D 00 00 00 02 00 0E
```

## 结构

从整体上看，一个Class文件结构如下。

| 长度（字节） | 4 | 2 | 2 | 2 | 不定 | 2 | 2 | 2 | 2 | 2 | 2 | 不定 | 2 | 不定 | 2 | 不定 |
| ---------- | - | - | - | - | --- | - | - | - | - | - | - | ---- | - | --- | - | --- |
| 名称 | magic | minor_version | major_version | constant_pool_count | constant_pool | access_flags | this_class | super_class | interfaces_count | interfaces | fields_count | fields | methods_count | methods | attributes_count | attributes |

类文件非常紧凑，每个数据项之间没有分隔符。数据项有2种类型，一种是无符号数，另一种称为表。无符号数可以表示数字或者UTF-8编码的字符串，比如上面的`magic`项，就是一个占4个字节的无符号数；表是由多个无符号数和其它表组成的复合结构数据。表中的数据项数量通常是不定的，通常在表前面有一个前置的计数器记录表中数据项的个数，比如上图中的`constant_pool`表，它的数据项数量记录在前面的`constant_pool_count`中。

## 魔数（magic）

魔数是指开头的4个字节`CA FE BA BE`，它的唯一作用是用来表示这是一个Class文件。

## 版本（minor_version、major_version）

之后的4个字节`minor_version`和`major_version`分别是次版本号和主版本号，表示Class文件的版本，版本号从45.0开始。例子中的版本是`0x00000036`，也就是十进制的`54`，也就是JDK10。

## 常量池（constant_pool_count、constant_pool）

版本号之后的2个字节代表常量池的容量，这里是`0x0013`，即十进制的`19`。比较特殊的是，这里的计数是从1开始的，实际数据项数目需要在计数上减去1，因此这里的19表示常量池中有18个常量。当计数为0时，则有特殊含义，表示不引用任何一个常量池项目。

常量池中的每一项都是一个表，目前一共有十几种不同的数据结构类型，它们的通用结构如下。

| 长度（字节）| 1 | len1 | ... | lenN |
| --------- | - | ---- | --- | ---- |
| 名称      | tag | property1 | ... | propertyN |

比如`CONSTANT_Class_info`类型的结构如下。

| 长度（字节）| 1 | 1 |
| --------- | - | - |
| 名称      | tag | name_index |

每一个类型开头的第一个字节都是`tag`，代表当前常量的类型，比如上面的`CONSTANT_Class_info`类型，它的`tag`就是7，除了`tag`外，后面的结构每种类型都有不同。下面列出部分类型和`tag`的关系。

|                  类型                 |   标志   |
| ------------------------------------ | -------- | 
| CONSTANT_Utf8_info                   |    1     |
| CONSTANT_Integer_info                |    3     |
| CONSTANT_Float_info                  |    4     |
| CONSTANT_Long_info                   |    5     |
| CONSTANT_Double_info                 |    6     |
| CONSTANT_Class_info                  |    7     |
| CONSTANT_String_info                 |    8     |
| CONSTANT_Fieldref_info               |    9     |
| CONSTANT_Methodref_info              |    10    |
| CONSTANT_InterfaceMethodref_info     |    11    |
| CONSTANT_NameAndType_info            |    12    |


在`0x0013`之后紧跟着的字节是`0x0A`，即十进制的`10`，按照上面表格中的对应关系，我们可以看到第一个常量的类型是`CONSTANT_Methodref_info`，它的结构如下。

| 长度 |  1  |                 2                  |                  2                  |
| --- | --- | ---------------------------------- | ----------------------------------- |
| 名称 | tag |               index                |               index                 |
| 描述 | 10  | 指向CONSTANT_Class_info类型常量的索引 | 指向CONSTANT_NameAndType类型常量的索引 |

因此后面的两个值分别是`0x0004`和`0x000F`，即十进制的`4`和`15`。

第4个常量的十六进制是`07 00 12`（这里的索引是从1开始的，1就是指第1个常量而不是第0个常量），因此它是`CONSTANT_Class_info`类型的常量。

`CONSTANT_Class_info`类型的结构如下。

| 长度 |  1  |         2           |
| --- | --- | ------------------- |
| 名称 | tag |        index        |
| 描述 | 7  | 指向全限定名常量项的索引 |

因此`0x0012`，即十进制的`18`。第18个常量的十六进制是`01 00 10 6A 61 76 61 2F 6C 61 6E 67 2F 4F 62 6A 65 63 74`，它的标志是`0x01`，即十进制的`1`，因此是一个`CONSTANT_Utf8_info`类型的常量，该类型的结构如下。

| 长度 |  1  |            2              |            length            |
| --- | --- | ------------------------- | ---------------------------- |
| 名称 | tag |          length           |            bytes             |
| 描述 |  1  | UTF-8编码的字符串占用的字节数 | 长度为length的UTF-8编码的字符串 |

所以该字符串的字节数是`0x0010`，即十进制的`16`，内容是`6A 61 76 61 2F 6C 61 6E 67 2F 4F 62 6A 65 63 74`，用UTF-8解码后就是`java/lang/Object`。

我们再来看第15个常量，它的十六进制是`0C 00 07 00 08`，`0x0C`的十进制是`12`，因此它是一个`CONSTANT_NameAndType_info`类型的常量，其结构如下。

| 长度 |  1  |            2              |              2             |
| --- | --- | ------------------------- | -------------------------- |
| 名称 | tag |          index            |            index           |
| 描述 |  12 | 指向该字段或方法名称常量的索引 | 指向该字段或方法描述符常量的索引 |

后面的两个数据项的值`0x0007`和`0x0008`分别是十进制的`7`和`8`。第7和第8个常数项的十六进制表示分别是`01 00 06 3C 69 6E 69 74 3E`和`01 00 03 28 29 56`，两者都是`CONSTANT_Utf8_info`类型的常量，它们的字符串值分别是`<init>`和`()V`。

综上，我们把这个`CONSTANT_Methodref_info`类型的变量连起来看，就是`java/lang/Object<init>()V`。

常量池中其余的常量也可以按照上面分析的步骤计算出来，JDK自带了一个工具`javap`可以帮我们自动解析，最后的结果如下。

```java
// 编译后执行 javap -v Test。
Constant pool:
   #1 = Methodref          #4.#15         // java/lang/Object."<init>":()V
   #2 = Fieldref           #3.#16         // playground/Test.m:I
   #3 = Class              #17            // playground/Test
   #4 = Class              #18            // java/lang/Object
   #5 = Utf8               m
   #6 = Utf8               I
   #7 = Utf8               <init>
   #8 = Utf8               ()V
   #9 = Utf8               Code
  #10 = Utf8               LineNumberTable
  #11 = Utf8               inc
  #12 = Utf8               ()I
  #13 = Utf8               SourceFile
  #14 = Utf8               Test.java
  #15 = NameAndType        #7:#8          // "<init>":()V
  #16 = NameAndType        #5:#6          // m:I
  #17 = Utf8               playground/Test
  #18 = Utf8               java/lang/Object
```

## 访问标志（access_flags）

之后的2个字节是`00 21`，表示访问标志。访问标志共有8个，如下所示。

|      名称       |       值     |                         描述                                |  
| -------------- | ------------ | ---------------------------------------------------------- |
| ACC_PUBLIC	 | 0x0001	    | 是否为Public类型                                             |
| ACC_FINAL    	 | 0x0010	    | 是否被声明为final，只有类可以设置                               |
| ACC_SUPER  	 | 0x0020	    | 是否允许使用invokespecial字节码指令的新语义                      |
| ACC_INTERFACE	 | 0x0200	    | 标志这是一个接口                                               |
| ACC_ABSTRACT	 | 0x0400	    | 是否为abstract类型，对于接口或者抽象类来说，次标志值为真，其他类型为假 |
| ACC_SYNTHETIC	 | 0x1000	    | 标志这个类并非由用户代码产生                                     |
| ACC_ANNOTATION | 0x2000	    | 标志这是一个注解                                               |
| ACC_ENUM   	 | 0x4000	    | 标志这是一个枚举                                               |

本例中`Test`类的标志是`ACC_PUBLIC`|`ACC_SUPER`，也就是`0x0001`|`0x0020`，等于`0x0021`。

## 类索引（this_class）

类索引用于确定当前类的全限定名，它指向一个`CONSTANT_Class_info`类的常量，本例中它的值是`0x0003`，也就是十进制的`3`，第3个常量的值是`07 00 11`，它继续指向一个`CONSTANT_Utf8_info`的常量，它的值是`01 00 0F 70 6C 61 79 67 72 6F 75 6E 64 2F 54 65 73 74`，其字符串表示是`playground/Test`。

## 父类索引（super_class）

父类索引用于确定当前类的父类的全限定名，除了`java.lang.Object`之外所有的类都有父类。在本例中父类索引的值是`0x0004`，也就是十进制的`4`，第4哥常量的值是`07 00 12`，它继续指向一个`CONSTANT_Utf8_info`的常量，它的值是`01 00 10 6A 61 76 61 2F 6C 61 6E 67 2F 4F 62 6A 65 63 74`，其字符串表示是`java/lang/Object`。

## 接口索引集合（interfaces_count、interfaces）

接口索引集合用来描述当前类实现了哪些接口。本例中的类没有实现接口，因此接口计数器部分（interfaces_count）的值为`0`（`0x0000`）。

## 字段表集合（fields_count，fields）

字段表用于描述接口或类中的变量。变量可以是静态变量或成员变量，但不包括方法的局部变量。字段表计数器（fields_count）表示字段表中包含多少个字段，本例中的值是`0x0001`，也就是1个。

一个字段表的结构如下所示。

|长度（字节）|      2       |      2     |        2         |        2         |     不定    | 
| -------- | ------------ | ---------- | ---------------- | ---------------- | ---------- |
|   名称    | access_flags | name_index | descriptor_index | attributes_count | attributes |

- access_flags

访问标志，比如`ACC_PUBLIC(0x0001)`表示字段是否是公开的，`ACC_PRIVATE(0x0002)`表示字段是否是私有的等。本例中该数据项的值是`0x0002`，表示私有字段。

- name_index

指向一个`CONSTANT_Utf8_info`类型的常量，代表字段的简单名称。简单名称是指仅有字段的名字，而不包括返回值、修饰符等内容。本例中该数据项的值是`0x0005`，指向第5个常量`01 00 01 6D`，它的字符串表示是`m`。
- descriptor_index

指向一个`CONSTANT_Utf8_info`类型的常量，代表字段的描述符。本例中该数据项的值是`0x0006`，指向第6哥常量`01 00 01 49`，它的字符串表示是`I`。

- attributes_count

字段可以包含一些额外的属性，`attributes_count`是属性计数器，表示属性的个数，本例中没有额外的属性，因此`attributes_count`的值是`0x0000`，即`0`。

- attributes

字段包含的额外的属性，本例中没有额外的属性。

## 描述符

上面多次提到了描述符，那么什么是描述符呢？

描述符的作用是用来描述字段的数据类型、方法的参数列表（包括数量、类型以及顺序）和返回值。根据规则，基本数据类型使用一个大写字母来表示，如下所示。

| 字符 |   类型  |
| --- | --------|
|  B  | byte    |
|  C  | char    |
|  D  | double  |
|  F  | float   |
|  I  | int     |
|  J  | long    |
|  S  | short   |
|  Z  | boolean |
|  V  | void    |

对于对象类型则用字符`L`加上对象的全限定名来表示，比如`Ljava/lang/Object`，并且加上一个分号表示表示全限定名结束。对于数组类型，每多一个维度就用一个`[`字符描述，比如`String[][]`类型的描述符是`[[Ljava/lang/String;`，`int[]`类型的描述符是`[I`。

上面是描述字段的方式，当我们用描述符来描述方法时，按照先参数列表后返回值的顺序进行描述，参数列表放在一对括号中，比如本例中方法`inc()`的描述符是`()I`，由于没有入参，因此括号中为空，括号后的`I`表示返回值是`int`类型。又比如`Integer valueOf(String s, int radix)`方法，它的描述符是`(Ljava/lang/String;I)Ljava/lang/Integer;`。

## 方法表集合（methods_count，methods）

方法表和上面的字段表类似，只不过是用来表述方法的。方法表计数器（methods_count）表示方法表中方法的数量，这里的值是`0x0002`，表示有`2`个方法。

下面，我们来看下方法表的结构。

|长度（字节）|      2       |      2     |        2         |        2         |     不定    | 
| -------- | ------------ | ---------- | ---------------- | ---------------- | ---------- |
|   名称    | access_flags | name_index | descriptor_index | attributes_count | attributes |

从结构上看它和字段表完全一致，每个字段的含义也是相同的。

我们先来看方法表中的第一个方法，它各个数据项如下所示。

- access_flags

访问标志，和字段表的区别是值不同，比如方法不能被`volatile`修饰，因此没有`ACC_VOLATILE`标志，但是针对方法有`ACC_SYNCHRONIZED`标志表示`synchronized`而字段没有。本例中`access_flags`的值是`0x0001`，表示`public`。

- name_index

名称索引。本例中`name_index`的值是`0x0007`，指向第7个常量，它的值是`01 00 06 3C 69 6E 69 74 3E`，是一个`CONSTANT_Utf8_info`类型的常量，其字符串表示是`<init>`。

- descriptor_index

描述符索引，本例中的值是`0x0008`，指向第8个常量，它的值是`01 00 03 28 29 56`，是一个`CONSTANT_Utf8_info`类型的常量，其字符串表示是`()V`。

- attributes_count

属性计数器。这里的值是`0x0001`，表示有一个属性。

- attributes

属性表。这里存放的是各个属性名称的索引，这里的值是`0x0009`，指向第9个常量，它的值是`01 00 04 43 6F 64 65`，是一个`CONSTANT_Utf8_info`类型的常量，其字符串表示是`Code`，也就是说该方法有一个名为`Code`的属性。关于属性的介绍会在下面具体展开。

### CONSTANT_Fieldref_info类型常量和方法表

既然我们已经有了方法表，那么常量池中`CONSTANT_Fieldref_info`类型的常量有什么用呢？是不是有点多余呢？

实际上这两者的用处是不同的，方法表记录的是字段本身的信息，而只有当我们引用到某个字段时才会使用到`CONSTANT_Fieldref_info`类型的常量。那么什么时候才算引用到该字段呢？比如`Test`类中的`inc()`方法就引用到了字段`m`，最常见的还有各个字段的`Getter`、`Setter`方法。

```java
public int inc() {
    return m + 1;
}
```

## 属性表

在字段表和方法表都出现了属性表的概念，下面我们看一下属性表到底是什么。

属性表用于描述一些额外的信息，各个属性之间不严格有序，只要不与已有的属性重名，任何属性都可以写入到属性表中，当然，虚拟机会忽略它不认识的属性，因此我们可以把属性表可以看成是一个`Map`对象。

属性表的结构如下。

|长度（字节）|           2          |         4        |  不定 |
| -------- | -------------------- | ---------------- | ---- |
|   名称    | attribute_name_index | attribute_length | info |

其中`attribute_name_index`和`attribute_length`部分是所有属性都有的，而`info`部分是每个属性自定义的。

在上文中提到方法`<init>`有一个`Code`属性，它存储了方法运行时执行的字节码，以下是它的结构。

|长度（字节）|           2          |         4        |     2     |     2     |      4      |  不定 |                  2            |       不定       |        2         |    不定    | 
| -------- | -------------------- | ---------------- | --------- | --------- | ----------- | ---- | ----------------------- | --------------- | ---------------- | ---------- |
|   名称    | attribute_name_index | attribute_length | max_stack | max_locals | code_length | code | exception_table_length | exception_table | attributes_count | attributes |

- attribute_name_index

属性名称。指向一个`CONSTANT_Utf8_info`类型的常量，最后解析出的字符串值是`Code`。

- attribute_length

属性长度。这里的值是`0x0000001D`，即`29`。

- max_stack

操作数栈的最大深度。这里是`0x0001`，即`1`。

- max_locals

局部变量表所需的存储空间，它的单位是`Slot`，是虚拟机为局部变量分配内存时用到的最小单位，对于不超过32位的数据类型占用1个`Slot`，比如`boolean`,`byte`,`char`,`float`,`int`等；对于超过32位的数据类型则占2个`Slot`，比如`long`和`double`。
局部变量表所需的存储空间并不等于局部变量的个数，因为当代码执行的位置超过一个局部变量的作用域时，该局部变量的`Slot`就可以被重用。这里的值是`0x0001`，即`1`。

- code_length

生成的字节码的长度。这里的值是`0x00000005`，即`5`。需要注意的是，虽然它占4个字节，但是虚拟机规范中规定一个方法不允许超过65535条字节码指令，因此实际上只用到了2个字节。

- code

方法的字节码指令。字节码指令并不是对常量池中常量的引用，当虚拟机读取字节码时，它可以找出对应的字节码指令，并且可以知道该指令后面是否需要跟随参数。
由于每个字节码指令只占1个字节，因此该方法的字节码指令的值是`2A B7 00 01 B1`。虚拟机依次读取后就可以得到以下的字节码。

| 步骤 | 字节码   |    字节码指令  |                     描述                        |
| --- | ------- | ------------- | ---------------------------------------------- |
| 1   | 0x2A    | aload_0       | 将第0个Slot中引用类型的局部变量放入操作数栈顶。       |
| 2   | 0xB7    | invokespecial | 调用操作数栈顶引用类型变量的构造方法，后面的2个字节`00 01`是它接收的参数，指向常量池中`CONSTANT_Methodref_info`类型的常量，这里就是本文刚开始解析的`java/lang/Object."<init>":()V`方法。|
| 3   | 0xB1    | return        | 结束当前方法。                                    |

我们用`javap`工具查看测试类中的两个方法。

```java
 public playground.Test();
    descriptor: ()V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 3: 0

  public int inc();
    descriptor: ()I
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=2, locals=1, args_size=1
         0: aload_0
         1: getfield      #2                  // Field m:I
         4: iconst_1
         5: iadd
         6: ireturn
      LineNumberTable:
        line 8: 0
```

结果中显示两个放的局部变量数量`locals`和方法参数数量`args_size`都是1，但是这两个方法并没有传入任何参数。实际上这里有一个隐藏的`this`参数，编译器在编译时会把对`this`的访问转化成对一个普通方法参数的访问。

- exception_table_length

异常表的长度。本例中没有捕获异常因此异常表长度为0，

- exception_table

异常表，详情见[异常](exception.md/#exception-table)。

- attributes_count

属性计数器。从这里我们可以看到，属性里面还可以嵌套属性，这里属性计数器的值是`0x0001`，表示`Code`属性中有`1`个子属性。

- attributes

属性。这里开头的两个字节是`00 0A`，因此指向第10个常量，第10个常量的值是`01 00 0F 4C 69 6E 65 4E 75 6D 62 65 72 54 61 62 6C 65`，它是一个`CONSTANT_Utf8_info`类型的常量，最后解析出的字符串值是`LineNumberTable`，该属性用于描述源码行号和字节码行号之间的对应关系，其结构如下。

|长度（字节）|          2           |         4        |            2             |        不定                      | 
| -------- | -------------------- | ---------------- | ------------------------ | ----------------- |
|   名称    | attribute_name_index | attribute_length | line_number_table_length | line_number_table |

其中`attribute_name_index`和`attribute_length`和`Code`属性类似，这里不再赘述，后面两个数据项是`LineNumberTable`特有的。

- line_number_table_length

行号表计数器，这里的值是`0x0001`，也就是`1`。

- line_number_table

行号表，记录了源码的行号和字节码行号的关系。每一对关系占4个字节，包括2个字节的`start_pc`和2个字节的`line_number`数据项，前者是字节码行号，后者是源码行号。这里的值是`00 00 00 03`，表示第0行字节码对应源码中的第3行。

接下来还剩下一部分的字节我们还没讲解，实际上它就是方法表集合中第二个方法`inc()`的相关数据，和我们分析的第一个方法类似，不再这里展开。

## 属性表集合（attributes_count，attributes）

最后我们迎来了类文件的尾声——属性表集合，它在上面的方法和字段部分已经出现过。

开头的`00 01`表示属性计数器`attributes_count`的值是`1`，即只有1个属性。后面的两个字节`00 0D`指向第`13`个常量，该常量是一个`CONSTANT_Utf8_info`类型的常量，解析出的字符串值是`SourceFile`，该类型的属性用于记录生成当前类文件的源码文件名称，它的结构如下。

|长度（字节）|          2           |         4        |        2         |
| -------- | -------------------- | ---------------- | ---------------- |
|   名称    | attribute_name_index | attribute_length | sourcefile_index |

`sourcefile_index`的值是`0x000E`，指向常量池中第`14`个常量，它的类型是`CONSTANT_Utf8_info`，解析出的字符串值是`Test.java`。

## 其它属性

除了上面提到的`Code`和`LineNumberTable`属性，还有几下几种常见的属性需要了解。

- Exceptions

`Exceptions`是和`Code`同级的属性，用来描述方法中可能抛出的受检查异常。

- LocalVariableTable

该属性用于记录局部变量的名字（方法参数名也算局部变量）。虚拟机在执行字节码指令时是不关注变量名的，因此如果没有`LocalVariableTable`属性，当别人在引用这个方法时，参数名称就会丢失，有时会显示成IDE生成的`args0`等形式的参数名。这项属性需要在编译时加上`-g`参数才会生成。

比如有以下的代码。

```java
public class Test {

    public int inc(int number) {
        return number + 1;
    }
}
```

我们使用`javac -g Test.java`命令进行编译，然后用`javap -l Test`查看生成的字节码信息。

```java
public int inc(int);
    LineNumberTable:
      line 6: 0
    LocalVariableTable:
      Start  Length  Slot  Name   Signature
          0       4     0  this   Lplayground/Test;
          0       4     1 number   I
```

可以看到这里有两个局部变量，一个是隐藏的`this`，一个就是方法参数名`number`。`Start`和`Length`分别代表局部变量生命周期开始的字节码行号和范围长度，也就是它的作用域范围。

- ConstantValue <a id="constant-value"></a>

该属性的作用是通知虚拟机自动为静态变量初始化。

目前虚拟机有2种策略，一种是在类构造器`<clinit>`方法中对静态变量进行初始化赋值，第二种就是使用`ConstantValue`属性。

对于第2种策略也有几种不同的情况。

以下代码会生成`ConstantValue`属性。

```java
public final static int VALUE = 10;
```

以下代码则不会生成`ConstantValue`属性。

```java
public static int VALUE = 10;
```

实际上只有当静态变量是基本类型或`String`类型并且被`final`修饰时才会使用到`ConstantValue`属性。

因此以下代码也无法使用`ConstantValue`属性，因为它是引用类型且不是`String`。

```java
public final static Object VALUE = new Object();
```

之所以引用类型不能使用`ConstantValue`属性是因为`ConstantValue`属性的值是对常量池中常量的引用，而常量池中没有通用的引用类型，只有`CONSTANT_Integer_info`等特定的几种类型。

- InnerClasses

该属性用于记录内部类和宿主类之间的关系。

- Deprecated

被`@Deprecated`注解标注的类、字段或方法拥有`Deprecated`属性，表示不推荐使用。

- Synthetic

该属性表示一个类、字段或方法是由编译器自动产生的，而不是由源码直接产生的。需要注意的是`<init>`和`<clinit>`方法除外。

- StackMapTable

该属性用于提升字节码验证的性能。

- Signature

用于存储类或字段的泛型信息。以下代码中的泛型信息就是从`Signature`属性中获取的。

```java
public class Test {

    List<Integer> list = new ArrayList<>();

    public static void main(String[] args) throws NoSuchFieldException {
        ParameterizedType type = (ParameterizedType) Test.class.getDeclaredField("list").getGenericType();
        System.out.println(type.getActualTypeArguments()[0]);// 此处打印“class java.lang.Integer”。
    }
}
```
