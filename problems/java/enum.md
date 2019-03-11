# 枚举

我们先写一个简单的枚举类`Human`表示人类，里面有不同的枚举值代表不同肤色的人种。

```java
enum Human {
    BLACK,
    WHITE,
    YELLOW;
}
```

对上面的代码进行编译后，使用[JAD工具](http://www.javadecompilers.com)进行反编译，可以看到以下的代码。

```java
final class Human extends Enum
{

    public static Human[] values()
    {
        return (Human[])$VALUES.clone();
    }

    public static Human valueOf(String s)
    {
        return (Human)Enum.valueOf(playground/Human, s);
    }

    private Human(String s, int i)
    {
        super(s, i);
    }

    public static final Human BLACK;
    public static final Human WHITE;
    public static final Human YELLOW;
    private static final Human $VALUES[];

    static
    {
        BLACK = new Human("BLACK", 0);
        WHITE = new Human("WHITE", 1);
        YELLOW = new Human("YELLOW", 2);
        $VALUES = (new Human[] {
            BLACK, WHITE, YELLOW
        });
    }
}
```

从上面可以看到`Human`类继承了`Enum`类，原本3个枚举值也变成了`Human`类的静态常量。

下面我们往`Human`类中加入一个构造方法。

```java
enum Human {
    BLACK("Africa"),
    WHITE("America"),
    YELLOW("China");

    private String country;

    public String getCountry() {
        return country;
    }

    Human(String country) {
        this.country = country;
    }
}
```

对修改后的代码进行反编译，结果如下。

```java
final class Human extends Enum
{

    public static Human[] values()
    {
        return (Human[])$VALUES.clone();
    }

    public static Human valueOf(String s)
    {
        return (Human)Enum.valueOf(playground/Human, s);
    }

    public String getCountry()
    {
        return country;
    }

    private Human(String s, int i, String s1)
    {
        super(s, i);
        country = s1;
    }

    public static final Human BLACK;
    public static final Human WHITE;
    public static final Human YELLOW;
    private String country;
    private static final Human $VALUES[];

    static
    {
        BLACK = new Human("BLACK", 0, "Africa");
        WHITE = new Human("WHITE", 1, "America");
        YELLOW = new Human("YELLOW", 2, "China");
        $VALUES = (new Human[] {
            BLACK, WHITE, YELLOW
        });
    }
}
```

可以看到，编译器自动修改了Human的构造方法中的参数。

我们更进一步，往`Human`类中增加一个抽象方法。

```java
enum Human {
    BLACK() {
        @Override
        String getCountry() {
            return "Africa";
        }
    },
    WHITE() {
        @Override
        String getCountry() {
            return "America";
        }
    },
    YELLOW() {
        @Override
        String getCountry() {
            return "China";
        }
    };

    abstract String getCountry();
}
```

这时用`javac`编译后你会发现，除了`Human.class`文件，编译器还为每一种枚举值单独创建了一个匿名类，形如`Human$1`、`Human$2`、`Human$3`。

这时候反编译的结果如下。

```java
abstract class Human extends Enum
{

    public static Human[] values()
    {
        return (Human[])$VALUES.clone();
    }

    public static Human valueOf(String s)
    {
        return (Human)Enum.valueOf(playground/Human, s);
    }

    private Human(String s, int i)
    {
        super(s, i);
    }

    abstract String getCountry();

    public static final Human BLACK;
    public static final Human WHITE;
    public static final Human YELLOW;
    private static final Human $VALUES[];

    static
    {
        BLACK = new Human("BLACK", 0) {

            String getCountry()
            {
                return "Africa";
            }

        };

        WHITE = new Human("WHITE", 1) {

            String getCountry()
            {
                return "America";
            }

        };

        YELLOW = new Human("YELLOW", 2) {

            String getCountry()
            {
                return "China";
            }

        };

        $VALUES = (new Human[] {
            BLACK, WHITE, YELLOW
        });
    }
}
```

`Human`类自动加上了`abstract`修饰符，而3种枚举值以匿名类的方式重写了`getCountry()`方法。
