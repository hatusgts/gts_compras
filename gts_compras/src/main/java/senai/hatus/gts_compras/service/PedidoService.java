package senai.hatus.gts_compras.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senai.hatus.gts_compras.entity.ItemPedido;
import senai.hatus.gts_compras.entity.Pedido;
import senai.hatus.gts_compras.entity.Produto;
import senai.hatus.gts_compras.entity.enums.Status;
import senai.hatus.gts_compras.repository.PedidoRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoService produtoService;
    private final ClienteService clienteService;

    public PedidoService(PedidoRepository pedidoRepository, ProdutoService produtoService, ClienteService clienteService) {
        this.pedidoRepository = pedidoRepository;
        this.produtoService = produtoService;
        this.clienteService = clienteService;
    }

    @Transactional
    public Pedido save(Pedido pedido) {
        // Validating customer is real
        if (pedido.getCliente() == null || pedido.getCliente().getId() == null) {
            throw new IllegalArgumentException("Cliente é obrigatório para criar um pedido.");
        }
        clienteService.findById(pedido.getCliente().getId());

        // Validate and reduce stock
        for (ItemPedido item : pedido.getItems()) {
            Produto produto = produtoService.findById(item.getProduto().getId());
            if (produto.getEstoque() < item.getQuantidade()) {
                throw new IllegalArgumentException("Produto " + produto.getNome() + " não possui estoque suficiente.");
            }
            produto.setEstoque(produto.getEstoque() - item.getQuantidade());
            item.setPrecoUnitario(produto.getPreco()); // Use the correct unit price of the product
            item.setPedido(pedido);
            produtoService.save(produto); // Save implicitly updates inventory
        }

        pedido.setData(new Date());
        pedido.setStatus(Status.CRIADO);

        // Map Address to Pedido
        if (pedido.getEndereco() != null) {
            pedido.getEndereco().setPedido(pedido);
        }

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Pedido findById(UUID id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
    }

    @Transactional
    public Pedido updateStatus(UUID id, Status newStatus) {
        Pedido pedido = findById(id);

        if (newStatus == Status.CANCELADO) {
            if (pedido.getStatus() != Status.CRIADO) {
                throw new IllegalArgumentException("Apenas pedidos com status CRIADO podem ser cancelados.");
            }
            // Return stock
            for (ItemPedido item : pedido.getItems()) {
                Produto p = item.getProduto();
                p.setEstoque(p.getEstoque() + item.getQuantidade());
                produtoService.save(p);
            }
            pedido.setStatus(Status.CANCELADO);
            return pedidoRepository.save(pedido);
        }

        // Flux: CRIADO -> PAGO -> ENVIADO (No jumping, No going back)
        if (pedido.getStatus() == Status.CRIADO && newStatus == Status.PAGO) {
            pedido.setStatus(Status.PAGO);
        } else if (pedido.getStatus() == Status.PAGO && newStatus == Status.ENVIADO) {
            pedido.setStatus(Status.ENVIADO);
        } else {
            throw new IllegalArgumentException("Transição de status inválida de " + pedido.getStatus() + " para " + newStatus);
        }

        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void delete(UUID id) {
        Pedido pedido = findById(id);
        if (pedido.getStatus() != Status.CRIADO) {
            throw new IllegalArgumentException("Não é possível deletar o pedido a menos que esteja CRIADO/CANCELADO.");
        }
        pedidoRepository.delete(pedido);
    }
}
