# Cglib

![版本](https://img.shields.io/badge/cglib:cglib-full-2.0.2-blue.svg)

先看一个简单的Cglib动态代理的例子。

```java
class Proxied {

    public void f() {
        System.out.println("Proxied");
    }
}

class ProxyInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("Proxy");
        methodProxy.invokeSuper(o, objects);
        return o;
    }
}

public static void main(String[] args) {
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(Proxied.class);
    enhancer.setCallback(new ProxyInterceptor());
    Proxied proxy = (Proxied) enhancer.create();
    proxy.f(); //此处打印“Proxy”，“Proxied”。
}
```

从使用方法来看，对比JDK自己的动态代理，Cglib的动态代理不需要被代理类实现接口，使用起来更加方便。下面我们来看下它的实现原理。

很显然，创建代理类的入口就是下面这行代码。

```java
enhancer.create();
```

在它的内部主要调用了下面的方法，并且直接返回该方法的返回值。

```java
// net.sf.cglib.proxy.Enhancer#createHelper
private Object createHelper() {
    // 验证Enhancer对象的各个参数是否合法。
    this.validate(false);
    if (this.superclass != null) {
        this.setNamePrefix(this.superclass.getName());
    } else if (this.interfaces != null) {
        this.setNamePrefix(this.interfaces[ReflectUtils.findPackageProtected(this.interfaces)].getName());
    }

    // 根据当前对象的一些字段生成一个组合键，这个键用来从缓存中读取或写入。
    Object key = KEY_FACTORY.newInstance(this.superclass, this.interfaces, this.filter, this.callbackTypes, this.useFactory);
    return super.create(key);
}

// net.sf.cglib.core.AbstractClassGenerator#create
// 该方法定义在Enhancer的基类中。
protected Object create(Object key) {
    try {
        Object instance = null;
        AbstractClassGenerator.Source var3 = this.source;
        synchronized(this.source) {
            ClassLoader loader = this.getClassLoader();
            Map cache2 = null;
            // ClassLoader为键的一级缓存。
            cache2 = (Map)this.source.cache.get(loader);
            if (cache2 == null) {
                cache2 = new HashMap();
                ((Map)cache2).put(NAME_KEY, new HashSet());
                this.source.cache.put(loader, cache2);
            } else if (this.useCache) {
                // 缓存中存储的是软引用。
                Reference ref = (Reference)((Map)cache2).get(key);
                instance = ref == null ? null : ref.get();
            }

            if (instance == null) {
                Object save = CURRENT.get();
                // 把当前对象存在ThreadLocal中，它的作用是可以通过一个静态方法获取当前的AbstractClassGenerator对象。
                // public static AbstractClassGenerator getCurrent() {return (AbstractClassGenerator)CURRENT.get();}
                CURRENT.set(this);

                Object var25;
                try {
                    this.key = key;
                    Class gen = null;
                    // 如果this.attemptLoad是true，则会尝试去加载类，默认是false。
                    if (this.attemptLoad) {
                        try {
                            gen = loader.loadClass(this.getClassName());
                        } catch (ClassNotFoundException var18) {
                            ;
                        }
                    }

                    if (gen == null) {
                        // 使用一种生成策略生成类对应的字节码。
                        byte[] b = this.strategy.generate(this);
                        String className = ClassNameReader.getClassName(new ClassReader(b));
                        this.getClassNameCache(loader).add(className);
                        // 根据字节码定义类。
                        gen = ReflectUtils.defineClass(className, b, loader);
                    }

                    // 获取类的实例。
                    instance = this.firstInstance(gen);
                    if (this.useCache) {
                        // 缓存实例，这里用的是软引用。
                        ((Map)cache2).put(key, new SoftReference(instance));
                    }

                    var25 = instance;
                } finally {
                    CURRENT.set(save);
                }

                return var25;
            }
        }
        // 如果缓存命中并且软引用还没有被GC清理，那么就直接通过缓存中的实例拿到Class对象并用反射创建新的实例。
        return this.nextInstance(instance);
    } catch (RuntimeException var21) {
        throw var21;
    } catch (Error var22) {
        throw var22;
    } catch (Exception var23) {
        throw new CodeGenerationException(var23);
    }
}
```

可以看到动态生成代理类的关键方法是`this.strategy.generate(this)`，生成策略的默认实现类是`net.sf.cglib.core.DefaultGeneratorStrategy`，而它的内部又调用了`net.sf.cglib.core.ClassGenerator`接口生成字节码，最后的方法其实就在`Enhancer`类中。

```java
// net.sf.cglib.core.DefaultGeneratorStrategy#generate
public byte[] generate(ClassGenerator cg) throws Exception {
    ClassWriter cw = this.getClassWriter();
    // 构建代理类的字节码。
    this.transform(cg).generateClass(cw);
    // 字节码输出到字节数组。
    return this.transform(cw.toByteArray());
}

// net.sf.cglib.proxy.Enhancer#generateClass
public void generateClass(ClassVisitor v) throws Exception {
    Class sc = this.superclass == null ? (class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object) : this.superclass;
    // Cglib中代理类需要继承被代理类，因此被代理类不能是final的。
    if (TypeUtils.isFinal(sc.getModifiers())) {
        throw new IllegalArgumentException("Cannot subclass final class " + sc);
    } else {
        this.depth = calculateDepth(sc);
        List constructors = new ArrayList(Arrays.asList(sc.getDeclaredConstructors()));
        // 找到所有非私有的构造方法。
        CollectionUtils.filter(constructors, new VisibilityPredicate(sc, true));
        if (constructors.size() == 0) {
            throw new IllegalArgumentException("No visible constructors in " + sc);
        } else {
            ClassEmitter e = new ClassEmitter(v);
            // 开始生成代理类，内部使用ASM技术。
            e.begin_class(1, this.getClassName(), Type.getType(sc), this.useFactory ? TypeUtils.add(TypeUtils.getTypes(this.interfaces), FACTORY) : TypeUtils.getTypes(this.interfaces), "<generated>");
            List actualMethods = new ArrayList();
            List interfaceMethods = new ArrayList();
            final Set forcePublic = new HashSet();
            // 找出需要在代理类中生成的方法。
            getMethods(sc, this.interfaces, actualMethods, interfaceMethods, forcePublic);
            List methods = CollectionUtils.transform(actualMethods, new Transformer() {
                public Object transform(Object value) {
                    Method method = (Method)value;
                    int modifiers = 16 | method.getModifiers() & -1025 & -257 & -33;
                    if (forcePublic.contains(MethodWrapper.create(method))) {
                        modifiers = modifiers & -5 | 1;
                    }

                    return ReflectUtils.getMethodInfo(method, modifiers);
                }
            });
            // 生成字段、方法等字节码。
            this.emit(e, false, CollectionUtils.transform(constructors, MethodInfoTransformer.getInstance()), methods, actualMethods);
            // 结束代理类的构建。
            e.end_class();
        }
    }
}
```

Cglib底层是用ASM库来生成字节码的，这部分代码过多因此不再展开。上面代码段主要的流程就是用反射和ASM库构建出代理类所需的字节码，最后调用`org.objectweb.asm.ClassWriter#toByteArray`方法把字节码写入一个字节数组中。

当我们拿到了代理类的字节码，接下来就用`java.lang.ClassLoader#defineClass`方法载入字节码生成代理类的`Class`对象。

```java
// 底层调用ClassLoader的defineClass方法。
ReflectUtils.defineClass(className, b, loader);
```

接下来就能通过Class对象拿到构造方法，最后通过构造方法生成代理类的实例，可以看到整体逻辑和JDK实现的动态代理是差不多的，只不过Cglib使用ASM来生成字节码。

下面我们看一下Cglib生成的代理类是怎样的，增加以下代码开启dump功能。

```java
System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "./");
```

由于生成的类比较长，并且有多个类文件，这里只截取最关键的一段代码。

```java
public class Proxied$$EnhancerByCGLIB$$14f006e6 extends Proxied implements Factory {

    public final void f() {
        MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
        if (this.CGLIB$CALLBACK_0 == null) {
            CGLIB$BIND_CALLBACKS(this);
            var10000 = this.CGLIB$CALLBACK_0;
        }

        if (var10000 != null) {
            var10000.intercept(this, CGLIB$f$0$0$Method, CGLIB$emptyArgs, CGLIB$f$0$0$Proxy);
        } else {
            super.f();
        }
    }
}
```

代码中的`f()`方法就是我们写在被代理类中的方法，可以看到这个方法覆盖了基类中的方法，它主要做的事就是调用我们事先传入的`MethodInterceptor`对象的`intercept`方法。
