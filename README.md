# The Fels programming language

## Language concept
Fels is a modern programming language designed with a focus on simplicity, flexibility, and powerful capabilities. It combines elements of object-oriented and functional programming, allowing develoeprs to create clean and maintainable code.

- `Easy syntax`: Fels offers an intuitive and concise syntax that is easy to learn.
- `Modularity`: The language supports the creation and use of modules, enabling code organization and reuse.
- `Error Handling`: Fels provides convenient mechanisms for error and exception handling, simplifying debugging.
- `Interactive Environment`: The ability to execute code interactively allows for quick testing and debugging of programs.

## Examples of core language features

Example 1: Hello,world!
```
println("Hello,World!")
```

Example 2: Defining and calling function
```
func hello(name){
    println("Hello, "+name+"!")
}
hello("FelekDevYT")
```

Example 3: Working with Classes and Objects
```
class Person{
    func init(name,age){
        this.name = name
        this.age = age
    }
    
    func getName() = this.name
    gunc getAge() {
        return this.age
    }
    
    func setAge(age){
        this.age = age
    }
    
    func setName(name) this.name = name
}

peter = new Person("Peter",18)
println(peter.getAge())
```

Example 4: Working with Arrays
```
numbers = [1, 2, 3, 4, 5]
for (num : numbers) {
    println(num)
}
```

## ChangeLog

[Read Fels change log](docs/ChangeLog.md)

## Installation

[Documentation of installation](docs/install.md)

## Contributions
We will review and assist with all reasonable pull requests as long as the following guidelines are met:
- The license header must be applied to all Java source code files.
- In general, check existing code to ensure your code matches relatively closely to the code already in the project.
- Favor readability over compactness.
- If you need help, check out the Google Java Style Guide for reference.

## License

[LICENSE](LICENSE)