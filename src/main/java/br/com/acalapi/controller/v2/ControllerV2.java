package br.com.acalapi.controller.v2;

import br.com.acalapi.entity.AE;
import br.com.acalapi.filtro.v2.Filtro;
import br.com.acalapi.service.ServiceV2;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public abstract class ControllerV2<T extends AE, F extends Filtro> {

    public abstract ServiceV2 getService();

    @RequestMapping(value = "/salvar-todos", method = RequestMethod.POST)
    public void salvar(@RequestBody List<T> ts) {
        getService().salvar(ts);
    }

    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
    public void salvar(@RequestBody T t) {
        getService().salvar(t);
    }

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public List<T> listar() {
        return getService().listarTodos();
    }

    @RequestMapping(value = "/listar", method = RequestMethod.POST)
    public List<T> listar(@RequestBody F f) {
        return getService().listar(f);
    }

    @RequestMapping(value = "/deletar/{id}", method = RequestMethod.DELETE)
    public void deletar(@PathVariable String id) {
        getService().deletar(id);
    }

    @RequestMapping(value = "/editar", method = RequestMethod.PUT)
    public void editar(@RequestBody T t) {
        getService().editar(t);
    }

    @RequestMapping(value = "/buscar/{id}", method = RequestMethod.GET)
    public T buscar(@PathVariable String id) {
       return (T) getService().buscar(id);
    }

    @RequestMapping(value = "/paginar", method = RequestMethod.POST)
    public Page<T> paginar(@RequestBody F f) {
        return (Page<T>) getService().paginar(f);
    }

    @RequestMapping(value = "paginar/{offset}/{limit}", method = RequestMethod.GET)
    public Page<T> paginar(@PathVariable int offset, @PathVariable int limit) {
        return (Page<T>) getService().paginar(offset, limit);
    }

}