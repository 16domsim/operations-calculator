package net.tfobz.domsim.operationen.funktionen;

import net.tfobz.domsim.operationen.grundbausteine.Funktion;
import net.tfobz.domsim.operationen.grundbausteine.Operand;

public class Betrag extends Funktion {

	public Betrag(Operand operand) {
		super(operand);
	}

	public Betrag() {
		super();
	}

	@Override
	public double getErgebnis() {
		double ret = 0.0;
		if (this.getOperand() != null)
			ret = Math.abs(this.getOperand().getErgebnis());
		return ret;
	}

	public String toString() {
		String ret = "Not avaiable yet";
		if (this.getOperand() != null) {
			ret = "(|(" + this.getOperand().toString() + "|)=" + String.valueOf(this.getErgebnis()) + ")";
		}
		return ret;
	}
}