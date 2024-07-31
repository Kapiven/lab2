import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.math.exp

fun calculate(input: String): Double {
    val ops = Stack<String>()
    val nums = Stack<Double>()

    val tokens = input.split(" ")
    for (token in tokens) {
        when {
            token.toDoubleOrNull() != null -> nums.push(token.toDouble())
            token in setOf("+", "-", "*", "/", "^", "sqrt", "exp") -> {
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(token)) {
                    applyOp(ops.pop(), nums)
                }
                ops.push(token)
            }
            token == "(" -> ops.push(token)
            token == ")" -> {
                while (ops.peek() != "(") {
                    applyOp(ops.pop(), nums)
                }
                ops.pop() // Remove the "("
            }
        }
    }
    while (!ops.isEmpty()) {
        applyOp(ops.pop(), nums)
    }
    return nums.pop()
}

fun precedence(op: String): Int {
    return when (op) {
        "+", "-" -> 1
        "*", "/" -> 2
        "sqrt", "exp", "^" -> 3
        else -> -1
    }
}

fun infixToPostfix(infix: String): String {
    val ops = Stack<String>()
    val postfix = StringBuilder()

    val tokens = infix.split(" ")
    for (token in tokens) {
        when {
            token.toDoubleOrNull() != null -> postfix.append("$token ")
            token in setOf("+", "-", "*", "/", "^") -> {
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(token)) {
                    postfix.append("${ops.pop()} ")
                }
                ops.push(token)
            }
            token == "(" -> ops.push(token)
            token == ")" -> {
                while (ops.peek() != "(") {
                    postfix.append("${ops.pop()} ")
                }
                ops.pop() // Remove the "("
            }
        }
    }
    while (!ops.isEmpty()) {
        postfix.append("${ops.pop()} ")
    }
    return postfix.toString().trim()
}

fun applyOp(op: String, nums: Stack<Double>) {
    val v = nums.pop()
    when (op) {
        "+" -> nums.push(nums.pop() + v)
        "-" -> nums.push(nums.pop() - v)
        "*" -> nums.push(nums.pop() * v)
        "/" -> nums.push(nums.pop() / v)
        "^" -> nums.push(nums.pop().pow(v))
        "sqrt" -> nums.push(sqrt(v))
        "exp" -> nums.push(exp(v))
    }
}

fun main() {
    print("Ingrese su operación: ")
    val input = readLine()!!
    val postfix = infixToPostfix(input)
    val result = calculate(postfix)
    println("El resultado es: $result")
}

