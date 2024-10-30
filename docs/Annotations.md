# This is full guide of annotations

## How you can define annotation

You can define anno using '@' symbol


## Annotations

## class

Class annotation handle to interpreter about next statement is class

```
@class CLASS
class CLASS{
    func CLASS(){}
    func get() = 2
}
```

## comment

Commenting annotation

```
@comment this is code-comment
```

## function

If you write @function anno Fels be know about next statement is function

```
@function FUNC
func FUNC() = "FUNC"
```

## doc

This is simple "documentation" to you language

```
@doc this is documentation
```

## import 

Annotation for Fels know about next statement is import

```
@import sfm
import "fels.lang.sfm"
```

## preprocess

Anno about the preprocess command

```
@preprocess defining
#define set define
```

## noUse

Anno about next statement no use in file

```
@noUse
var = 6
```

## NULL

Nulleable value

```
@NULL
var = func(){}
```

## FPM

Document the FelsPackageManager using

```
@FPM
#licklude "Buffer.fels"
```

## returns

This anno show type of func or var return

```
@returns NUMBER
func a() = 2
```

## see

Anno about you should see page

```
@see www.google.com
```

## version

This anno show version of current value

```
@vesion 0.0.1
func get() = 1
```

## science

This anno about science this value in Fels(FPM)

```
@science 2 RRE-RELEASE-CONDIDAT OF FELS RELEASE
```

## creator

Write you name in you files!

```
@creator FelekDevYT
func FELEK() = "FELEK"
```

