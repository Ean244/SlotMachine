package com.gmail.ianlim224.slotmachine.data;

import com.gmail.ianlim224.slotmachine.SlotMachine;

public class MoneyCounter {
	public static final long MAX_BET = SlotMachine.getInstance().getConfig().getLong("max_bet_amount");
	public static final long BUY_PRICE = SlotMachine.getInstance().getConfig().getLong("bet_price");
	private long money;

	public MoneyCounter() {
		money = 0;
		addMoneyByPrice();
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public void addMoney(long m) {
		this.money += m;
	}

	public void addMoneyByPrice() {
		this.money += BUY_PRICE;
	}

	public void removeMoney(long m) {
		this.money -= m;
	}

	public void removeMoneyByPrice() {
		this.money -= BUY_PRICE;
	}
}
