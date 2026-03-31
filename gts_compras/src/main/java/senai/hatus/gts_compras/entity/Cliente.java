package senai.hatus.gts_compras.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "TB_CLIENTE")
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String nome;

    @Email
    private String email;
    
    @OneToMany(mappedBy = "TB_CLIENTE", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Endereco> enderecos = new HashSet<>();

    
    @OneToMany(mappedBy = "TB_CLIENTE", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Pedido> pedidos = new HashSet<>();
}
