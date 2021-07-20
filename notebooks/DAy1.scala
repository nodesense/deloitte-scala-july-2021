// Databricks notebook source
println("Hello Scala")

// COMMAND ----------

// mutablity / immutable type, value
//  variable /  value
// IMMUTABLE, value or reference cannot be changed once assigned first time
val PI = 3.14
println("PI", PI)
// we cannnot change value of PI
//PI = 2 // error

// COMMAND ----------

// MUTABLE => VARIABLE, Value can be changed
var index = 0
println("index", index)
index = 1
println("index", index)

// COMMAND ----------

// Any operators are basically a function
// Primitive - Bool, Byte, Int, Float, Double etc - Scala treat primiviate as classes during compilation, At Runtime, primitives shall be java native types int, long, double
// Reference - Object, String, People, HashTable

var i = 10
i += 1
i = i + 1
i = i.+(1) // + is a function
println(i)

// COMMAND ----------

// Compile time Strict Type checking
var j: Int = 10
// j = "hello" // Error, type issue, assinging a string to int

// COMMAND ----------

// explicit type
var k: Int = 100
// implicit - Scala will check right side expression, derive data type based on that
var p = 100

// COMMAND ----------

// scala is expresion based, not statement oriented
// scala, if, forloop, match case, try catch are expressions, not statements
// expression returns return

// COMMAND ----------

// If condition , expression

val result = if (101 % 2 == 0) "Even" else "Odd"
print(result)

// COMMAND ----------

// multi line block
// block also an expression

val z = {
  println("blah")
  println("foo")
  100 // the last excuted expression is returned from block
}

println("Z is", z)

// COMMAND ----------

val oddOrEven = {
  println("a is 10")
  val a = 10
  // if is an expression, expression output is returned
  if (a % 2 == 0) "Even" else "Odd"
}
println(oddOrEven)

// COMMAND ----------

val greater = {
  val a1 = 200
  val a2 = 170
  val a3 = 150
  // nested if with block etc
  if ( (a1 > a2) && (a1 > a3) ) {
    a1
  } else if ( a2 > a3) {
    a2
  } else {
    a3
  }
}

println("GT ", greater)

// COMMAND ----------

// for and yield  is an expression
// <- iterator
val output = for (i   <-  1 to 10) yield i * 2
println(output)


// COMMAND ----------

// for loop with if guard expression
val odds = for ( i <- 1 to 10 if i % 2 == 1) yield i
println("Odds", odds)

// COMMAND ----------

// for loop with if guard with block
// Will not work...
val odds = for ( i <- 1 to 10 if i % 2 == 1 ) {
  println("I is ", i)
  yield i // return from the block
}

println("Odds are ", odds)

// COMMAND ----------

// String
val c = 'C' // char 2 bytes
val name = "Scala"
// multi line string, preserve white space
val framework= """
learning
spark
scala
sql
databricks
"""

println(name)
println(framework)

// COMMAND ----------

// try catch is also an expression

val result = try {
  42 / 0
} catch {
  case _: Throwable => "invalid expression"
}

println("RESULT", result)

// COMMAND ----------

// Methods

// reusable code, 1 liner
// return type is Int, taken as type inference
def power(n: Int) = n * n

// method with multiline
def sq(n: Int) = {
  println("N is ", n)
  n * n
}

println("power", power(5))
println("s", sq(5))

// COMMAND ----------

def add(a: Int, b: Int) = a + b

println(add(10, 20)) // data is passed left to right, a = 10, b = 20

// named arguments/parameters
println ( add (b = 20, a = 10)) // b = 20, a = 10

// COMMAND ----------

// default paramters, used when the caller doesn't pass the defualt value
def sub(a: Int, b: Int = 0) = a - b

println( sub(20, 10)) // a = 20, b = 10
println( sub(20)) // a = 20, b = 0
println( sub(a = 20)) // a = 20, b = 0
println( sub(b = 10, a = 20)) // a = 20, b = 10



// COMMAND ----------

// multi line block and methods
def mul(a: Int, b: Int) = {
  println("A", a, "B", b)
  a * b
}

println(mul(10, 20))

// COMMAND ----------

// annonymous function
// python, java - lambda,
// scala - function
// functions are first class citizen, mean function itself is an object,
// pass function as parameter
// retur nfunction as return value
// assign function as variable reference
// => fat arrow

