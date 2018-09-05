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

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link FormulaAssistant}
 *
 * @author Kweny
 * @since 0.0.1
 */
public class FormulaAssistantTest {

    @Test
    public void testStreamingAverage() {
        double avg = 0;
        BigDecimal bigAvg = new BigDecimal(0);
        double sum = 0;
        int count = 0;
        BigDecimal bigSum = new BigDecimal(0);
        for (int i = 0; i < 1000; i++) {
            double operand = new Random().nextDouble();
            sum += operand;
            bigSum = bigSum.add(new BigDecimal(operand));
            count ++;

            avg = FormulaAssistant.streamingAverage(avg, i, operand);
            bigAvg = FormulaAssistant.streamingAverage(bigAvg, new BigDecimal(i), new BigDecimal(operand), 100, RoundingMode.HALF_UP);
        }
        double verifyAvg = sum / count;
        BigDecimal verifyBigAvg = bigSum.divide(new BigDecimal(count), 100, RoundingMode.HALF_UP);
        assertEquals(null, verifyAvg, avg, 0.000000001);
        assertEquals(null, verifyBigAvg.doubleValue(), bigAvg.doubleValue(), 0.0000000000000000001);
    }
}