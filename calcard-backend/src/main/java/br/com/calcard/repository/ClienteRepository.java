
package br.com.calcard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.com.calcard.model.Cliente;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(collectionResourceRel = "cliente", path = "cliente")
public interface ClienteRepository extends PagingAndSortingRepository<Cliente, Long> {

	public Long countByDependentesAndLimite(@Param("dependentes") String dependentes,@Param("limite") String limite);

	public Long countByEstadoAndLimite(@Param("estado")String estado,@Param("limite") String limite);

	public Long countByEstadoCivilAndLimite(@Param("estadoCivil")String estadoCivil,@Param("limite") String limite);

	public Long countByIdadeAndLimite(@Param("idade")String idade, @Param("limite")String limite);

	public Long countByLimite(@Param("limite")String limite);

	public Long countByRendaAndLimite(@Param("renda")String renda, @Param("limite")String limite);

	public Long countByResultadoAndLimite(@Param("resultado")String resultado, @Param("limite")String limite);

	public 	Long countBySexoAndLimite(@Param("sexo")String sexo, @Param("limite")String limite);

	@Query("SELECT DISTINCT c.resultado, c.limite FROM Cliente c")
	public List<Object[]> findLimiteOptions();
	
	public List<Cliente> findByCpfContaining(@Param("q")String q);

}
