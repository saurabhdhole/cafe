package com.mm.ms;

import javax.websocket.server.PathParam;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


public abstract class BaseAbstractController<T, ID> {

	@RequestMapping(method = RequestMethod.POST)
	public abstract ResponseEntity<T> create(@RequestBody T inputentity);
	
	@RequestMapping(value="{id}", method = RequestMethod.GET)
	public abstract ResponseEntity<T> read(@PathVariable(value="id")ID id);
	
	@RequestMapping(method = RequestMethod.GET)
	public abstract ResponseEntity<Iterable<T>> readAll();
	
	@RequestMapping(value="/pageable", method = RequestMethod.GET)
	public abstract ResponseEntity<Iterable<T>> readAllPageable(
			@PathParam("firstresult") Integer firstresult,
			@PathParam("maxresult") Integer maxresult,
			@PathParam("sortdir") String sortdir,
			@PathParam("sortfield") String sortfield);
	
	@RequestMapping(method = RequestMethod.PATCH)
	public abstract ResponseEntity<T> update(@RequestBody T tobemerged);
	
	@RequestMapping(value="{id}", method = RequestMethod.DELETE)
	public abstract ResponseEntity<T> delete(@PathVariable(value="id")ID id);	
}