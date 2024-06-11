package net.tfobz.domsim.operationen.grundbausteine;

public class Argument extends Konstante {

	private int ergebnis = 0;

	public Argument(double ergebnis) {
		this.setErgebnis(ergebnis);
	}

	public Argument() {
		super();
	}

	@Override
	public void setErgebnis(double ergebnis) {
		this.ergebnis = Math.abs((int) ergebnis);
	}

	public double getErgebnis() {
		return this.ergebnis;
	}

	@Override
	public String toString() {
		return String.valueOf(this.ergebnis);
	}

	@Override
	public void setUserObject(Object object) {
		if (object != null && object instanceof String) {
			try {
				Argument.this.ergebnis = Integer.valueOf((String) object);
			} catch (NumberFormatException e) {
				System.out.println("Üngültige eingabe!");
			}
		}
	}
}
