package net.laaber.marsweather.shared.util;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
import net.laaber.marsweather.shared.exception.InvalidDateException;
import org.junit.jupiter.api.Test;

public class SolCalculatorTest {
    private static final LocalDate LANDING_DATE = LocalDate.of(2012, 8, 6);

    @Test
    public void onLandingDate() {
        var sol = SolCalculator.from(LANDING_DATE);
        assertThat(sol).isEqualTo(0);
    }

    @Test
    public void beforeLandingDate() {
        assertThatThrownBy(() -> SolCalculator.from(LANDING_DATE.minusDays(1)))
                .isInstanceOf(InvalidDateException.class);
    }

    @Test
    public void afterLandingDate() {
        var sol = SolCalculator.from(LANDING_DATE.plusDays(1));
        assertThat(sol).isEqualTo(1);
    }

    @Test
    public void on20260214() {
        var sol = SolCalculator.from(LocalDate.of(2026, 2, 14));
        assertThat(sol).isEqualTo(4808);
    }
}