val power = (n: Int) => n * n
val add = (a : Int, b: Int) => a + b

println("Power", power(5))
println("Add", add(10, 20))

val sum = add // we can assgine a function as reference
println("Sum", sum(10, 20))

// COMMAND ----------

// syntactic sugar
// compiler makes code easier for us, internally it converts to actual code

val mul = (n: Int, m: Int) => n * m

// mul(10, 20) is sugar part, easy for developer
println(mul(10, 20)) // scala will internally convert the code into mul.apply(10, 20)

println(mul.apply(10, 20)) // compilter convert to .apply

// COMMAND ----------

// make a method to be a function

def add(a: Int, b: Int) = a + b

// val add2 = add // error, we cannot assigne method as reference


//add2 is a function now
// create a function that converts method to function using methodName _
val add2 = add _ // creates a wrapper function, that provides function on top of method

println("add2", add2(10 ,20))
println("add2.apply", add2.apply(10, 20))

val add3 = add2 // add2 is a function reference

// COMMAND ----------

// Unit type , equalent of void in java
def greet(msg: String): Unit = println("Hello " + msg)
greet("Scala")

// COMMAND ----------

// Function N, N can be 0, 1, 2, ....22
// Where N means, the number of arguments passed as input to function

// FunctionN, Function0
// Function0 - no arg to function, return 1 result of Type Unit, means void
val greet = new Function0[Unit] {
  def apply(): Unit = println("hello")
}

greet() // no arg
greet.apply()


// COMMAND ----------

// FunctionN, Function1[Int,               Int]
//                       num of arg,        result type

val power = new Function1[Int, Int] {
  // 1 arg : result type
  def apply(n: Int) : Int = n * n
}

println(power(5))
println(power.apply(5))

// COMMAND ----------

// Excercise
// Create a add function of Type Function2, where arg 1 is Int, Arg 2 is Int, result type is Int
// the function returns a + b

val add = new Function2[Int, Int,  Int]  {
  def apply(a: Int, b: Int): Int  = a + b
}

println(add(10, 20))


// COMMAND ----------

// class
// class is a blue print of object
// class is an object factory

// name and company are constructor parameters
// name and company ARE NOT member variables
class Symbol(name: String, company: String) {
  // class body
  // default constructor
  println("Name is", name)
  println("Company is", company)
}

// create an instance for class Symbol
val s1 = new Symbol("INFY", "Infosys")



// COMMAND ----------

// create a class with member variables
// put either val/var while declarign constructor arg to make it as member variable
class Symbol(val name: String, val company: String) {
  println("Name is", name)
  println("Company is", company)
}

val s1 = new Symbol("INFY", "Infosys")
// access member variables name and company
println("name", s1.name)
println("company", s1.company)

val s2 = new Symbol("TCS", "TCS")


// COMMAND ----------



// COMMAND ----------

// define a class it has 1 member variable of type var, 1 member variabel of type val
// then 1 constructor
// val, var means member variable, others are constructor arg
// name, price are member variable
// sector is constructor arg
class Stock(val name: String, var price: Double, sector: String) {

}

val s1 = new Stock("INFY", 500, "IT")
println(s1.name)
println(s1.price)
//println(s1.sector) // ??? It won't work, as sector is not member of Stock,

// COMMAND ----------

// access specifier
// how to protect a member variable or member functions from outside access
// name is member variable of public type, access outside class
// price, is member variabel of private type, accessible only within class and cannot be accessed outside
// no public keyword in scala, default is public
class Stock(val name: String, private var price: Double, sector :String) {

}

val s1 = new Stock("INFY", 500.0, "IT")
println(s1.name) // works, as name is public type
// println(s1.price)// won't work, as price is not accessible outside Stock class


// COMMAND ----------

// we have spcial string conctruct s"", $name
val a = 10
val b = 20
val c = a + b
val result = s" $a + $b  is  $c" // $a look up value from var/val a
println(result)


// COMMAND ----------

class Stock(val name: String, private var price: Double, sector :String) {
  // overwrite a method called toString __str__
  // whenever we print, or calling "hello " + s1, toString shall be called
  override def toString = s"Stock($name, $price, $sector)"
}

val s1 = new Stock("INFY", 500.0, "IT")

// printing object as is
println(s1) // while using objects with print, or concat with string, toString() of Object is called, that prints object location in memmory


// COMMAND ----------

