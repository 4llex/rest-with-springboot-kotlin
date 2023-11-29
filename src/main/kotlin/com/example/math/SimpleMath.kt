package com.example.math

import org.springframework.stereotype.Component
import kotlin.math.sqrt

@Component
class SimpleMath {

    fun sum(numberOne: Double, numberTwo: Double) :Double = numberOne + numberTwo

    fun sub(numberOne: Double, numberTwo: Double) = numberOne - numberTwo

    fun multi(numberOne: Double, numberTwo: Double) :Double = numberOne * numberTwo

    fun div(numberOne: Double, numberTwo: Double) :Double = numberOne / numberTwo

    fun media(numberOne: Double, numberTwo: Double) :Double = (numberOne + numberTwo) / 2

    fun square(number: Double) :Double = sqrt(number)


}