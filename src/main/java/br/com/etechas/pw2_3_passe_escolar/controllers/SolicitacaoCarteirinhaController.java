package br.com.etechas.pw2_3_passe_escolar.controllers;

import br.com.etechas.pw2_3_passe_escolar.models.SolicitacaoCarteirinha;
import br.com.etechas.pw2_3_passe_escolar.repositories.SolicitacaoCarteirinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carteirinha")
public class SolicitacaoCarteirinhaController {


    @Autowired
    public SolicitacaoCarteirinhaRepository solicitacaoCarteirinhaRepository;



    @GetMapping
    public ResponseEntity<List<SolicitacaoCarteirinha>> listarTodos()
    {
        return ResponseEntity.ok(
                solicitacaoCarteirinhaRepository.findAll()
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<SolicitacaoCarteirinha> encontrarPorId(@PathVariable Long id)
    {
        Optional<SolicitacaoCarteirinha> opt = solicitacaoCarteirinhaRepository.findById(id);
        if(opt.isPresent()){
            return  ResponseEntity.ok(opt.get());
        }
        return ResponseEntity.notFound().build();

    }


    @PostMapping
    public ResponseEntity<SolicitacaoCarteirinha>  postarSolicitacao(@RequestBody SolicitacaoCarteirinha solicitacaoCarteirinha)
    {
        return ResponseEntity.ok(solicitacaoCarteirinhaRepository.save(solicitacaoCarteirinha));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SolicitacaoCarteirinha> deletarSolicitacao(@PathVariable Long id)
    {
        Optional<SolicitacaoCarteirinha> opt = solicitacaoCarteirinhaRepository.findById(id);
        if(opt.isPresent())
        {
            solicitacaoCarteirinhaRepository.deleteById(id);
            return  ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

    }

    public ResponseEntity<SolicitacaoCarteirinha> alterarSolicitacao(@PathVariable Long id, @RequestBody SolicitacaoCarteirinha solicitacaoCarteirinha)
    {
        Optional<SolicitacaoCarteirinha> opt = solicitacaoCarteirinhaRepository.findById(id);
        if(opt.isPresent())
        {
            SolicitacaoCarteirinha solicitacaoAtualizada =
                    solicitacaoCarteirinhaRepository.save(solicitacaoCarteirinha);
            return ResponseEntity.ok(solicitacaoAtualizada);
        }
        return ResponseEntity.notFound().build();
    }
}