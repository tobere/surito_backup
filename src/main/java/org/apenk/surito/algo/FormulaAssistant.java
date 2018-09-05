/*
 * Copyright (C) 2018 Apenk.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apenk.surito.algo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * TODO Kweny FormulaAssistant
 *
 * @author Kweny
 * @since 0.0.1
 */
public class FormulaAssistant {

    private static final BigDecimal BIG_ZERO = new BigDecimal(0);
    private static final BigDecimal BIG_ONE = new BigDecimal(1);

    public static double randomNumber(double min, double max) {
        return min + (Math.random() * (max - min + 1));
    }

    public static BigDecimal randomNumber(BigDecimal min, BigDecimal max) {
        return max.subtract(min).add(BIG_ONE).multiply(new BigDecimal(Math.random())).add(min);
    }

    public static int calcPageTotalNumber(int recordTotalNumber, int pageSize) {
        return (recordTotalNumber + pageSize - 1) / pageSize;
    }

//    public static double average(double... values) {
//
//    }

    /**
     * <p>流式计算平均值。
     * 根据已有的平均值 {@code prevAverage}、
     * 已有操作数的数量 {@code prevCount}、
     * 新的操作数 {@code newOperand} 计算新的平均值。</p>
     *
     * <p>注意：操作数的数量 {@code prevCount} 不能是负数，可以为 0。</p>
     *
     * <p>第一次计算时，{@code prevAverage} 和 {@code prevCount} 传入 0 即可。</p>
     *
     * @param prevAverage 上一次计算的平均值
     * @param prevCount 上一次平均值的操作数总数
     * @param newOperand 新的操作数
     * @return 新的平均值
     * @throws ArithmeticException 当 {@code prevCount} 小于 0 时抛出该异常
     * @since 0.0.1
     */
    public static double streamingAverage(double prevAverage, int prevCount, double newOperand) {
        if (prevCount < 0) {
            throw new ArithmeticException("The number of operands cannot be negative.");
        }
//        return prevAverage + (newOperand - prevAverage) / (prevCount + 1);
        return (prevAverage * prevCount) / (prevCount + 1) + newOperand / (prevCount + 1);
    }

    /**
     * <p>流式计算平均值（大数）。
     * 根据已有的平均值 {@code prevAverage}、
     * 已有操作数的数量 {@code prevCount}、
     * 新的操作数 {@code newOperand} 计算新的平均值。</p>
     *
     * <p>注意：操作数的数量 {@code prevCount} 不能是负数，可以为 0。</p>
     *
     * <p>为避免出现除不尽的情况，需要指定精确度 {@code scale} 和舍入模式 {@code roundingMode}。</p>
     *
     * <p>第一次计算时，{@code prevAverage} 和 {@code prevCount} 传入 0 即可。</p>
     *
     * @param prevAverage 上一次计算的平均值
     * @param prevCount 上一次平均值的操作数总数
     * @param newOperand 新的操作数
     * @param scale 精确度
     * @param roundingMode 舍入模式
     * @return 新的平均值
     * @throws ArithmeticException 当 {@code prevCount} 小于 0 时抛出该异常
     * @since 0.0.1
     */
    public static BigDecimal streamingAverage(BigDecimal prevAverage, BigDecimal prevCount, BigDecimal newOperand,
                                              int scale, RoundingMode roundingMode) {
        if (prevCount.compareTo(BIG_ZERO) < 0) {
            throw new ArithmeticException("The number of operands cannot be negative.");
        }
        BigDecimal currCount = prevCount.add(BIG_ONE);
        // (prevAverage * prevCount) / (prevCount + 1) + newOperand / (prevCount + 1);
        return prevAverage.multiply(prevCount).divide(currCount, scale, roundingMode)
                .add(newOperand.divide(currCount, scale, roundingMode));
    }

    public static void main(String[] args) {
        double avg1 = 0;
        BigDecimal avg2 = new BigDecimal(0);
        for (int i = 0; i < 100; i++) {
            double operand = new Random().nextDouble();
            avg1 = streamingAverage(avg1, i, operand);
            avg2 = streamingAverage(avg2, new BigDecimal(i), new BigDecimal(operand), 100, RoundingMode.HALF_UP);
        }
        System.out.println(avg1);
        System.out.println(avg2);
        System.out.println(avg2.doubleValue());
        System.out.println(avg2.toEngineeringString());
        System.out.println(avg2.toPlainString());
        System.out.println(avg2.toString());


        System.out.println(new BigDecimal(100).compareTo(BIG_ZERO));
        System.out.println(new BigDecimal(-100).compareTo(BIG_ZERO));
    }

}