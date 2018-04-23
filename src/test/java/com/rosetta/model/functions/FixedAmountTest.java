package com.rosetta.model.functions;

import com.rosetta.model.*;
import com.rosetta.model.calculation.FixedAmount;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class FixedAmountTest {

    @Test
    void shouldCalculate() {
        InterestRatePayout interestRatePayout = InterestRatePayout.builder()
                .setQuantity(ContractualQuantity.builder()
                        .setNotionalSchedule(NotionalSchedule.builder()
                                .setNotionalStepSchedule((NonNegativeAmountSchedule) NonNegativeAmountSchedule.builder()
                                        .setCurrency(CurrencyEnum.EUR)
                                        .setInitialValue(BigDecimal.valueOf(50_000_000))
                                        .build())
                                .build())
                        .build())
                .setInterestRate(InterestRate.builder()
                        .setFixedRate(Schedule.builder()
                                .setInitialValue(BigDecimal.valueOf(0.06))
                                .build())
                        .build())
                .setDayCountFraction(DayCountFractionEnum._30E_360)
                .setCalculationPeriodDates(CalculationPeriodDates.builder()
                        .setEffectiveDate(AdjustableDate.builder()
                                .setUnadjustedDate(LocalDate.of(2018, 3, 1))
                                .setDateAdjustments(BusinessDayAdjustments.builder()
                                        .setBusinessDayConvention(BusinessDayConventionEnum.NONE)
                                        .build())
                                .build())
                        .setTerminationDate(AdjustableDate.builder()
                                .setUnadjustedDate(LocalDate.of(2020, 3, 1))
                                .setDateAdjustments(BusinessDayAdjustments.builder()
                                        .setBusinessDayConvention(BusinessDayConventionEnum.MODFOLLOWING)
                                        .setBusinessCenters(BusinessCenters.builder()
                                                .setBusinessCentersReference("primaryBusinessCenters")
                                                .build())
                                        .build())
                                .build())
                        .setCalculationPeriodFrequency((CalculationPeriodFrequency) CalculationPeriodFrequency.builder()
                                .setRollConvention(RollConventionEnum._3)
                                .setPeriodMultiplier(3)
                                .setPeriod(PeriodExtendedEnum.M)
                                .build())
                        .setCalculationPeriodDatesAdjustments(BusinessDayAdjustments.builder()
                                .setBusinessDayConvention(BusinessDayConventionEnum.MODFOLLOWING)
                                .setBusinessCenters(BusinessCenters.builder()
                                        .setBusinessCentersReference("primaryBusinessCenters")
                                        .build())
                                .build())
                        .build())
                .build();

        FixedAmount.Result fixedAmount = new FixedAmount().calculate(interestRatePayout);

// FIXME       assertThat(fixedAmount.getFixedAmount(), is(new BigDecimal("750000.0000")));
        assertThat(fixedAmount.getCurrencyAmount(), is(CurrencyEnum.EUR));
    }

}