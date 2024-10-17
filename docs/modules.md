**__# Fels modules documentation

## Introduction to modules

### Import you module

To import module you can write:

```
using "fels.lang.sfm"
```

### Structure of module

fels.lang.sfm

fels - is main package for ALL modules
lang - secondary package
sfm - module


### Modules list

<pre>
fels:
    -grm
        -color
        -fgl
        -forms
        -mBox
        -rayFels
    -io
        -clipboard
        -files
        -scanner
    -lang
        -jfels
        -memory
        -sfm
        -string
        -system
    -utils
        -arrays
        -date
        -ftypes
        -math
        -random
</pre>

### Modules

<h3>lang</h3>

<h4>memory<h4>

You can delete/create variables and functions using memory module:

```
using "fels.lang.memory"

var = 123
remVar(var)

//println(var)//VaribaleDoesNotExistsException

defineVar("asdf",123)//create new variable

isVarExists(var)//false

var = 123
println(getVarValue(var))//123

func a() = 2

remFuncs(a())

//a()//DoesNotExistsFunction

remFunc(a())//asnalog of remFuncs()

putSFunc("asdf",2)
println(asdf())//2

runFile("file.fels")//run fels file without arguments of run

```

<h4>sfm</h4>

```
Functions:

echo(args...) - write all arguments as new line
equals(o1,o2) - "==" full analog
quit(ec) - exit from program with ec code
length(val) - get length of val
sleep(time) - sleep main thread on time ms
thread(function) - run function in new thread
try(function) - try run function 

itoa - int to another type
atoi - another type to int
itoc - int to ASCII char
typeOf(arg) - get type of arg

default(type) - get default value of type
```
