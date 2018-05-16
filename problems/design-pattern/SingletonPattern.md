# 单例模式

#### Java

- [懒汉模式](https://github.com/pojozhang/playground/blob/master/solutions/java/src/main/java/playground/design/singleton/LazySingleton.java) `并发不安全` `简单`
- [懒汉同步锁模式](https://github.com/pojozhang/playground/blob/master/solutions/java/src/main/java/playground/design/singleton/LazySyncSingleton.java) `并发安全` `简单` `效率低`
- [懒汉双重校验模式](https://github.com/pojozhang/playground/blob/master/solutions/java/src/main/java/playground/design/singleton/LazyDCLSingleton.java) `并发不安全`
- [懒汉内存可见双重校验模式](https://github.com/pojozhang/playground/blob/master/solutions/java/src/main/java/playground/design/singleton/LazyDCLVSingleton.java) `并发安全` `效率高` `since 1.5`
- [懒汉内部类模式](https://github.com/pojozhang/playground/blob/master/solutions/java/src/main/java/playground/design/singleton/LazyInnerSingleton.java) `并发安全` `效率高`
- [饿汉模式](https://github.com/pojozhang/playground/blob/master/solutions/java/src/main/java/playground/design/singleton/UrgentSingleton.java) 
- [枚举单例模式](https://github.com/pojozhang/playground/blob/master/solutions/java/src/main/java/playground/design/singleton/EnumSingleton.java) `史上最前` `since 1.5`
- [内部枚举模式](https://github.com/pojozhang/playground/blob/master/solutions/java/src/main/java/playground/design/singleton/InnerEnumSingleton.java) 
