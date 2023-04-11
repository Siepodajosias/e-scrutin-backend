package com.escrutin.escrutinbackend.utils;

import com.escrutin.escrutinbackend.exception.DateException;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.List;

import static java.lang.Integer.*;
import static java.time.LocalDate.*;
import static java.util.Objects.nonNull;

public class DateUtils {

	public static String localDateToString(LocalDate date) {
		if (nonNull(date)) {
			return StringUtils.join(List.of(date.getDayOfMonth(), date.getMonthValue() < 10 ? "0" + date.getMonthValue() : date.getMonthValue(), date.getYear()), "-");
		}
		return "";
	}

	public static LocalDate stringToLocalDate(String date) {
		String[] split = date.split("-");
		if (split.length == 3) {
			if (parseInt(split[0]) < 0 || parseInt(split[0]) > 31) {
				throw DateException.jourIncoherent(split[0]);
			}
			else if (parseInt(split[1]) < 0 || parseInt(split[1]) > 12) {
				throw DateException.moisIncoherent(split[1]);
			}
			else if (parseInt(split[2]) < 1000) {
				throw DateException.anneeIncoherent(split[0]);
			}
			return of(parseInt(split[2]), parseInt(split[1]), parseInt(split[0]));
		}
		throw DateException.dateMalformee(date);
	}

	public static String reverseDateString(String date) {
		String[] split = date.split("-");
		if (split.length == 3) {
			if (parseInt(split[0]) < 0 || parseInt(split[0]) > 31) {
				throw DateException.jourIncoherent(split[0]);
			}
			else if (parseInt(split[1]) < 0 || parseInt(split[1]) > 12) {
				throw DateException.moisIncoherent(split[1]);
			}
			else if (parseInt(split[2]) < 1000) {
				throw DateException.anneeIncoherent(split[0]);
			}
			return StringUtils.join(List.of(split[2], split[1], split[0]), "-");
		}
		throw DateException.dateMalformee(date);
	}
}

