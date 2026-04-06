package senai.hatus.gts_compras.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import senai.hatus.gts_compras.entity.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
}
