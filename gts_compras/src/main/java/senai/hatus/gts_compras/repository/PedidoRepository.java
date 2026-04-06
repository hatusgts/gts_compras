package senai.hatus.gts_compras.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import senai.hatus.gts_compras.entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
}
