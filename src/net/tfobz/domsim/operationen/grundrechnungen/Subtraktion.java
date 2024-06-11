package net.tfobz.domsim.operationen.grundrechnungen;

import net.tfobz.domsim.operationen.grundbausteine.Operand;
import net.tfobz.domsim.operationen.grundbausteine.Operation;

public class Subtraktion extends Operation {
	public Subtraktion(Operand operand0, Operand operand1) {
		super(operand0, operand1);
	}

	public Subtraktion() {
		super();
	}

	public double getErgebnis() {
		double ret = 0.0;
		if (this.getOperand(0) != null)
			ret = this.getOperand(0).getErgebnis();
		if (this.getOperand(1) != null)
			ret = ret - this.getOperand(1).getErgebnis();
		return ret;
	}

	public String toString() {
		String ret = "Not avaiable yet";
		if (this.getOperand(0) != null && this.getOperand(1) != null) {
			ret = "(" + this.getOperand(0).toString() + "-" + this.getOperand(1).toString() + "="
					+ String.valueOf(this.getErgebnis()) + ")";
		}
		return ret;
	}

}
