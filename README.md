![](img/title.png)

| >English< | [简体中文](README_CN.md) | [臺灣正體](README_TW.md) |  <br /><br />

> **Menu**
> * <a href="#start">    Briefly   </a>
> * <a href="#command">  Command   </a>
> * <a href="#main">    Norms   </a>
> * <a href="#item">     Item   </a>
> * <a href="#license">  License </a>

## 简述 <a id="start"></a>
Script Block is a mod for minecraft 1.7.10. Provides a script block that can be executed in a specific situation, as well as a set of tools. </br >
Make a new dir named `ScriptBlockData` and put all of your json (or json 5) in it. If you would like use Json5, you should a Lib `With Pineapple Psychic`, or all of Json5 file will skip. The text see *Norms*

## Command <a id="command"></a>
**Primary Name:** /script<br />
**Other Name:**/scb, /sc<br />
The primary name and other name are same command.<br />

**parameters**<br />
reload - Reload from dir.<br />
set [File Name] - Set a script from the name of file into a Chapter Of Record.<br />
block - Get a Script Block.

## Norms <a id="main"></a>
Script parsing relies on a stricter specification, which look like an interpreted language with strict specifications.<br />
The mod parser will and will only parse lists and strings (numbers are automatically escaped when parsed, and exceptions are thrown for content that cannot be escaped.) <br />
The parser tries to parse small lists in one list, which means that you must use two lists even if you have only one content.<br />
Look like this:
```json5
[
  [
    "@Command",
    "say", "Hello", "World!"
  ]
]
```

When this script is triggered, it will send "Hello World!" to the server's players. ”。 This is explained when interpreting the "@Command" keyword.<br />

<br />

**@Set**<br />

The keyword takes "@Set" should two parameters, like this:
```json
[
  "@Set", "Test",
  "Hello！"
]
```
The above content stores the variable "Test" to the variable pool and declares the content string "Hello! ”。<br />
When we need to deposit a number? Please use decimals for easy parser understanding. Like this:
```json
[
  "@Set", "TestNumber",
  "10.0"
]
```
Good. So knowing that it will be stored in the variable pool, how do you get the data from the variable pool?<br />
The character "%" helps us get the variable, and the variable can be passed to another variable, as demonstrated below:
```json
  "@Set", "AnotherTestNumber",
  "%TestNumber"
]
```
This way we pass the value of "TestNumber" to the new variable "AnotherTestNumber".

Of course, there are some magic parameters here, using the character "&" to declare a magic parameter to get some in-game data.
> | Method name |content purpose|
> |--------|-------|
> | &Player | get the name of the player who triggered it|
> | &World|Get the world name|
> | &Health|Get the player's Health|
> | &Hunger | get the player Hunger Level|
> | &Item | Get items in the player's hands|
> | &Lv | Get player level|
> | &Random | Get random numbers between 0-100|

<br />

**Calculation Group**<br/>

Calculations Group can be inserted in most places, such as @Set. Here's an example of the operation: <br />
```json
[
  "@Set", "AnotherTestNumber",
  ["@Add", 
  "%TestNumber", "10.0"
  ]
]
```
When this entry is parsed, the value of the variable "AnotherTestNumber" will be the result of "TestNumber + 10.0".

A variable arithmetic group requires a two-bit argument, both of which support strings, variables, or another arithmetic set using numeric form.
<br />

> ***@Add*** keyword are used to declare the addition of two digits. <br />
> ***@Min*** keyword are used to declare two-digit subtraction. <br />
> ***@Mul*** keyword are used to multiply two-digit numbers. <br />
> ***@Exc*** keyword are used to declare a two-digit division. <br />

<br />

**Judgment handle** <br />
Use keyword @If state a judgment that parameters allow strings, variables, and arithmetic groups. The next bit of the @If requires access to a judgment form:
> ***@Is*** keyword are used to determine that the content of two parameters is the same. <br />
> ***@IsNot*** keyword are used to determine the content of two parameters. <br />
> ***@Greater*** keyword are used to determine that the first parameter is greater than the second parameter. <br />
> ***@Less*** keyword are used to determine that the first parameter is less than the second parameter. <br />

```json5

// SomeFile.json5

//...
[
  "@If", "@Is",
  "%Test", "Hello！",
  ["@Set", "Test", "True!"], // If true, run this.
  ["@Set", "Test", "False!"] // If false, run this.
]
// ...
```
The above script will return True, and the value of Test will be set to "True!" .

<br />

**@Command** <br />
let's provide a directive. Remember the first script? Take it:
```json5
[
  [
    "@Command",
    "say", "Hello", "World!"
  ]
]
```

@Command keyword outputs a command. How do we usually output a piece of command? Like `/say`, output Hello World! ,We should write it like this: '/say Hello World!' <br />
Whether it's for Minecraft's command interpreter or our script interpreter, this command will be divided into three sections: `say`, `Hello`, `World!`. The same is true if the command takes parameters. Filling these three paragraphs separately into the list of declared @Command will eventually be flattened into one paragraph. **Please note that do not provide spaces in the string. **<br />
The sending identity of this instruction will be the console (equivalent to the command block). @Command keywords can be declared in the @If handle as a judgment task, and no operations are accepted in @Command, but the "%" keyword can be used to declare some magic variables and your variable pool variables. We have "Test" in our variable pool, so "%Test" is provided here. About @Command The available magic variables are shown in the table:


> |variable name|content|
> |---|---|
> |%Player| returns the name of the player that triggered it
> |%World|Returns the name of the current world|
> |%pos|return coordinates (x y z)|
> |%PlayerPos| returns the player's coordinates (x y z) |

## Item <a id="item"></a>
![](img/item.png)

Items in mods can all be found in the Creative Inventory 's MISC.

* ***Script Blocks:*** Script blocks are the core of this mod and need to be operated with other items.
* ***Chapters of Record:*** Chapters of Record obtained by using instructions to be obtained by copying blank chapters can be used. Right-click the script square to fill (or overwrite) the script content.
* ***Chapter Of Void:*** Right-click a script square with non-empty content to copy the content and turn it into a chapter of record.
* ***Broom Of Void:*** Right-clicking on a block will clean the skin of the script block within the '3*3*3' range, initializing its image.
* ***Touch Of Paint:*** Right-click on a non-scripted block to get a map of the block, and right-click on the scripted block to camouflage. 
* ***Snoop Of Eye:*** Check the script contents of the script block.
* ***Heart Of Block:*** Scripts that execute script blocks directly regardless of trigger form, if any.
* ***Sound Of Harmony:*** Change the way script blocks are triggered. There are four states: Off, Stampede Trigger, Right Hit Trigger, Stampede or Right Hit (Mixed).


## License <a id="license"></a>
<br />


[Pinepple Licese](LICENSE) <p />
[AFL-3.0](LICENSE.txt)