# 泛型

## 协变

Java的数组是协变的，比如下面的代码。

```java
SuperClass[] array = new SubClass[10];
```

其中`SubClass`是`SuperClass`的子类。

泛型是不支持协变的，比如以下代码无法通过编译。

```java
ArrayList<SuperClass> list = new ArrayList<SubClass>();
```

这时候就需要用到上边界限定通配符。

```java
ArrayList<? extends SuperClass> list = new ArrayList<SubClass>();
```

## 上边界限定通配符

我们把`<? extends T>`的形式称为上边界限定通配符，它指的是有一个类是`T`或其子类，我们可以记为`S`，`S`是`T`或者`T`的子类，但是我们不知道它具体是哪一个类。

```java
class SuperClass {
}

class SubClassA extends SuperClass {
}

class SubClassB extends SuperClass {
}

public static void main(String[] args) {
    List<? extends SuperClass> list = new ArrayList<>();
    list.add(new SubClassA()); //此句编译不通过。
}
```

上面的代码尝试把`SuperClass`的子类`SubClassA`的一个对象放入`List<? extends SuperClass>`类型的集合中，结果却编译不通过，这是为什么呢？

原因是上边界限定通配符是用来描述尖括号里的泛型的，比如上面的`List<? extends SuperClass>`，我们可以把它看作`List<S>`，而`S`是`SuperClass`的一个子类，至于是哪个子类我们不得而知，因此`List<? extends SuperClass>`可能是`List<SubClassA>`，也可能是`List<SubClassB>`，所以我们无法把任何对象放入集合中（只能放入`null`）。

虽然我们不能往集合中增加任何`null`以外的元素，但是读取是没有问题的。

```java
SuperClass superClass = list.get(0);
```

可以看到读取出来的类型就是`SuperClass`，虽然我们不知道泛型具体是`SuperClass`的哪一个子类，但是它们对应的元素都一定能转成`SuperClass`。

既然这样的写法会导致我们无法插入元素，那么有什么用呢？上面的例子比较奇怪，通常我们是用下面这种方式使用上边界限定通配符的。

```java
void f(ArrayList<SuperClass> list) {
}

f(new ArrayList<SubClass>());
```

上面代码中当我们尝试调用`f()`方法时提示编译不通过，因为`ArrayList<SubClass>`是无法转成`ArrayList<SuperClass>`，解决方法就是使用上边界限定通配符。

```java
void f(ArrayList<? extends SuperClass> list) {
}
```

## 下边界限定通配符

和上边界限定通配符类似，我们把`<? super T>`的形式称为下边界限定通配符，它指的是有一个类是`T`或其基类，我们可以记为`S`，`S`是`T`或者`T`的基类，但是我们不知道它具体是哪一个类。

下面的代码试图把`SuperClass`子类的实例放入集合中，和上边界限定通配符中的例子相反，这种做法在下边界限定通配符中是允许的。因为虽然我们不知道泛型中的类具体是`SuperClass`的哪一个基类（从继承层次上看），但是由于`SuperClass`的子类一定是`SuperClass`基类的子类，所以可以插入到集合中。但是`SuperClass`的基类是不能插入到集合中的，理由同上边界限定通配符类似。

```java
public static void main(String[] args) {
    List<? super SuperClass> list = new ArrayList<>();
    list.add(new SubClassA());
    list.add(new SubClassB());

    list.add(new SuperSuperClass()); //编译不通过，这里的SuperSuperClass是SuperClass的基类。
}
```

当我们尝试去读取时，由于编译器不知道是基类具体是什么类型，所以只能返回`Object`类型，因为它是所有类型的基类。

```java
Object object = list.get(0);
```

通常我们使用如下的方式应用下边界限定通配符。

```java
void f(ArrayList<SubClass> list) {
}

f(new ArrayList<SuperClass>());
```

上面代码中的`ArrayList<SuperClass>`无法转成`ArrayList<SubClass>`，因此我们需要用到下边界限定通配符从而通过编译。

```java
void f(ArrayList<? super SubClass> list) {
}
```

## 无边界通配符

无边界通配符的形式是`<?>`，等价于`<? extends Object>`，它表示没有任何限制，可以是任何的类型。由于是任何类型，所以编译器不知道它是什么类型，因此以下代码是不能通过编译的。

```java
List<?> list = new ArrayList<>();
list.add(1);
```

既然无边界通配符是任意类型，那么为什么当我们试图往集合中插入整数1时却无法通过编译呢？其实原理和上边界限定通配符是类似的。
我们把`List<?>`看作`List<S>`，这里的`S`可以是任何类型，可能是`List<Boolean>`，也可能是`List<String>`，编译器是不知道的。当你往集合中插入元素1时不代表编译器就认为它是一个`List<Integer>`类型的集合。

以下两行代码是有区别的。

```java
List<?> listA = new ArrayList<>();
List listB = new ArrayList<>();
```

第二行等价于`new ArrayList<Object>()`，因此我们可以插入任何类型的对象。

## 自限定

形如以下代码的泛型称为自限定泛型。

```java
// java.lang.Enum
Enum<E extends Enum<E>>
```

自限定所做的就是要求在继承关系中，强制要求将正在定义的类当做参数传递给基类，这是它的定义。下面我们看一个例子。

首先我们定义一个抽象基类。

```java
abstract class Generics<T> implements Comparable<T>{
}
```

然后定义一个子类。

```java
class A extends Generics<A>{

    @Override
    public int compareTo(A o) {
        return 0;
    }
}
```

在`main()`方法中对两个`A`类型的对象进行比较。

```java
public static void main(String[] args) {
    new A().compareTo(new A());
}
```

上面的代码可以正常运行，我们对`A`进行一些修改，泛型参数从`A`修改为`Integer`。

```java
class A extends Generics<Integer>{

    @Override
    public int compareTo(Integer o) {
        return 0;
    }
}
```

这时原本应该比较两个`A`类型对象的`compareTo()`方法只能把`A`对象和一个整数对象进行比较，这是不合理的，通常我们只应该对两个同类型的对象进行比较，而在这里我们却可以随意修改泛型参数，那么我们能不能增加一些限制呢？

答案是可以的，这里我们就用到了自限定泛型。这时如果`A`里的泛型参数是`Integer`那么是无法通过编译的。通过这种方法我们对子类的泛型参数进行了限制。

```java
abstract class Generics<T extends Generics<T>> implements Comparable<T>{
}
```

## 泛型中的异常

泛型也可用在异常中。

```java
interface Generics<E extends Exception> {
    void f() throws E;
}
```

但是我们不能在`catch`中使用泛型，以下代码不能编译。

```java
void f(){
    try {}
    catch (E e){}
}
```

## 类型擦除

泛型信息只存在代码编译阶段，编译完成后相关的泛型信息会被擦除。比如下面的代码中，`Integer`的信息被擦除了，导致程序打印结果是“true”。

```java
public static void main(String[] args) {
    List<Integer> list = new ArrayList<>();
    System.out.println(list.getClass() == ArrayList.class); //此处打印true。
}
```
