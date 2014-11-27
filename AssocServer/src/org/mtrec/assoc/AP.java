package org.mtrec.assoc;

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

	public AP(String id, String mac, String chan, String power) {
		Id = Integer.parseInt(id);
		MAC = mac;
		Channel = Integer.parseInt(chan);
		Power = Integer.parseInt(power);
	}

}
