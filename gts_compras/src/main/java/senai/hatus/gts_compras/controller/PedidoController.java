package senai.hatus.gts_compras.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import senai.hatus.gts_compras.entity.Pedido;
import senai.hatus.gts_compras.entity.enums.Status;
import senai.hatus.gts_compras.service.PedidoService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<Pedido> create(@Valid @RequestBody Pedido pedido) {
        return new ResponseEntity<>(pedidoService.save(pedido), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> findAll() {
        return ResponseEntity.ok(pedidoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(pedidoService.findById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Pedido> updateStatus(@PathVariable UUID id, @RequestParam Status status) {
        return ResponseEntity.ok(pedidoService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pedido> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(pedidoService.updateStatus(id, Status.CANCELADO));
    }
}
