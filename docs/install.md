# Installing and running Fels

## Installing

1.[Download the last release](https://github.com/FelekDevYT/Fels-programming-language/releases/tag/pre7)
![img.png](img.png)

2.Unzip .rar archive to folder on you PC
![img_1.png](img_1.png)

3.Open test.fels file in text editor
![img_4.png](img_4.png)

4.Write to test.fels:
```
using "fels.lang.sfm"
println("Hello,world!")

```

5.run cmd on this folder
![img_2.png](img_2.png)

6.write to cmd: ```./fels run -file test.fels```
![img_3.png](img_3.png)

7.Press Enter key and get result of program
![img_6.png](img_6.png)

8.My congratulations, you are start you first program on Fels!

## Professional running guide

Behind read this part you should read basics of Fels [Read](lang.md)

## Running configurations

You can set run arguments by next steps:

1.Write tokens to console>>>```./fels run -dst -file test.fels```
![img_7.png](img_7.png)

2.Do Show AbstractSyntaxTree signature>>>```./fels run -dsast -file test.fels```
![img_9.png](img_9.png)

3.Do Show Measurement time>>>```./fels run -dsmt -file test.fels```
![img_5.png](img_5.png)

4.Do Show Variables(Visitor)>>>```./fels run -dsv -file test.fels```
![img_8.png](img_8.png)

5.Do Preprocess>>>```./fels run -dp -file test.fels```
![img_8.png](img_8.png)
