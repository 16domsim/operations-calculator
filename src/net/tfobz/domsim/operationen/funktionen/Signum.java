package net.tfobz.domsim.operationen.funktionen;

import net.tfobz.domsim.operationen.grundbausteine.Funktion;
import net.tfobz.domsim.operationen.grundbausteine.Operand;

public class Signum extends Funktion {

	public Signum(Operand operand) {
		super(operand);
	}

	public Signum() {
		super();
	}

	@Override
	public double getErgebnis() {
		double ret = 0.0;
		if (this.getOperand() != null) {
			ret = this.getOperand().getErgebnis();
			if (ret < 0)
				ret = -1;
			if (ret == 0)
				ret = 0;
			if (ret > 0)
				ret = 1;
		}
		return ret;
	}

	public String toString() {
		String ret = "Not avaiable yet";
		if (this.getOperand() != null) {
			ret = "(sign(" + this.getOperand().toString() + "|)=" + String.valueOf(this.getErgebnis()) + ")";
		}
		return ret;
	}
}