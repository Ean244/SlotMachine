package com.gmail.ianlim224.slotmachine.utils;

import java.util.ArrayList;
import java.util.List;

import com.gmail.ianlim224.slotmachine.SlotMachine;

public class RandNumGenerator {
	private List<Integer> usedNumbers;
	private int maxNumber;

	public RandNumGenerator() {
		usedNumbers = new ArrayList<>();
		maxNumber = 2;
	}

	public int next() {
		Integer res = SlotMachine.getrandom(0, maxNumber);
		if (usedNumbers.contains(res)) {
			next();
		}
		usedNumbers.add(res);
		if (usedNumbers.size() == maxNumber) {
			usedNumbers.clear();
		}
		return res;
	}
}
