# Fels modules documentation

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

<h4>string</h4>

String module have basic function for working with Strings!

```
string.getBytes()//getting bytes of string
string.sprintf()//strintf Java like method
string.split()//split you string
//Java like functions
string.indexOf()
string.lastIndexOf()
string.charAt()
string.toChar()//Gets first symbol as string
string.substring()
string.toLowerCase()
string.toUpperCase()
string.trim()
string.replace()
string.replaceAll()
string.replaceFirst()
string.parseInt()
string.parseDouble()
string.parseLong()
string.stripMargin()
```

<h4>system</h4>

System module funcs:

```
system.currentTimeMillis()
system.nanoTime()
system.getUsedMemory()
system.getTotalMemory()
system.getMaxMemory()
system.getFreeMemory()
system.availableProcessors()
system.exec()//execute cmd code
system.getProperty()
/*PROPERTIES:
fels.vesion
fels.creator
fels.loader.version
date
*/
system.exit()
```

<h4>number</h4>

Number funcs

```
number.min()
number.max()
number.MAX_VALUE()
number.MIN_VALUE()
```

<h4>ftypes</h4>
ftypes module using for handling with types

FTYPES constants:

ftypes.OBJECT<br>
ftypes.NUMBER<br>
ftypes.STRING<br>
ftypes.ARRAY<br>
ftypes.FUNCTION<br>
ftypes.MAP<br>
ftypes.CLASS<br>

FTYPES funcs:

typeToString()//ftype const to text<br>
ftypes.toByte()<br>
ftypes.toShort()<br>
ftypes.toInt()<br>
ftypes.toLong()<br>
ftypes.toFloat()<br>
ftypes.toDouble()<br>

## character

Character funcs:
character.isDigit()<br>
character.isLetter()<br>
character.isDigitOrLetter()<br>
character.isAlphabetic()<br>

## utils.arrays

arrays.array()//creating array with first argument size<br>
arrays.join()<br>
arrays.sort()<br>
arrays.arrayCombine()<br>
arrays.arrayKeyExists()<br>
arrays.arrayValues()<br>
arrays.arraysSplice()<br>
arrays.range()<br>
arrays.stringFromBytes()<br>

## utils.math

Constants:

math.PI<br>
math.E<br>

Standard math functions:

math.abs()<br>
math.acos()<br>
math.asin()<br>
math.atan()<br>
math.atan2()<br>
math.cbrt()<br>
math.ceil()<br>
math.copySign()<br>
math.cos()<br>
math.cosh()<br>
math.exp()<br>
math.expm1()<br>
math.floor()<br>
math.getExponent()<br>
math.hypot()<br>
math.IEEEremainder()<br>
math.log()<br>
math.log1p()<br>
math.log10()<br>
math.max()<br>
math.min()<br>
math.nextAfter()<br>
math.nextUp()<br>
math.nextDown()<br>
math.pos()<br>
math.rint()<br>
math.round()<br>
math.signum()<br>
math.sin()<br>
math.sinh()<br>
math.sqrt()<br>
math.tan()<br>
math.tanh()<br>
math.toDegrees()<br>
math.toRadians()<br>
math.ulp()<br>

## utils.random

Functions:

random.random()//random num from to<br>
random.rand()//random num from 0 to <br>
random.rnd()//full random number<br>

## utils.jfels

jfels.isNull()<br>
jfels.newClass()<br>
jfels.toObject()<br>
jfels.toValue()<br>
jfels.null()<br>
jfels.boolean.class()<br>
jfels.boolean[].class()<br>
jfels.boolean[][].class()<br>
jfels.byte.class()<br>
jfels.byte[].class()<br>
jfels.byte[][].class()<br>
jfels.short.class()<br>
jfels.short[].class()<br>
jfels.short[][].class()<br>
jfels.char.class()<br>
jfels.char[].class()<br>
jfels.char[][].class()<br>
jfels.int.class()<br>
jfels.int[].class()<br>
jfels.int[][].class()<br>
jfels.long.class()<br>
jfels.long[].class()<br>
jfels.long[][].class()<br>
jfels.float.class()<br>
jfels.float[].class()<br>
jfels.float[][].class()<br>
jfels.double.class()<br>
jfels.double[].class()<br>
jfels.double[][].class()<br>
jfels.String.class()<br>
jfels.String[].class()<br>
jfels.String[][].class()<br>
jfels.Object.class()<br>
jfels.Object[].class()<br>
jfels.Object[][].class()<br>

### io.files

files.readAllLines()<br>
files.readAllBytes()<br>
files.readAllText()<br>
files.writeAllText()<br>
files.writeAllLines()<br>
