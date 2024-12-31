# Module Documentation

## Table of Contents
1. [color](#module-color)
2. [fgl](#module-fgl)
3. [forms](#module-forms)
4. [mBox](#module-mbox)
5. [rayFels](#module-rayfels)
6. [clipboard](#module-clipboard)
7. [ffsh](#module-ffsh)
8. [files](#module-files)
9. [scanner](#module-scanner)
10. [stream](#module-stream)
11. [character](#module-character)
12. [console](#module-console)
13. [ftypes](#module-ftypes)
14. [memory](#module-memory)
15. [number](#module-number)
16. [sfm](#module-sfm)
17. [string](#module-string)
18. [system](#module-system)
19. [arrays](#module-arrays)
20. [date](#module-date)
21. [jfels](#module-jfels)
22. [json](#module-json)
23. [math](#module-math)
24. [random](#module-random)
25. [yaml](#module-yaml)

---

## Module `color`
### Functions:
- `reset()`: Resets the console color.
- `red()`: Sets the text color to red.
- `green()`: Sets the text color to green.
- `blue()`: Sets the text color to blue.
- `white()`: Sets the text color to white.
- `black()`: Sets the text color to black.
- `purple()`: Sets the text color to purple.
- `yellow()`: Sets the text color to yellow.
- `cyan()`: Sets the text color to cyan.

---

## Module `fgl`
### Functions:
- `window(title: String, width: Int, height: Int)`: Creates a new window with the specified title and dimensions.
- `prompt(message: String)`: Displays a dialog box requesting input.
- `keypressed()`: Returns the code of the pressed key.
- `mousehover()`: Returns the current mouse coordinates.
- `line(x1: Int, y1: Int, x2: Int, y2: Int)`: Draws a line on the canvas.
- `oval(x: Int, y: Int, w: Int, h: Int)`: Draws an oval on the canvas.
- `foval(x: Int, y: Int, w: Int, h: Int)`: Draws a filled oval on the canvas.
- `rect(x: Int, y: Int, w: Int, h: Int)`: Draws a rectangle on the canvas.
- `frect(x: Int, y: Int, w: Int, h: Int)`: Draws a filled rectangle on the canvas.
- `clip(x: Int, y: Int, w: Int, h: Int)`: Sets the drawing area.
- `drawstring(text: String, x: Int, y: Int)`: Draws a string of text on the canvas.
- `repaint()`: Refreshes the canvas.

---

## Module `forms`
### Functions:
- `newButton(text: String)`: Creates a new button with the specified text.
- `newLabel(text: String, align: Int)`: Creates a new label with the specified text and alignment.
- `newPanel()`: Creates a new panel.
- `newWindow(title: String)`: Creates a new window with the specified title.
- `newProgressBar()`: Creates a new progress bar.
- `newScrollPane(view: Component)`: Creates a new scroll pane with the specified view.

---

## Module `mBox`
### Functions:
- `showErrorMessage(title: String, message: String)`: Displays an error message.
- `showInformationMessage(title: String, message: String)`: Displays an informational message.
- `showWarningMessage(title: String, message: String)`: Displays a warning message.
- `showNoneMessage(title: String, message: String)`: Displays a plain message.
- `showInputDialog(title: String, message: String)`: Displays an input dialog and returns the entered text.

---

## Module `rayFels`
### Functions:
- `rfInitWindow(width: Int, height: Int, title: String)`: Initializes a Raylib window.
- `rfIsCloseRequest()`: Checks if a close request has been made for the window.
- `rfCloseWindow()`: Closes the Raylib window.
- `rfSetFPS(fps: Int)`: Sets the target frames per second.
- `rfBeginDrawing()`: Begins the drawing process.
- `rfEndDrawing()`: Ends the drawing process.
- `rfDrawText(text: String, x: Int, y: Int, fontSize: Int, color: Color

## Module `color`
### Functions:
- `reset()`: Resets the console color.
- `red()`: Sets the text color to red.
- `green()`: Sets the text color to green.
- `blue()`: Sets the text color to blue.
- `white()`: Sets the text color to white.
- `black()`: Sets the text color to black.
- `purple()`: Sets the text color to purple.
- `yellow()`: Sets the text color to yellow.
- `cyan()`: Sets the text color to cyan.

---

## Module `fgl`
### Functions:
- `window(title: String, width: Int, height: Int)`: Creates a new window with the specified title and dimensions.
- `prompt(message: String)`: Displays a dialog box requesting input.
- `keypressed()`: Returns the code of the pressed key.
- `mousehover()`: Returns the current mouse coordinates.
- `line(x1: Int, y1: Int, x2: Int, y2: Int)`: Draws a line on the canvas.
- `oval(x: Int, y: Int, w: Int, h: Int)`: Draws an oval on the canvas.
- `foval(x: Int, y: Int, w: Int, h: Int)`: Draws a filled oval on the canvas.
- `rect(x: Int, y: Int, w: Int, h: Int)`: Draws a rectangle on the canvas.
- `frect(x: Int, y: Int, w: Int, h: Int)`: Draws a filled rectangle on the canvas.
- `clip(x: Int, y: Int, w: Int, h: Int)`: Sets the drawing area.
- `drawstring(text: String, x: Int, y: Int)`: Draws a string of text on the canvas.
- `repaint()`: Refreshes the canvas.

---

## Module `forms`
### Functions:
- `newButton(text: String)`: Creates a new button with the specified text.
- `newLabel(text: String, align: Int)`: Creates a new label with the specified text and alignment.
- `newPanel()`: Creates a new panel.
- `newWindow(title: String)`: Creates a new window with the specified title.
- `newProgressBar()`: Creates a new progress bar.
- `newScrollPane(view: Component)`: Creates a new scroll pane with the specified view.

---

## Module `mBox`
### Functions:
- `showErrorMessage(title: String, message: String)`: Displays an error message.
- `showInformationMessage(title: String, message: String)`: Displays an informational message.
- `showWarningMessage(title: String, message: String)`: Displays a warning message.
- `showNoneMessage(title: String, message: String)`: Displays a plain message.
- `showInputDialog(title: String, message: String)`: Displays an input dialog and returns the entered text.

---

## Module `rayFels`
### Functions:
- `rfInitWindow(width: Int, height: Int, title: String)`: Initializes a Raylib window.
- `rfIsCloseRequest()`: Checks if a close request has been made for the window.
- `rfCloseWindow()`: Closes the Raylib window.
- `rfSetFPS(fps: Int)`: Sets the target frames per second.
- `rfBeginDrawing()`: Begins the drawing process.
- `rfEndDrawing()`: Ends the drawing process.
- `rfDrawText(text: String, x: Int, y: Int, fontSize: Int, color: Color)`: Draws text on the screen.
- `rfDrawLine(x1: Int, y1: Int, x2: Int, y2: Int, color: Color)`: Draws a line on the screen.
- `rfDrawRect(x: Int, y: Int, width: Int, height: Int, color: Color)`: Draws a rectangle on the screen.
- `rfGetFPS()`: Returns the current frames per second.
- `rfDrawFPS(x: Int, y: Int)`: Draws the current frames per second on the screen.
- `rfDrawGrid(spacing: Int, size: Int)`: Draws a grid on the screen.

---

## Module `clipboard`
### Functions:
- `get()`: Returns the contents of the clipboard.
- `set(text: String)`: Sets the text in the clipboard.
- `clear()`: Clears the clipboard.
- `add(text: String)`: Adds text to the current clipboard content.

---

## Module `ffsh`
### Functions:
- `fopen(path: String, mode: String)`: Opens a file with the specified path and mode.
- `flush(fd: Int)`: Flushes the file stream associated with the file descriptor.
- `fclose(fd: Int)`: Closes the file associated with the file descriptor.
- `fread(fd: Int, size: Int, count: Int)`: Reads data from the file into a buffer.
- `fwrite(fd: Int, buffer: Byte[], size: Int, count: Int)`: Writes data from a buffer to the file.
- `feof(fd: Int)`: Checks if the end of the file has been reached.
- `ferror(fd: Int)`: Checks for errors on the file stream.

---

## Module `files`
### Functions:
- `exists(path: String)`: Checks if a file or directory exists at the specified path.
- `delete(path: String)`: Deletes the file or directory at the specified path.
- `rename(oldPath: String, newPath: String)`: Renames a file or directory.
- `copy(source: String, destination: String)`: Copies a file from the source path to the destination path.
- `move(source: String, destination: String)`: Moves a file from the source path to the destination path.
- `list(directory: String)`: Returns a list of files and directories in the specified directory.

---

## Module `scanner`
### Functions:
- `nextInt()`: Reads the next integer from the input.
- `nextString()`: Reads the next string from the input.
- `nextLine()`: Reads the next line from the input.
- `hasNext()`: Checks if there is more input available.
- `close()`: Closes the scanner.

---

## Module `stream`
### Functions:
- `openStream(path: String)`: Opens a stream to the specified path.
- `readStream(stream: Stream, size: Int)`: Reads data from the stream.
- `writeStream(stream: Stream, data: Byte[])`: Writes data to the stream.
- `closeStream(stream: Stream)`: Closes the specified stream.

---

## Module `character`
### Functions:
- `isDigit(c: Char)`: Checks if the character is a digit.
- `isLetter(c: Char)`: Checks if the character is a letter.
- `isWhitespace(c: Char)`: Checks if the character is a whitespace.
- `toUpperCase(c: Char)`: Converts the character to uppercase.
- `toLowerCase(c: Char)`: Converts the character to lowercase.

---

## Module `console`
### Functions:
- `print(message: String)`: Prints a message to the console.
- `println(message: String)`: Prints a message to the console with a newline.
- `readInput(prompt: String)`: Reads input from the console with a prompt.
- `clear()`: Clears the console.

---

## Module `ftypes`
### Functions:
- `isInteger(value: String)`: Checks if the value can be parsed as an integer.
- `isFloat(value: String)`: Checks if the value can be parsed as a float.
- `isBoolean(value: String)`: Checks if the value can be parsed as a boolean.
- `isString(value: Any)`: Checks if the value is a string.

---

## Module `memory`
### Functions:
- `allocate(size: Int)`: Allocates a block of memory of the specified size.
- `deallocate(pointer: Pointer)`: Deallocates the memory block pointed to by the pointer.
- `copy(source: Pointer, destination: Pointer, size: Int)`: Copies data from the source pointer to the destination pointer.
- `set(pointer: Pointer, value: Byte, size: Int)`: Sets a block of memory to a specified value.

---

## Module `number`
### Functions:
- `parseInt(value: String)`: Parses a string and returns its integer value.
- `parseFloat(value: String)`: Parses a string and returns its float value.
- `toString(value: Int)`: Converts an integer to its string representation.
- `toString(value: Float)`: Converts a float to its string representation.

---

## Module `sfm`
### Functions:
- `loadSound(filePath: String)`: Loads a sound file from the specified path.
- `playSound(sound: Sound)`: Plays the specified sound.
- `stopSound(sound: Sound)`: Stops the specified sound.
- `setVolume(sound: Sound, volume: Float)`: Sets the volume for the specified sound.

---

## Module `string`
### Functions:
- `length(value: String)`: Returns the length of the string.
- `substring(value: String, start: Int, end: Int)`: Returns a substring from the specified start to end index.
- `indexOf(value: String, search: String)`: Returns the index of the first occurrence of the search string.
- `toUpperCase(value: String)`: Converts the string to uppercase.
- `toLowerCase(value: String)`: Converts the string to lowercase.
- `trim(value: String)`: Removes leading and trailing whitespace from the string.
- `split(value: String, delimiter: String)`: Splits the string into an array of substrings based on the specified delimiter.
- `replace(value: String, target: String, replacement: String)`: Replaces all occurrences of the target string with the replacement string.

---

## Module `system`
### Functions:
- `getOS()`: Returns the name of the operating system.
- `getCurrentDirectory()`: Returns the current working directory.
- `exit(code: Int)`: Exits the program with the specified exit code.
- `getEnvironmentVariable(name: String)`: Returns the value of the specified environment variable.

---

## Module `arrays`
### Functions:
- `length(array: Array)`: Returns the length of the array.
- `get(array: Array, index: Int)`: Returns the element at the specified index in the array.
- `set(array: Array, index: Int, value: Any)`: Sets the element at the specified index in the array to the given value.
- `push(array: Array, value: Any)`: Adds a new element to the end of the array.
- `pop(array: Array)`: Removes the last element from the array and returns it.
- `slice(array: Array, start: Int, end: Int)`: Returns a new array containing the elements from the specified start index to the end index.

---

## Module `date`
### Functions:
- `now()`: Returns the current date and time.
- `format(date: Date, formatString: String)`: Formats the date according to the specified format string.
- `parse(dateString: String)`: Parses a date string and returns a Date object.
- `addDays(date: Date, days: Int)`: Adds the specified number of days to the date.

---

## Module `jfels`
### Functions:
- `loadImage(filePath: String)`: Loads an image from the specified file path.
- `drawImage(image: Image, x: Int, y: Int)`: Draws the specified image at the given coordinates.
- `resizeImage(image: Image, width: Int, height: Int)`: Resizes the image to the specified width and height.
- `saveImage(image: Image, filePath: String)`: Saves the image to the specified file path.

---

## Module `json`
### Functions:
- `parse(jsonString: String)`: Parses a JSON string and returns a corresponding object.
- `stringify(object: Any)`: Converts an object to a JSON string.
- `getValue(jsonObject: Object, key: String)`: Retrieves the value associated with the specified key in the JSON object.
- `setValue(jsonObject: Object, key: String, value: Any)`: Sets the value for the specified key in the JSON object.

---

## Module `math`
### Functions:
- `add(a: Float, b: Float)`: Returns the sum of two numbers.
- `subtract(a: Float, b: Float)`: Returns the difference between two numbers.
- `multiply(a: Float, b: Float)`: Returns the product of two numbers.
- `divide(a: Float, b: Float)`: Returns the quotient of two numbers.
- `sqrt(value: Float)`: Returns the square root of the specified value.
- `pow(base: Float, exponent: Float)`: Returns the result of raising the base to the power of the exponent.

---

## Module `random`
### Functions:
- `nextInt(min: Int, max: Int)`: Returns a random integer between the specified minimum and maximum values.
- `nextFloat(min: Float, max: Float)`: Returns a random float between the specified minimum and maximum values.
- `nextBoolean()`: Returns a random boolean value (true or false).
- `shuffle(array: Array)`: Shuffles the elements of the specified array randomly.

---

## Module `yaml`
### Functions:
- `load(filePath: String)`: Loads a YAML file and returns the corresponding object.
- `dump(object: Any)`: Converts an object to a YAML string.
- `getValue(yamlObject: Object, key: String)`: Retrieves the value associated with the specified key in the YAML object.
- `setValue(yamlObject: Object, key: String, value: Any)`: Sets the value for the specified key in the YAML object.