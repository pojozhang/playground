# OO
 - Object - Oriented
 - 面向对象

# OO Basics
- Abstraction 抽象
- Inheritance 继承
- Encapsulation 封装
- Polymorphism 多态


# OO Priciples  
`Reference: "Head First Design Pattern"`

## 1. Encapsulate what varies.
- Seperate what changes from what stays the same.
- Take what varies and encapsulate it, so it won't affect the rest of your code.
- Take the parts that vary and encapsulate them, so that later you can alter or extend the parts that vary without affecting those don't.
- Result? Fewer unintended consequences from code changes and more flexibility in your systems.


## 2. Program to an interface, not an implementation.
- "Program to an interface" really means "Program to a supertype".
- The point is to exploit polymorphism by programing to a supertype so that the actual runtime object isn't locked into the code.
- The declared type of the variables should be a supertype, usually an abstract class or interface, so that the objects assigned to.
- those variables can be of any concrete implementation of the supertype, which means the class declaring them doesn't have to know about the actual object types.


## 3. Favor composition over inheritance.
- HAS-A can be better than IS-A.
- Not only does it let you encapsulate a family of algorithms into their own set of classes, but it also lets you change behavior at runtime as long as the object (you're composing with) implements the correct behavior interface.
