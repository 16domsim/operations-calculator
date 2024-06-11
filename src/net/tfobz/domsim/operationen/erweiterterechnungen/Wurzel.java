package net.tfobz.domsim.operationen.erweiterterechnungen;

import net.tfobz.domsim.operationen.grundbausteine.ArgOperation;
import net.tfobz.domsim.operationen.grundbausteine.Argument;
import net.tfobz.domsim.operationen.grundbausteine.Operand;

public class Wurzel extends ArgOperation {

	public Wurzel(Operand operand, Argument argument) {
		super(operand, argument);
	}

	public Wurzel() {
		super();
	}

	@Override
	public double getErgebnis() {
		double ret = 0.0;
		if (this.getOperand() != null)
			ret = this.getOperand().getErgebnis();
		if (this.getArgument() != null) {
			if (ret > 0) 
				ret = Math.pow(Math.E, ((1D / this.getArgument().getErgebnis()) * Math.log(ret)));
			else
				ret = 0.0;
		}
		return ret;
	}

	public String toString() {
		String ret = "Not avaiable yet";
		if (this.getOperand() != null && this.getArgument() != null) {
			ret = "(Wurzel(" + this.getArgument().toString() + "," + this.getOperand().toString() + ")="
					+ String.valueOf(this.getErgebnis()) + ")";
		}
		return ret;
	}
}
