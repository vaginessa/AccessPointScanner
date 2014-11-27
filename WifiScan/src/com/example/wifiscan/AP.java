package com.example.wifiscan;

public class AP {
	private int Id;

	private String MAC;

	private int Power;

	private int Channel;

	public int getId() {
		return Id;
	}

	public String getMAC() {
		return MAC;
	}

	public int getChannel() {
		return Channel;

	}

	public int getPower() {
		return Power;

	}

	public AP() {

	}

	public AP(int id, String mac, int chan, int power) {
		Id = id;
		MAC = mac;
		Channel = chan;
		Power = power;
	}

}
