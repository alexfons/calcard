package br.com.calcard.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.calcard.model.ResultOption;
import br.com.calcard.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class ClienteProbability {

	@ToString.Exclude
	private final ClienteRepository repository;
	private List<ResultOption> resultOptions;
	private BigDecimal totalCount;

	public ClienteProbability(final ClienteRepository repositor) {
		repository = repositor;
	}

	public ClienteProbability calculate() {
		populateTotalCount();
		populateResultOptions();
		calculate(resultOptions.iterator());
		return this;
	}

	private void calculate(final Iterator<ResultOption> elements) {
		if (elements.hasNext()) {
			final ResultOption next = elements.next();
			next.setCount(countBy(next.getLimite()));
			next.setProbability(next.getCount().divide(totalCount, ResultOption.MC));
			calculate(elements);
		}
		return;
	}

	private BigDecimal countBy(final String limite) {
		return BigDecimal.valueOf(repository.countByLimite(limite));
	}

	public List<ResultOption> getResultOptions() {
		return resultOptions;
	}

	private void populateResultOptions() {
		resultOptions = new ArrayList<>();
		repository.findLimiteOptions().forEach(result -> resultOptions.add(new ResultOption(result[0].toString(),result[1].toString())));
	}

	private void populateTotalCount() {
		totalCount = BigDecimal.valueOf(repository.count());
	}

}
