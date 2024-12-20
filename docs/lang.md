# Fels programming language documentation

## Write you first program

to write first program write code:
```
println("Hello,world!")
```

## Comment you code!

```
//one line comment 
/*
This is multiline comment
*/
```

## Variables

to set new variable write:
```
name = "Tom"
age = 60

println("Name is "+name+", age: "+age)
```

## Variable types

```
a = 60//NUMBER
b = "NAME"//STRING
c = func(a,b) = 2//FUNCTION
d = [1,2,3]//Array
//And more
```

## I/O

This is I/O example

```
println("asdf")//Write text to console 
print("adsf")//Write text to console with out refactoring to new line

//INPUT

using "fels.io.scanner"//Import scanner module

a = scanner.readNum()//Read number from console
println(a)//write variable a to console

```

## Logical gates

This is basic logical gates:

```
println(true or false)
println(true || false)

println(true and false)
println(true && false)

println(not true)
println(!true)
```

Logical operators:

```
a = true
b = false

if(a){
    if(a == b){
        println("?a == b: "+a==b)
    }else{
        println("logical else")
    }
}
```

## User-Defined functions

### User-defined functions basics

Create you function:

```
func out(text){
    println(text)
}
```

Call you function:

```
out("Hello,Fels!")
```

### Extended User-defined functions

Let's add return to you function:

```
func a(){
    return "a"   
}
println(a())//a

//or use "speed form"

func a() = "a"
println(a())//a
```

### Link to function

Write this code:

```
//LINK NOT EXECUTE YOU FUNCTION

func a() = 2
printn(a())//2

println(::a)//nothink output

```

## Loops

### Basic loops

While loop:

```
a = 0
while(a < 10){
    println("a = "+a)
    a++
}
```

For loop:

```
for(i = 0,i < 10,i++){
    println("i = "+i)
}
```

### Extended loops

For-each loop:

```
array = [1,2,3,4,5]

for(el : array){
    println(el)
}
```

Range(python like) loop:

```
range(5){
    println("This is range loop!")
}
```

Loop(rust like) loop:

```
a = 10
loop{
    if(a == 10) break
    println("a = "+a)
    a++
}
```

## OOP

Write you first class:

```
class Test{
    func Test(){}//CONSTRUCTOR
    
    func getASDF() = "asdf"
}
```

Create object of you class:

```
obj = new Test()
println(obj.getASDF())//asdf
```

This keyword:

```
class Vehicle{
    func Vehicle(speed){
        this.speed = spedd
    }
    
    func getSpeed() = this.speed
    
    func setSpeed(speed){
        this.speed = speed
    }
}

car = new Vehicle(60)
car.setSpeed(65)
println(car.getSeed())//65
```
## Extended operators

Create extended operator:

```
`a b c d` = 2
println(`a b c d`)//2
```

## Arrays

### 1D arrays

Create you first array:

```
arr = [1,2,3,4,5]
println(arr)//[1,2,3,4,5]
```

Using in for-each:

```
for(el : arr){
    print(el)
}
//12345
```

Changing array item:

```
arr = [1,2,3,4,5]

arr[1] = "asdf"
println(arr[1])//asdf
```

Creating empty array:

```
using ["fels.utils.arrays"]

arr = arrsay(5)
arr[0] = 1
println(arr[0])//1
```

Append array:

```
arr1 = [1,2]
arr2 = [3,4]

arr3 = arr1 :: arr2
println(arr3)//[1,2,3,4]
```

## 2D and more arrays

You can yse all functions and methods from 1D arrays

Create 2D arrays 

```
arr = [1,2,"3",[1,2,"asdf"]]

println(arr[4][2])
```

Or you can create array using array module

```
using ["fels.utils.arrays"]

arr5 = array(4, 4)
println(arr5)
arr5[1] = arr1
println(arr5)
```

## Map type

Create new map

```
map = {
    "key":"value"
}

println(map["key"])
println(map.key)
```

Changing map values:

```
map = {
    "key":"value"
}

println(map["key"])
println(map.key)

map1["newkey"] = "newvalue"
map1[0] = "zero"
x = 400
map1[x] = [1, 2, 3]
println( map1)
```

## Importing information from other files

You can import functions and classes from other files using 2 methods

other.fels
```
func a() = 2
```

1.Method(using preprocessor):

you_run_it_file.fels
```
#include "other.fels"

println(a())//2
```

2.Method(using import statement)
```
import "other.fels"
println(a())//2
```


## Preprocess you code

For Fels can preprocess you code you should add "-dp" argument to run [see more about it](install.md)

Let's add define to you project:

```
#define OUTLN println
OUTLN(123)//123

#define ABC func

ABC a() = 2
OUTLN(a())//2
```

Import functions and classes from other file:

```
#include "other_file.fels"

/*
YOU CAN USE FUNCTIONS AND CLASSES FROM IMPORTED FILE
*/
```

## ECHO statement

You can use echo function for println all arguments:

```
echo(1,2,3,4,5)
/*OUTPUT:
1
2
3
4
5
*/
```

## Destructing assignment

Use the destructing assignment:

```
using "fels.lang.sfm"//using standard library

arr = ["1",2,"3"]
extract(var1,var2,var3) = arr
echo(var1,var2,var3)
```

Swapping:

```
using "fels.lang.sfm"//using standard library

var1 = "1"
var2 = 2

echo(var1,var2)
extract(var2,var1) = [var1,var2]
echo(var1,var2)
```

## Operation overloading

Create you first overloading:

```
func `println`(a,b) = a+b
println(`println`(1,2))//3
```

## Pattern matching

Example for you using pattern matching:
```
func a(a) = match(a){
    case 1: "One"
    case 2: "Two"
    case 3: "Three"
}

println(a(2))//Two
```

## Create an error

You can throw error

```
panic("This is error")
```
