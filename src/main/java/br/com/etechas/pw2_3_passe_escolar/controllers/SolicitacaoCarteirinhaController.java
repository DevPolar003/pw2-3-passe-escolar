package br.com.etechas.pw2_3_passe_escolar.controllers;

import br.com.etechas.pw2_3_passe_escolar.models.SolicitacaoCarteirinha;
import br.com.etechas.pw2_3_passe_escolar.repositories.SolicitacaoCarteirinhaRepository;
import br.com.etechas.pw2_3_passe_escolar.enums.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carteirinhas-transporte")
public class SolicitacaoCarteirinhaController {

    @Autowired
    public SolicitacaoCarteirinhaRepository solicitacaoCarteirinhaRepository;

    @GetMapping
    public ResponseEntity<List<SolicitacaoCarteirinha>> listarTodos() {
        return ResponseEntity.ok(solicitacaoCarteirinhaRepository.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitacaoCarteirinha> encontrarPorId(@PathVariable Long id) {
        Optional<SolicitacaoCarteirinha> opt = solicitacaoCarteirinhaRepository.findById(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<SolicitacaoCarteirinha> postarSolicitacao(@RequestBody SolicitacaoCarteirinha solicitacaoCarteirinha) {
        solicitacaoCarteirinha.setDataSolicitacao(LocalDate.now());
        solicitacaoCarteirinha.setStatus(Status.SOLICITADO);
        solicitacaoCarteirinha.setDataRetiradaPrevista(null);
        return ResponseEntity.ok(
                solicitacaoCarteirinhaRepository.save(solicitacaoCarteirinha)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolicitacaoCarteirinha> alterarSolicitacao(@PathVariable Long id, @RequestBody SolicitacaoCarteirinha solicitacaoCarteirinha) {
        Optional<SolicitacaoCarteirinha> opt = solicitacaoCarteirinhaRepository.findById(id);
        if (opt.isPresent()) {
            SolicitacaoCarteirinha solicitacaoAtualizada = opt.get();
            
            solicitacaoAtualizada.setNomeEstudante(solicitacaoCarteirinha.getNomeEstudante());
            
            solicitacaoAtualizada.setMatricula(solicitacaoCarteirinha.getMatricula());
            
            solicitacaoAtualizada.setLinhaOnibus(solicitacaoCarteirinha.getLinhaOnibus());
            return ResponseEntity.ok(solicitacaoCarteirinhaRepository.save(solicitacaoAtualizada));
        }

        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSolicitacao(@PathVariable Long id) {
        Optional<SolicitacaoCarteirinha> opt =
                solicitacaoCarteirinhaRepository.findById(id);
        if (opt.isPresent()) {
            if (opt.get().getStatus() != Status.SOLICITADO) {
                return ResponseEntity.badRequest().build();
            }
            solicitacaoCarteirinhaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    return ResponseEntity.notFound().build();
}

@PatchMapping("/{id}/avancar-status")
public ResponseEntity<SolicitacaoCarteirinha> avancarStatus(@PathVariable Long id) {
        Optional<SolicitacaoCarteirinha> opt = solicitacaoCarteirinhaRepository.findById(id);
        if (opt.isPresent()) {
            SolicitacaoCarteirinha solicitacao = opt.get();
            if (solicitacao.getStatus() == Status.SOLICITADO) {
                solicitacao.setStatus(Status.EM_CONFECCAO);
                solicitacao.setDataRetiradaPrevista(
                LocalDate.now().plusDays(5)
                );
            } else if (solicitacao.getStatus() == Status.EM_CONFECCAO) {
                solicitacao.setStatus(Status.PRONTO_RETIRADA);
            } else if (solicitacao.getStatus() == Status.PRONTO_RETIRADA) {
                solicitacao.setStatus(Status.ENTREGUE);
            } else {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(
                    solicitacaoCarteirinhaRepository.save(solicitacao)
            );
        }
        return ResponseEntity.notFound().build();
    }
}
