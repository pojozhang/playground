# 动态代理

![版本](https://img.shields.io/badge/java-10-blue.svg)

下面是一个简单的动态代理的例子。

```java
// JDK自带的动态代理必须要实现一个接口。
interface Interface {
    void f();
}

// 被代理类。
class Proxied implements Interface {

    @Override
    public void f() {
        System.out.println("Proxied");
    }
}

// 代理类
class ProxyHandler implements InvocationHandler {

    private Proxied proxied;

    public ProxyHandler(Proxied proxied) {
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("ProxyHandler");
        method.invoke(proxied, args);
        return null;
    }
}

public static void main(String[] args) {
    Interface proxy = (Interface) Proxy.newProxyInstance(Proxied.class.getClassLoader(), Proxied.class.getInterfaces(), new ProxyHandler(new Proxied()));
    proxy.f(); //此处打印“ProxyHandler”，“Proxied”。
}
```

使用JDK提供的`Proxy.newProxyInstance()`方法，我们可以动态的生成一个实现了开发者指定接口的代理类。

```java
System.out.println(proxy.getClass());
```

我们打印代理类的类型信息，可以看到它的类名是`$Proxy0`，那么这个类是怎么来的呢？我们来看一下`Proxy.newProxyInstance()`方法的源码。

```java
public static Object newProxyInstance(ClassLoader loader,
                                      Class<?>[] interfaces,
                                      InvocationHandler h) {
    Objects.requireNonNull(h);
    final Class<?> caller = System.getSecurityManager() == null
                                ? null
                                : Reflection.getCallerClass();
    // 获取代理类的构造方法。
    Constructor<?> cons = getProxyConstructor(caller, loader, interfaces);

    return newProxyInstance(caller, cons, h);
}

private static Object newProxyInstance(Class<?> caller,
                                       Constructor<?> cons,
                                       InvocationHandler h) {
    try {
        if (caller != null) {
            checkNewProxyPermission(caller, cons.getDeclaringClass());
        }
        // 用反射创建代理对象。
        return cons.newInstance(new Object[]{h});
    } catch (IllegalAccessException | InstantiationException e) {
        throw new InternalError(e.toString(), e);
    } catch (InvocationTargetException e) {
        Throwable t = e.getCause();
        if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        } else {
            throw new InternalError(t.toString(), t);
        }
    }
}
```

以上可以看出在`newProxyInstance()`方法中首先拿到了代理类的构造器，然后用反射创建了代理对象。那么这个构造器是什么拿到的呢？我们继续到进到`getProxyConstructor()`方法中。

```java
private static Constructor<?> getProxyConstructor(Class<?> caller,
                                                  ClassLoader loader,
                                                  Class<?>... interfaces)
{
    // 这里的if-else分支中的代码几乎完全一样，仅当interfaces的长度为1时进行优化。
    if (interfaces.length == 1) {
        Class<?> intf = interfaces[0];
        if (caller != null) {
            checkProxyAccess(caller, loader, intf);
        }
        return proxyCache.sub(intf).computeIfAbsent(
            loader,
            (ld, clv) -> new ProxyBuilder(ld, clv.key()).build()
        );
    } else {
        final Class<?>[] intfsArray = interfaces.clone();
        if (caller != null) {
            checkProxyAccess(caller, loader, intfsArray);
        }
        final List<Class<?>> intfs = Arrays.asList(intfsArray);
        return proxyCache.sub(intfs).computeIfAbsent(
            loader,
            (ld, clv) -> new ProxyBuilder(ld, clv.key()).build()
        );
    }
}
```

看来核心代码都藏在`ProxyBuilder`类中，我们继续往下看。

```java
// java.lang.reflect.Proxy.ProxyBuilder#build
Constructor<?> build() {
    Class<?> proxyClass = defineProxyClass(module, interfaces);
    final Constructor<?> cons;
    try {
        cons = proxyClass.getConstructor(constructorParams);
    } catch (NoSuchMethodException e) {
        throw new InternalError(e.toString(), e);
    }
    AccessController.doPrivileged(new PrivilegedAction<Void>() {
        public Void run() {
            cons.setAccessible(true);
            return null;
        }
    });
    return cons;
}
```

上面代码中有两行代码是核心，一是用`defineProxyClass()`方法定义了一个类，二是用反射拿到刚才定义的类的构造器。

由于`defineProxyClass()`方法比较长我们截取最核心的几段代码。

```java
private static Class<?> defineProxyClass(Module m, List<Class<?>> interfaces) {
    // 此处省略了代码。
    // 生成代理类的类名，也就是上文$Proxy0类名的出处。
    // nextUniqueNumber是一个AtomicLong类型的静态常量，用来为代理类分配唯一的序号。
    long num = nextUniqueNumber.getAndIncrement();
    String proxyName = proxyPkg.isEmpty()
                            ? proxyClassNamePrefix + num
                            : proxyPkg + "." + proxyClassNamePrefix + num;

    // 此处省略了代码。
    // 最关键的一步：生成代理类的字节码。
    byte[] proxyClassFile = ProxyGenerator.generateProxyClass(
            proxyName, interfaces.toArray(EMPTY_CLASS_ARRAY), accessFlags);
    try {
        // 载入上面生成的字节码，生成代理类的类对象。
        Class<?> pc = UNSAFE.defineClass(proxyName, proxyClassFile,
                                            0, proxyClassFile.length,
                                            loader, null);
        // 此处省略了代码。
        return pc;
    } catch (ClassFormatError e) {
        throw new IllegalArgumentException(e.toString());
    }
}
```

动态生成类的关键就是`ProxyGenerator.generateProxyClass()`方法，它在内部使用了私有的`ProxyGenerator`类型对象生成对象的字节码。

```java
private byte[] generateClassFile() {
    // 此处省略了代码。
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    DataOutputStream dout = new DataOutputStream(bout);

    try {
        // Java .class文件的魔数。
        dout.writeInt(0xCAFEBABE);
        dout.writeShort(CLASSFILE_MINOR_VERSION);
        dout.writeShort(CLASSFILE_MAJOR_VERSION);
        // cp是constant pool的缩写。
        // 这里是把常量池的字节码写入到字节流中。
        cp.write(dout);

        // 此处省略了代码。
        // 写入字段个数和字段信息。
        dout.writeShort(fields.size());
        for (FieldInfo f : fields) {
            f.write(dout);
        }

        // 写入方法个数和方法信息。
        dout.writeShort(methods.size());
        for (MethodInfo m : methods) {
            m.write(dout);
        }

        // 此处省略了代码。
    } catch (IOException e) {
        throw new InternalError("unexpected I/O Exception", e);
    }

    // 转成byte数组。
    return bout.toByteArray();
}
```

从上面可以看到`ProxyGenerator`用反射的方式获取到代理类所需要的方法和字段等信息并生成相应的字节码，最后把字节码写到字节流中。

我们可以在动态代理的代码前加入以下代码让JVM把生成出来的代理类的`.class`文件保存到本地。

```java
System.setProperty("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");
```

对它进行反编译后可以看到下面的代码。

```java
package playground;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

final class $Proxy0 extends Proxy implements Interface {
    private static Method m1;
    private static Method m2;
    private static Method m3;
    private static Method m0;

    public $Proxy0(InvocationHandler var1) throws  {
        super(var1);
    }

    public final boolean equals(Object var1) throws  {
        try {
            return (Boolean)super.h.invoke(this, m1, new Object[]{var1});
        } catch (RuntimeException | Error var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    public final String toString() throws  {
        try {
            return (String)super.h.invoke(this, m2, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final void f() throws  {
        try {
            super.h.invoke(this, m3, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final int hashCode() throws  {
        try {
            return (Integer)super.h.invoke(this, m0, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    static {
        try {
            m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
            m2 = Class.forName("java.lang.Object").getMethod("toString");
            m3 = Class.forName("playground.Interface").getMethod("f");
            m0 = Class.forName("java.lang.Object").getMethod("hashCode");
        } catch (NoSuchMethodException var2) {
            throw new NoSuchMethodError(var2.getMessage());
        } catch (ClassNotFoundException var3) {
            throw new NoClassDefFoundError(var3.getMessage());
        }
    }
}
```

可以看到生成出来的`f()`方法内部调用了`super.h.invoke(this, m3, (Object[])null)`，这里的`h`实际上就是基类`Proxy`类中的`InvocationHandler`，本例中就是我们自己实现的那个`ProxyHandler`。

```java
protected InvocationHandler h;
```

至此，动态代理的学习告一段落了。
