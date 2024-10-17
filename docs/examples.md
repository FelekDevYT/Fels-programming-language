# Read examples of Fels code and get more info about Fels programming!

## Write calculator with simple parsing:

```
using "fels.lang.sfm"
using "fels.utils.ftypes"
using "fels.lang.string"
using "fels.utils.arrays"

operations = {
    "+":func(a,b)=a+b,
    "-":func(a,b)=a-b,
    "*":func(a,b)=a*b,
    "/":func(a,b)=a/b,
    ">":func(a,b)=a>b,
	"<":func(a,b)=a<b
}

func calc(expr){
    pos = 0
    len = length(expr)

    func isDigit(c)=48<=c&&c <= 57

    func parseNumber(){
        buffer = ""
        while (pos < len && isDigit(string.charAt(expr, pos))) {
            buffer+=string.toChar(string.charAt(expr,pos))
            pos++
        }
        return atoi(buffer)
    }

    func parseOperation(){
        while (pos < len && !arrays.arrayKeyExists(string.toChar(string.charAt(expr, pos)), operations)) {
            pos++
        }
        return operations[string.toChar(string.charAt(expr,pos++))]
    }

    num1 = parseNumber()
    op = parseOperation()
	println(op)
    num2 = parseNumber()
    return op(num1,num2)
}

println(calc("2+2"))
```

## BrainF*ck like language interpreter

```
using "fels.lang.sfm"
using "fels.utils.arrays"
using "fels.lang.string"

func interpret(code){
    memory = arrays.array(10000)
    pointer = 0
    pos = 0
    mp = 0
    len = length(code)

    while(pos < len){
        cmd = string.charAt(code,pos)

        if(cmd == "+") pointer++
        else if(cmd == "-") pointer--
        else if(cmd == ".") println(pointer)
        else if(cmd == "*") {
            memory[mp] = pointer
        }
        else if(cmd == "&"){
            pointer = memory[mp]
        }
        else if(cmd == "{") mp++
        else if(cmd == "}") mp--
        else if(cmd == "^"){
            pos++
            pos += atoi(string.charAt(code,pos))
        }
        else if(cmd == "@"){
            pos++
            quit(string.charAt(code,pos))
        }

        pos++
    }
}


code = ""
interpret(code)
```
