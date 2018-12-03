package br.com.calcard.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import br.com.calcard.model.Cliente;
import br.com.calcard.model.ClienteRequest;
import br.com.calcard.model.ResultOption;
import br.com.calcard.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@AllArgsConstructor
@ToString
public class ClientePrediction {

	@ToString.Exclude
	private final ClienteRepository repository;
	private ResultOption result;
	private Cliente cliente;

	public ClientePrediction(final ClienteRepository repository) {
		this.repository = repository;
	}

	private BigDecimal calculateBayeTheorem(final Iterator<BigDecimal> elements, final BigDecimal totalCount) {
		if (!elements.hasNext()) {
			return BigDecimal.valueOf(1);
		}
		final BigDecimal next = elements.next();
		final MathContext mC = ResultOption.MC;
		final BigDecimal calculateBayeTheorem = calculateBayeTheorem(elements, totalCount);
		return next.divide(totalCount, mC).multiply(calculateBayeTheorem, mC);
	}

	public BigDecimal findPrediction(final ClienteRequest request, final String resultValue) {

		/*
		 * Queries below are constructed using parameter values of age , income
		 * , student , creditRating , approved passed to function. Function
		 * finds probability for every individual parameter of provided class
		 * value and using naive baye's theorem it calculates total probability
		 */
		final List<BigDecimal> elements = new ArrayList<>();

		final String resultFiedName = "Limite";
		final List<String> parameterFiedNames = Arrays
				.asList(new String[] { "Dependentes", "Estado", "EstadoCivil", "Idade", "Renda", "Sexo" });
		parameterFiedNames.forEach(
				parameterFiedName -> elements.add(getElement(parameterFiedName, request, resultFiedName, resultValue)));

		final BigDecimal totalCount = BigDecimal.valueOf(repository.countByLimite(resultValue));
		return calculateBayeTheorem(elements.iterator(), totalCount);

	}

	private BigDecimal getElement(final String parameterFiedName, final ClienteRequest request,
			final String resultFiedName, final String resultValue) {

		try {
			final String reqMethodName = "get" + parameterFiedName;
			final Method reqMethod = request.getClass().getMethod(reqMethodName);
			final Object reqMethodInvoke = reqMethod.invoke(request);
			final String repoMethodName = "countBy" + parameterFiedName + "And" + resultFiedName;
			final Method repoMethod = repository.getClass().getMethod(repoMethodName, reqMethodInvoke.getClass(),
					resultValue.getClass());
			return BigDecimal.valueOf((Long) repoMethod.invoke(repository, reqMethodInvoke, resultValue));
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			log.error("ERROR getting element", e);
			return null;
		}
	}

	public Cliente getResult() {
		return cliente;
	}

	public ClientePrediction predict(final ClienteRequest request, final Iterator<ResultOption> resultOptions) {

		if (resultOptions.hasNext()) {
			final ResultOption next = resultOptions.next();
			next.setPrediction(
					findPrediction(request, next.getLimite()).multiply(next.getProbability(), ResultOption.MC));
			if (result == null || next.getPrediction().compareTo(result.getPrediction()) > 0) {
				result = next;
				cliente= new Cliente(request, result);
			}
			predict(request, resultOptions);
		}
		return this;
	}

}
