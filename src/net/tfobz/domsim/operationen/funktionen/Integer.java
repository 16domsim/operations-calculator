package net.tfobz.domsim.operationen.funktionen;

import net.tfobz.domsim.operationen.grundbausteine.Funktion;
import net.tfobz.domsim.operationen.grundbausteine.Operand;

public class Integer extends Funktion {

	public Integer(Operand operand) {
		super(operand);
	}

	public Integer() {
		super();
	}

	@Override
	public double getErgebnis() {
		double ret = 0.0;
		if (this.getOperand() != null)
			ret = (int) this.getOperand().getErgebnis();
		return ret;
	}

	public String toString() {
		String ret = "Not avaiable yet";
		if (this.getOperand() != null) {
			ret = "(integer(" + this.getOperand().toString() + "|)=" + String.valueOf(this.getErgebnis()) + ")";
		}
		return ret;
	}
}