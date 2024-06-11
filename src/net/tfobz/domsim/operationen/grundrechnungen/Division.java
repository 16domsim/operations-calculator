package net.tfobz.domsim.operationen.grundrechnungen;

import net.tfobz.domsim.operationen.grundbausteine.Operand;
import net.tfobz.domsim.operationen.grundbausteine.Operation;

public class Division extends Operation {
	public Division(Operand operand0, Operand operand1) {
		super(operand0, operand1);
	}

	public Division() {
		super();
	}

	public double getErgebnis() {
		double ret = 0.0;
		if (this.getOperand(0) != null)
			ret = this.getOperand(0).getErgebnis();
		if (this.getOperand(1) != null) {
			if (this.getOperand(1).getErgebnis() != 0)
				ret = ret / this.getOperand(1).getErgebnis();
			else
				ret = 0.0;
		}
		return ret;
	}

	public String toString() {
		String ret = "Not avaiable yet";
		if (this.getOperand(0) != null && this.getOperand(1) != null) {
			ret = "(" + this.getOperand(0).toString() + "/" + this.getOperand(1).toString() + "="
					+ String.valueOf(this.getErgebnis()) + ")";
		}
		return ret;
	}

}
