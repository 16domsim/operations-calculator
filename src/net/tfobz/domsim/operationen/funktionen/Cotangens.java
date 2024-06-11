package net.tfobz.domsim.operationen.funktionen;

import net.tfobz.domsim.operationen.grundbausteine.Funktion;
import net.tfobz.domsim.operationen.grundbausteine.Operand;

public class Cotangens extends Funktion {

	public Cotangens(Operand operand) {
		super(operand);
	}

	public Cotangens() {
		super();
	}

	@Override
	public double getErgebnis() {
		double ret = 0.0;
		if (this.getOperand() != null)
			ret = 1D / Math.tan(this.getOperand().getErgebnis());
		return ret;
	}

	public String toString() {
		String ret = "Not avaiable yet";
		if (this.getOperand() != null) {
			ret = "(cotan(" + this.getOperand().toString() + ")=" + String.valueOf(this.getErgebnis()) + ")";
		}
		return ret;
	}
}