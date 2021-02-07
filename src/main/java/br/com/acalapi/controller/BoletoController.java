package br.com.acalapi.controller;

import br.com.acalapi.controller.filtro.Filtro;
import br.com.acalapi.dto.ChaveValorDTO;
import br.com.acalapi.entity.Contrato;
import br.com.acalapi.entity.financeiro.Boleto;
import br.com.acalapi.repository.BoletoRepository;
import br.com.acalapi.repository.ContratoRepository;
import br.com.acalapi.repository.PagamentoRepository;
import br.com.acalapi.service.DatabaseSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/boleto")
public class BoletoController extends Controller<Boleto, Filtro>{

    @Autowired
    private BoletoRepository repository;

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;



    @Autowired
    private DatabaseSequenceService databaseSequenceService;

    @Override
    public MongoRepository getRepository() {
        return repository;
    }

    @RequestMapping(value="/sequencia", method = RequestMethod.GET)
    public long buscarSeq() {
        return databaseSequenceService.generateSequence("boleto_sequence");
    }

    @Override
    public Sort getSort() {
        return Sort.by("tipoLogradouro.nome").ascending().and(Sort.by("nome").ascending());
    }

    @Override
    public Query getQueryDuplicidade(Boleto boleto) {
        return
            new Query().addCriteria(
                Criteria
                    .where("referencia").is(boleto.getReferencia())
                    .and("contrato.id").is(boleto.getContrato().getId()));
    }

    @Override
    public void salvar(@RequestBody List<Boleto> boletos) {

        boletos.forEach(b-> {
            b.setNumero(getNumeroBoleto());

            if(b.getContrato().getReferencias() == null){
               b.getContrato().setReferencias(new ArrayList<>());
            }

            b.getContrato().getReferencias().add(b.getReferencia());
            contratoRepository.save(b.getContrato());
        });

        super.salvar(boletos);
    }

    @Transactional
    @RequestMapping(value="/salvar", method = RequestMethod.POST)
    public void salvar(@RequestBody Boleto boleto){

        pagamentoRepository.saveAll(boleto.getPagamentos());
        repository.save(boleto);
    }


    public String getNumeroBoleto(){
        long id = databaseSequenceService.generateSequence(Boleto.SEQUENCE_NAME);

        String data =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy")) + "." +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM")) + "." ;

        String ldap = String.format("%6s", String.valueOf(id)).replace(" ", "0");

        return data + ldap;
    }

    @RequestMapping(value="/countByReferencia/{referencias}", method = RequestMethod.GET)
    public List<ChaveValorDTO> countByCategoria(@PathVariable List<String> referencias){

        List<ChaveValorDTO> lista = new ArrayList<ChaveValorDTO>();

        referencias.forEach(r -> {
            lista.add(new ChaveValorDTO(r, repository.countByReferencia(r) ));
        });

        return lista ;
    }

    @RequestMapping(value="/buscarPorNumero/{numero}", method = RequestMethod.GET)
    public Boleto buscar(@PathVariable String numero) {
        return repository.findByNumero(numero);
    }

    @Override
    public void deletar(@PathVariable String id) {

        Boleto boleto = super.buscar(id);

        Contrato contrato = mongoOperations.findById(boleto.getContrato().getId(), Contrato.class);
        contrato.getReferencias().remove(boleto.getReferencia());

        contratoRepository.save(contrato);

        super.deletar(id);
    }
}
