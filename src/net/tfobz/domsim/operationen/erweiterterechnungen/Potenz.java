package net.tfobz.domsim.operationen.erweiterterechnungen;

import net.tfobz.domsim.operationen.grundbausteine.Operand;
import net.tfobz.domsim.operationen.grundbausteine.Operation;

public class Potenz extends Operation {

	public Potenz(Operand operand0, Operand operand1) {
		super(operand0, operand1);
	}

	public Potenz() {
		super();
	}

	@Override
	public double getErgebnis() {
		double ret = 0.0;
		if (this.getOperand(0) != null)
			ret = this.getOperand(0).getErgebnis();
		if (this.getOperand(1) != null) {
			if (ret != 0 && this.getOperand(1).getErgebnis() != 0)
				ret = Math.pow(ret, this.getOperand(1).getErgebnis());
			else
				ret = 0.0;
		}
		return ret;
	}

	public String toString() {
		String ret = "Not avaiable yet";
		if (this.getOperand(0) != null && this.getOperand(1) != null) {
			ret = "(" + this.getOperand(0).toString() + "^" + this.getOperand(1).toString() + "="
					+ String.valueOf(this.getErgebnis()) + ")";
		}
		return ret;
	}
}
