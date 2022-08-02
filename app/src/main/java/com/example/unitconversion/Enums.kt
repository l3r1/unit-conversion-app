package com.example.unitconversion

import java.math.BigDecimal

enum class Enums(val value: BigDecimal) {

    //length
    KILOMETERS(BigDecimal(1000.0)),
    METERS(BigDecimal(1.0)),
    DECIMETERS(BigDecimal(0.1)),
    CENTIMETERS(BigDecimal(0.01)),
    MILLIMETERS(BigDecimal(0.001)),
    NAUTICAL_MILES(BigDecimal(1852.0)),
    LAND_MILES(BigDecimal(1609.34)),
    YARDS(BigDecimal(0.9144)),
    FEET(BigDecimal(0.3048)),
    INCHES(BigDecimal(0.0254)),

    //mass
    TONNES(BigDecimal(1e+6)),
    QUINTALS(BigDecimal(1e+5)),
    STONES(BigDecimal(6350.29)),
    KILOGRAMS(BigDecimal(1000.0)),
    POUNDS(BigDecimal(453.592)),
    OUNCES(BigDecimal(28.3495)),
    GRAMS(BigDecimal(1.0)),
    MILLIGRAMS(BigDecimal(0.001)),
    CARATS(BigDecimal(0.2)),

    //speed
    KILOMETERS_PER_HOUR(BigDecimal(1.0)),
    MILES_PER_HOUR(BigDecimal(3.6)),
    FEET_PER_HOUR(BigDecimal(3.281)),
    METERS_PER_SECOND(BigDecimal(1.0 / 3.6)),
    KNOTS(BigDecimal(1.0 / 1.852)),

    //area
    SQ_KILOMETRES(BigDecimal(1e+6)),
    HECTARES(BigDecimal(10000.0)),
    ACRES(BigDecimal(4046.86)),
    ARES(BigDecimal(100.0)),
    SQ_METERS(BigDecimal(1.0)),

    //volume
    CUBIC_METERS(BigDecimal(1000.0)),
    GALLONS(BigDecimal(4.546)),
    US_GALLONS(BigDecimal(3.785)),
    LITRES(BigDecimal(1.0)),
    MILLILITRES(BigDecimal(0.001)),

    //time
    YEARS(BigDecimal(365.0)),
    MONTHS(BigDecimal(30.0)),
    WEEKS(BigDecimal(7.0)),
    DAYS(BigDecimal(1.0)),
    HOURS(BigDecimal(1.0 / 24)),
    MINUTES(BigDecimal(1.0 / 1440)),
    SECONDS(BigDecimal(1.0 / 86400)),
    MILLISECONDS(BigDecimal(1.0 / 86400000)),

    //energy
    MEGAJOULES(BigDecimal(1e+6)),
    KILOJOULES(BigDecimal(1000.0)),
    JOULES(BigDecimal(1.0)),
    KILOCALORIES(BigDecimal(4184.0)),
    KILOWATT_HOURS(BigDecimal(3.6e+6)),

    //information
    PETABYTES(BigDecimal(1e+9)),
    TERABYTES(BigDecimal(1e+6)),
    GIGABYTES(BigDecimal(1000.0)),
    MEGABYTES(BigDecimal(1.0)),
    KILOBYTES(BigDecimal(0.001)),
    BYTES(BigDecimal(1e-6)),
    BITS(BigDecimal(1.25e-7)),
}