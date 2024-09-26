package com.onmyway.util

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class InputTextValidatorTest {

    private lateinit var inputTextValidator: InputTextValidator

    @Before
    fun setUp() {
        inputTextValidator = InputTextValidator()
    }

    @Test
    fun validateUserName_matchesCondition_returnsTrue() {
        val korRange = '가' .. '힣'
        val engUpperCaseRange = 'A' .. 'Z'
        val engLowerCaseRange = 'a' .. 'z'
        val entireCharRangeArray = listOf(korRange, engUpperCaseRange, engLowerCaseRange).flatMap { it.toList() }.toCharArray()

        val minUserNameLength = 2
        val maxUserNameLength = 4

        // 전체 케이스
        /*val idxArray = IntArray(maxUserNameLength) {
            if (it >= minUserNameLength) -1 else 0
        }
        lateinit var createdName: String
        lateinit var result: ValidationResult
        while (idxArray[maxUserNameLength - 1] < entireCharRangeArray.size) {
            createdName = idxArray.map {
                if (it != -1) entireCharRangeArray[it] else ""
            }.joinToString("")
            result = inputTextValidator.validateUserName(createdName)
            assertThat(result.result).isEqualTo(true)

            idxArray[0]++
            for (idx in 0 until maxUserNameLength - 1) {
                if (idxArray[idx] >= entireCharRangeArray.size) {
                    idxArray[idx] = 0
                    idxArray[idx + 1]++
                }
            }
        }*/

        // 글자 수 별 랜덤 케이스 천만개
        lateinit var idxArray: IntArray
        lateinit var createdName: String
        lateinit var result: ValidationResult
        for (userNameLength in minUserNameLength .. maxUserNameLength) {
            repeat(10000000) {
                idxArray = IntArray(userNameLength) { (0 until maxUserNameLength).random() }
                createdName = idxArray.map { entireCharRangeArray[it] }.joinToString("")
                result = inputTextValidator.validateUserName(createdName)
                assertThat(result.result).isEqualTo(true)
            }
        }
    }

    @Test
    fun validateUserName_mismatchesCondition_returnsFalse() {
        lateinit var result: ValidationResult
        val charRange = 'a' .. 'z'
        for (char1 in charRange) {
            for (char2 in charRange) {
                for (char3 in charRange) {
                    result = inputTextValidator.validateUserName("${char1}${char2}${char3}")
                    assertThat(result.result).isEqualTo(true)
                }
            }
        }
    }

    @Test
    fun validateUserName_engEngEngEng_returnsFalse() {

    }

    @Test
    fun validateUserId() {
    }

    @Test
    fun validatePassword() {
    }

    @Test
    fun validateEmail() {
    }
}