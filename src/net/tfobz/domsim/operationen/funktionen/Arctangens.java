package net.tfobz.domsim.operationen.funktionen;

import net.tfobz.domsim.operationen.grundbausteine.Funktion;
import net.tfobz.domsim.operationen.grundbausteine.Operand;

public class Arctangens extends Funktion {

	public Arctangens(Operand operand) {
		super(operand);
	}

	public Arctangens() {
		super();
	}


	@Override
	public double getErgebnis() {
		double ret = 0.0;
		if (this.getOperand() != null)
			ret = Math.atan(this.getOperand().getErgebnis());
		return ret;
	}

	public String toString() {
		String ret = "Not avaiable yet";
		if (this.getOperand() != null) {
			ret = "(arctan(" + this.getOperand().toString() + ")="
					+ String.valueOf(this.getErgebnis()) + ")";
		}
		return ret;
	}
}
