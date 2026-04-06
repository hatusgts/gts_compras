package senai.hatus.gts_compras.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senai.hatus.gts_compras.entity.Produto;
import senai.hatus.gts_compras.repository.ProdutoRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public Produto save(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Produto findById(UUID id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
    }

    @Transactional
    public Produto update(UUID id, Produto updated) {
        Produto existing = findById(id);
        existing.setNome(updated.getNome());
        existing.setPreco(updated.getPreco());
        existing.setEstoque(updated.getEstoque());
        return produtoRepository.save(existing);
    }

    @Transactional
    public void delete(UUID id) {
        Produto existing = findById(id);
        produtoRepository.delete(existing);
    }
}
