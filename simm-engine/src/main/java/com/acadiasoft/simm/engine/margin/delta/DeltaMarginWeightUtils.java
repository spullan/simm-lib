/*
 * Copyright (c) 2017 AcadiaSoft, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.acadiasoft.simm.engine.margin.delta;

import com.acadiasoft.simm.engine.param.RiskConcentration;
import com.acadiasoft.simm.engine.param.RiskWeight;
import com.acadiasoft.simm.model.risk.RiskClass;
import com.acadiasoft.simm.model.sensitivity.Sensitivity;
import com.acadiasoft.simm.model.sensitivity.WeightedSensitivity;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class DeltaMarginWeightUtils {

  /**
   * As defined in Appendix 1 of doc/ISDA_SIMM_2.0_(PUBLIC).pdf
   */
  static List<WeightedSensitivity> weightSensitivities(RiskClass riskClass, List<Sensitivity> sensitivities) {
    return sensitivities.stream().map((s) -> weightSensitivity(riskClass, s, RiskConcentration.getDeltaConcentrationRisk(riskClass, s, sensitivities)))
      .collect(Collectors.toList());
  }

  static WeightedSensitivity weightSensitivity(RiskClass riskClass, Sensitivity sensitivity, BigDecimal concentrationRiskFactor) {
    BigDecimal riskWeight = RiskWeight.getRiskWeight(riskClass, sensitivity);
    BigDecimal weightedValue = riskWeight.multiply(sensitivity.getAmountUsd()).multiply(concentrationRiskFactor);
    return new WeightedSensitivity(sensitivity, weightedValue);
  }
}
