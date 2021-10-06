package devolon.fi.evcsms.model.enums.station;

import lombok.Getter;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@Getter
public enum UnitInCalculationDistanceBetweenTwoPoints {
    KILOMETER(6371.0),
    MILE(3959.0);

    private Double value;

    UnitInCalculationDistanceBetweenTwoPoints(Double value) {
        this.value = value;
    }
}
