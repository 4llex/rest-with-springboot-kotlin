package com.example.controller

import com.example.converters.NumberConverter.convertToDouble
import com.example.converters.NumberConverter.isNumeric
import com.example.exceptions.InvalidMathOperationException
import com.example.exceptions.NaoExisteRaizDeNumeroNegativoException
import com.example.exceptions.UnsupportedMathOperationException
import com.example.math.SimpleMath
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class MathController(
    private val simpleMath: SimpleMath
) {

    @GetMapping(value = ["/sum/{numberOne}/{numberTwo}"])
    fun sum(
        @PathVariable(value="numberOne") numberOne: String?,
        @PathVariable(value="numberTwo") numberTwo: String?
    ): Double {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw UnsupportedMathOperationException("Please, set a numeric value!")

        return simpleMath.sum(convertToDouble(numberOne), convertToDouble(numberTwo))
    }

    @GetMapping(value = ["/sub/{numberOne}/{numberTwo}"])
    fun sub(
        @PathVariable(value = "numberOne") numberOne: String?,
        @PathVariable(value = "numberTwo") numberTwo: String?
    ): Double {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw UnsupportedMathOperationException("Please, set a numeric value!")

        return simpleMath.sub(convertToDouble(numberOne), convertToDouble(numberTwo))
    }

    @GetMapping(value = ["/multi/{numberOne}/{numberTwo}"])
    fun multiplication(
        @PathVariable(value = "numberOne") numberOne: String?,
        @PathVariable(value = "numberTwo") numberTwo: String?
    ): Double {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw UnsupportedMathOperationException("Please, set a numeric value!")

        return simpleMath.multi(convertToDouble(numberOne), convertToDouble(numberTwo))
    }

    @GetMapping(value = ["/div/{numberOne}/{numberTwo}"])
    fun divide(
        @PathVariable(value = "numberOne") numberOne: String?,
        @PathVariable(value = "numberTwo") numberTwo: String?
    ): Double {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw UnsupportedMathOperationException("Please, set a numeric value!")

        val denominator = convertToDouble(numberTwo)
        if (denominator.equals(0.0))
            throw InvalidMathOperationException("Denominador nao pode ser 0(zero)")

        return simpleMath.div(convertToDouble(numberOne), convertToDouble(numberTwo))
    }

    @GetMapping(value = ["/media/{numberOne}/{numberTwo}"])
    fun media(
        @PathVariable(value = "numberOne") numberOne: String?,
        @PathVariable(value = "numberTwo") numberTwo: String?
    ): Double {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw UnsupportedMathOperationException("Please, set a numeric value!")

        return simpleMath.media(convertToDouble(numberOne), convertToDouble(numberTwo))
    }

    @GetMapping(value = ["/square/{number}"])
    fun square(
        @PathVariable(value = "number") numberOne: String?
    ): Double {
        if (!isNumeric(numberOne))
            throw UnsupportedMathOperationException("Please, set a numeric value!")

        val number = convertToDouble(numberOne)
        if (number < 0)
            throw NaoExisteRaizDeNumeroNegativoException("Impossivel calcular raiz de numero negativo!")

        return simpleMath.square(number)
    }

}