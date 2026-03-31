package senai.hatus.gts_compras.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_ENDERECO")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String rua;

    @Column
    private String cidade;

    @Column
    private String cep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Cliente cliente;
    
    @OneToOne(mappedBy = "Endereco")
    private Pedido pedido;

}
