package com.example.unitconversion

import java.math.BigDecimal

enum class Enums(val value: BigDecimal) {

    //length
    KILOMETERS(BigDecimal("1000.0")),
    METERS(BigDecimal("1.0")),
    DECIMETERS(BigDecimal("0.1")),
    CENTIMETERS(BigDecimal("0.01")),
    MILLIMETERS(BigDecimal("0.001")),
    NAUTICAL_MILES(BigDecimal("1852.0")),
    LAND_MILES(BigDecimal("1609.34")),
    YARDS(BigDecimal("0.9144")),
    FEET(BigDecimal("0.3048")),
    INCHES(BigDecimal("0.0254")),
    LIGHT_YEARS(BigDecimal("9460730472580800.0")),
    VERSTAS(BigDecimal("1066.8")),
    SAZHENS(BigDecimal("2.1336")),
    ARSHINS(BigDecimal("0.7112")),

    //mass
    TONNES(BigDecimal("1000000.0")),
    QUINTALS(BigDecimal("100000.0")),
    STONES(BigDecimal("6350.29")),
    KILOGRAMS(BigDecimal("1000.0")),
    POUNDS(BigDecimal("453.592")),
    OUNCES(BigDecimal("28.3495")),
    GRAMS(BigDecimal("1.0")),
    MILLIGRAMS(BigDecimal("0.001")),
    CARATS(BigDecimal("0.2")),

    //speed
    SPEED_OF_LIGHT(BigDecimal("1079252848.0")),
    SPEED_OF_SOUND(BigDecimal("1234.8")),
    KILOMETRES_PER_HOUR(BigDecimal("1.0")),
    MILES_PER_HOUR(BigDecimal("1.609")),
    FEET_PER_HOUR(BigDecimal("1.097")),
    METRES_PER_SECOND(BigDecimal("3.6")),
    KNOTS(BigDecimal("1.852")),
    EARTH_ORBITAL_SPEED(BigDecimal("107208.0")),
    SUN_ORBITAL_SPEED(BigDecimal("828000.0")),

    //acceleration
    METERS_PER_SQ_SECOND(BigDecimal("1.0")),
    FEET_PER_SQ_SECOND(BigDecimal("0.3048")),
    CENTIMETERS_PER_SQ_SECOND(BigDecimal("0.01")),
    GRAVITATIONAL_ACCELERATION(BigDecimal("9.8")),

    //force
    NEWTONS(BigDecimal("1.0")),
    DYNES(BigDecimal("0.00001")),
    KILOPONDS(BigDecimal("9.80665")),
    POUND_FORCES(BigDecimal("4.448")),
    POUNDALS(BigDecimal("0.138")),

    //area
    SQ_KILOMETRES(BigDecimal("1000000.0")),
    HECTARES(BigDecimal("10000.0")),
    ACRES(BigDecimal("4046.86")),
    ARES(BigDecimal("100.0")),
    SQ_METRES(BigDecimal("1.0")),
    FOOTBALL_PITCHES(BigDecimal("7140.0")),
    SQ_MILES(BigDecimal("2590000.0")),
    DESSIATINS(BigDecimal("10926.512")),

    //volume
    CUBIC_METRES(BigDecimal("1000.0")),
    LITRES(BigDecimal("1.0")),
    MILLILITRES(BigDecimal("0.001")),
    GALLONS(BigDecimal("4.546")),
    US_GALLONS(BigDecimal("3.785")),
    PINTS(BigDecimal("0.568")),
    US_PINTS(BigDecimal("0.473")),
    BARRELS(BigDecimal("158.987")),
    CUBIC_FEET(BigDecimal("28.317")),


    //time
    YEARS(BigDecimal("31536000.0")),
    MONTHS(BigDecimal("2628000.0")),
    WEEKS(BigDecimal("604800.0")),
    DAYS(BigDecimal("86400.0")),
    HOURS(BigDecimal("3600.0")),
    MINUTES(BigDecimal("60.0")),
    SECONDS(BigDecimal("1.0")),
    MILLISECONDS(BigDecimal("0.001")),

    //energy
    MEGAJOULES(BigDecimal("1000000.0")),
    KILOJOULES(BigDecimal("1000.0")),
    JOULES(BigDecimal("1.0")),
    KILOCALORIES(BigDecimal("4184.0")),
    KILOWATT_HOURS(BigDecimal("3600000.0")),
    MEGATONS(BigDecimal("4184000000000000.0")),
    KILOTONS(BigDecimal("4184000000000.0")),
    KILOGRAM_FORCE_METERS(BigDecimal("9.80665")),

    //power
    GIGAWATTS(BigDecimal("1000000000.0")),
    MEGAWATTS(BigDecimal("1000000.0")),
    KILOWATTS(BigDecimal("1000.0")),
    WATTS(BigDecimal("1.0")),
    HORSEPOWERS(BigDecimal("745.7")),
    KILOCALORIES_PER_HOUR(BigDecimal("1.162")),

    //information
    PETABYTES(BigDecimal("1000000000.0")),
    TERABYTES(BigDecimal("1000000.0")),
    GIGABYTES(BigDecimal("1000.0")),
    MEGABYTES(BigDecimal("1.0")),
    KILOBYTES(BigDecimal("0.001")),
    BYTES(BigDecimal("0.000001")),
    BITS(BigDecimal("0.000000125")),
}