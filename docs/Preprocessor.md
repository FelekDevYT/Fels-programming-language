# This is tutorial of preprocessor in Fels

## How you can write preprocessor code

for run preprocessor code write preprocessor directives

## Preprocessor directives

### define

You can use define(C++ like)

```
#define RickRoll println
RickRoll("Rick Astley")
```

## Including from files

You can include funcs and classes from another file

```
#include "test.fels"
```

## FelsPackageManager

You can install library from github repository using:

```
#linklude "Buffer.fels"
```

#linklude - install and include file from repos

## Regions

You can create region:

```
#region reg
/*
You code
*/
#endregion reg
```

## Defining var-like var

```
#define var 60
println(var)
```

## Creating error using preprocessor:

```
#error this is simple error
```

## Running cmd command using preprocessor:

Lets run Linux cmd command:

```
#cmdc ls
```
