package br.com.calcard.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResultOption {

	public static MathContext MC = new MathContext(3, RoundingMode.HALF_EVEN);

	private String resultado;
	private String limite;
	private BigDecimal count;
	private BigDecimal probability;
	private BigDecimal prediction;

	public ResultOption(final String resultado,final String limite ) {
		this.limite = limite;
		this.resultado = resultado;
	}
}
