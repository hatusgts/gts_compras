package senai.hatus.gts_compras.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "TB_ITEM_PEDIDO")
public class ItemPedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Min(value = 1, message = "Quantity must be greater than 0")
    private Integer quantidade;

    @NotNull
    private Double precoUnitario;

}
