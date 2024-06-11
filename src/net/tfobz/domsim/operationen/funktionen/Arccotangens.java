package net.tfobz.domsim.operationen.funktionen;

import net.tfobz.domsim.operationen.grundbausteine.Funktion;
import net.tfobz.domsim.operationen.grundbausteine.Operand;

public class Arccotangens extends Funktion {

	public Arccotangens(Operand operand) {
		super(operand);
	}

	public Arccotangens() {
		super();
	}

	@Override
	public double getErgebnis() {
		double ret = 0.0;
		if (this.getOperand() != null)
			ret = 1D / Math.atan(this.getOperand().getErgebnis());
		return ret;
	}

	public String toString() {
		String ret = "Not avaiable yet";
		if (this.getOperand() != null) {
			ret = "(arccotan(" + this.getOperand().toString() + ")=" + String.valueOf(this.getErgebnis()) + ")";
		}
		return ret;
	}
}
